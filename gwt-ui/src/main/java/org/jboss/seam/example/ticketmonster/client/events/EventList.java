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
package org.jboss.seam.example.ticketmonster.client.events;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;
import org.gwt.mosaic.ui.client.ListBox;
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
import org.jboss.seam.example.ticketmonster.client.common.SelectionListener;
import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.EventCategory;

import java.util.List;

public class EventList extends LayoutPanel implements MessageCallback {

    private final MessageBus bus = ErraiBus.get();
    private ListBox<Event> listBox;

    private SelectionListener<Event> listener = null;
    private String currentCategory = "";

    private com.google.gwt.user.client.ui.ListBox dropBox;
    private LayoutPanel toolBox;

    public EventList() {
        super();
        setPadding(0);

        LayoutPanel listing = new LayoutPanel( new BoxLayout(BoxLayout.Orientation.VERTICAL));

         // ------------------------------------

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
        toolBar.add(new Button("New Event", new ClickHandler()
        {
            public void onClick(ClickEvent clickEvent) {
                newEvent();
            }
        }));

        // listbox
        listBox = createListBox();

         // ------------------------------------

        toolBox = new LayoutPanel();
        toolBox.setPadding(0);
        toolBox.setWidgetSpacing(0);
        toolBox.setLayout(new BoxLayout(BoxLayout.Orientation.HORIZONTAL));
        toolBox.add(toolBar, new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));

        // filter
        LayoutPanel filterPanel = new LayoutPanel(new BoxLayout(BoxLayout.Orientation.VERTICAL));
        filterPanel.setStyleName("mosaic-ToolBar");
        dropBox = new com.google.gwt.user.client.ui.ListBox(false);
        dropBox.getElement().setAttribute("style", "font-size:10px;");
        dropBox.addItem("Select a category:");
        
        dropBox.addChangeListener(new ChangeListener() {
            public void onChange(Widget sender) {                
                refresh();
            }
        });
        filterPanel.add(dropBox);

        toolBox.add(filterPanel, new BoxLayoutData(BoxLayoutData.FillStyle.VERTICAL));

        // ------------------------------------

        // assembly
        listing.add(toolBox, new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));
        listing.add(listBox, new BoxLayoutData(BoxLayoutData.FillStyle.BOTH));

        this.add(listing);
        
        // register with bus
        bus.subscribe("EventList", this);

        Timer t = new Timer()
        {
            @Override
            public void run() {
                init();
            }
        };
        
        t.schedule(150);

    }

    private void init()
    {
        // fetch categories
        MessageBuilder.createMessage()
                .toSubject("CategoryManagement")
                .command("READ")
                .with(MessageParts.ReplyTo, "EventList")
                .done().sendNowWith(bus);

    }

    private void newEvent() {

    }

    public void refresh() {
        MessageBuilder.createMessage()
                .toSubject("EventManagement")
                .command("READ")
                .with(MessageParts.ReplyTo, "EventList")
                .with("category", dropBox.getItemText(dropBox.getSelectedIndex()))
                .done().sendNowWith(bus);

        if(listener!=null)
            listener.reset();
    }

    public void setListener(SelectionListener<Event> listener) {
        this.listener = listener;
    }

    public void callback(Message message) {

        if(message.hasPart("categories"))
        {
            List<EventCategory> categories = (List<EventCategory>)message.get(List.class, "categories");
            bindCategories(categories);
        }
        else
        {
            // event callback
            List<Event> values = message.get(List.class, "events");
            System.out.println("Loaded " +values.size() + " events");
            bindData(values);
        }
    }

    private void bindCategories(List<EventCategory> categories) {
        dropBox.clear();

        for(EventCategory c : categories)
        {
            dropBox.addItem(c.getDescription());        
        }

        toolBox.layout();
    }


    private void bindData(List<Event> instances)
    {
        final DefaultListModel<Event> model =
                (DefaultListModel<Event>) listBox.getModel();
        model.clear();

        List<Event> list = instances;
        for(Event inst : list)
        {
            model.add(inst);
        }

        // layout again
        this.invalidate();
    }

    private ListBox createListBox()
    {
        final ListBox<Event> listBox =
                new ListBox<Event>( new String[] { "<b>Event</b>", "Start", "End", "Major"});

        listBox.setFocus(true);

        listBox.setCellRenderer(new ListBox.CellRenderer<Event>() {
            public void renderCell(ListBox<Event> listBox, int row, int column,
                                   Event item) {
                switch (column) {
                    case 0:
                        listBox.setText(row, column, item.getName());
                        break;
                    case 1:
                        String start = DateTimeFormat.getShortDateFormat().format(item.getStartDate());
                        listBox.setText(row, column, start);
                        break;
                    case 2:
                        String end = DateTimeFormat.getShortDateFormat().format(item.getEndDate());
                        listBox.setText(row, column, end);
                        break;
                    case 3:
                        String major = item.isMajor() ? "*" : "";
                        listBox.setText(row, column, major);
                        break;
                    default:
                        throw new RuntimeException("Unexpected column size "+column);
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
                            Event item = listBox.getItem(index);
                            if(listener!=null)
                                listener.update(item);
                        }
                    }
                }
        );

        return listBox;
    }
}
