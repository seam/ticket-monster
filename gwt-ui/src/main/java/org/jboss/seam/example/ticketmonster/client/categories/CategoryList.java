/*
 * Copyright 2009 JBoss, a divison Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.example.ticketmonster.client.categories;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import org.gwt.mosaic.ui.client.ListBox;
import org.gwt.mosaic.ui.client.MessageBox;
import org.gwt.mosaic.ui.client.ToolBar;
import org.gwt.mosaic.ui.client.event.RowSelectionEvent;
import org.gwt.mosaic.ui.client.event.RowSelectionHandler;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.list.DefaultListModel;
import org.gwt.mosaic.ui.client.table.AbstractScrollTable;
import org.jboss.errai.bus.client.ErraiBus;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.MessageCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.framework.MessageBus;
import org.jboss.errai.bus.client.protocols.MessageParts;
import org.jboss.seam.example.ticketmonster.client.common.ModelObserver;
import org.jboss.seam.example.ticketmonster.client.common.SelectionListener;
import org.jboss.seam.example.ticketmonster.model.EventCategory;

import java.util.List;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 29, 2010
 */
public class CategoryList extends LayoutPanel implements MessageCallback {

    private final MessageBus bus = ErraiBus.get();
    private ListBox<EventCategory> listBox;

    private SelectionListener<EventCategory> listener = null;

    public CategoryList() {
        super(new BoxLayout(BoxLayout.Orientation.VERTICAL));
        setPadding(0);

        // toolbar
        final ToolBar toolBar = new ToolBar();
        ClickHandler clickHandler = new ClickHandler()
        {
            public void onClick(ClickEvent clickEvent)
            {
                refresh();
            }
        };

        toolBar.add(new Button("Refresh", clickHandler));
        toolBar.addSeparator();
        toolBar.add(new Button("New Category", new ClickHandler()
        {
            public void onClick(ClickEvent clickEvent) {
                newCategory();
            }
        }));

        // listbox
        listBox = createListBox();

        // assembly
        this.add(toolBar, new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));
        this.add(listBox, new BoxLayoutData(BoxLayoutData.FillStyle.BOTH));

        // register with bus
        bus.subscribe("CategoryList", this);

        Timer t = new Timer()
        {
            @Override
            public void run() {
                refresh();
            }
        };

        t.schedule(150);

    }

    private void newCategory() {
        MessageBox.prompt("New Category", "Please enter a category description:", "",
                new MessageBox.PromptCallback<String>() {
                    public void onResult(String input) {
                        if(!input.equals(""))
                            MessageBuilder.createMessage()
                                    .toSubject("CategoryManagement")
                                    .command("CREATE")
                                    .with("description", input)
                                    .noErrorHandling()
                                    .sendNowWith(bus);

                        ModelObserver.fireChange("EventCategory");
                    }
                });
    }

    public void refresh() {
        MessageBuilder.createMessage()
                .toSubject("CategoryManagement")
                .command("LIST")
                .with(MessageParts.ReplyTo, "CategoryList")
                .done().sendNowWith(bus);

        if(listener!=null)
            listener.reset();
    }

    public void setListener(SelectionListener<EventCategory> listener) {
        this.listener = listener;
    }

    public void callback(Message message) {

        List<EventCategory> values = message.get(List.class, "categories");
        System.out.println("Loaded " +values.size() + " categories");
        bindData(values);
    }

    private void bindData(List<EventCategory> instances)
    {
        final DefaultListModel<EventCategory> model =
                (DefaultListModel<EventCategory>) listBox.getModel();
        model.clear();

        List<EventCategory> list = instances;
        for(EventCategory inst : list)
        {
            model.add(inst);
        }

        // layout again
        this.invalidate();
    }

    private ListBox createListBox()
    {
        final ListBox<EventCategory> listBox =
                new ListBox<EventCategory>( new String[] { "<b>Category</b>"});

        listBox.setFocus(true);

        listBox.setCellRenderer(new ListBox.CellRenderer<EventCategory>() {
            public void renderCell(ListBox<EventCategory> listBox, int row, int column,
                                   EventCategory item) {
                switch (column) {
                    case 0:

                        String name = item.getDescription();
                        listBox.setWidget(row, column, new HTML(name));
                        break;
                    default:
                        throw new RuntimeException("Unexpected column size");
                }
            }
        });

        listBox.setMinimumColumnWidth(0, 190);
        listBox.setColumnResizePolicy(AbstractScrollTable.ColumnResizePolicy.MULTI_CELL);

        listBox.addRowSelectionHandler(
                new RowSelectionHandler()
                {
                    public void onRowSelection(RowSelectionEvent rowSelectionEvent)
                    {
                        int index = listBox.getSelectedIndex();
                        if(index!=-1)
                        {
                            EventCategory item = listBox.getItem(index);
                            if(listener!=null)
                                listener.update(item);
                        }
                    }
                }
        );

        return listBox;
    }
}
