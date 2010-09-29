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
import com.google.gwt.user.client.ui.Button;
import org.gwt.mosaic.ui.client.MessageBox;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.jboss.errai.bus.client.ErraiBus;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.framework.MessageBus;
import org.jboss.seam.example.ticketmonster.client.common.ModelObserver;
import org.jboss.seam.example.ticketmonster.client.common.PropertyGrid;
import org.jboss.seam.example.ticketmonster.client.common.SelectionListener;
import org.jboss.seam.example.ticketmonster.model.EventCategory;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 29, 2010
 */
public class CategoryDetail extends LayoutPanel implements SelectionListener<EventCategory> {

    private final MessageBus bus = ErraiBus.get();
    private PropertyGrid properties;
    private EventCategory currentSelection = null;

    public CategoryDetail() {
        super(new BoxLayout(BoxLayout.Orientation.HORIZONTAL));
        super.setStyleName("bpm-detail-panel");

        properties = new PropertyGrid(
                new String[] {"ID", "Name"}
        );

        properties.setPadding(5);
        
        this.add(properties, new BoxLayoutData(BoxLayoutData.FillStyle.BOTH));

        // action
        LayoutPanel actionPanel = new LayoutPanel(new BoxLayout(BoxLayout.Orientation.VERTICAL));
        Button deleteBtn = new Button("Delete", new ClickHandler()
        {
            public void onClick(ClickEvent clickEvent) {

                if(null==currentSelection) return;
                
                MessageBox.confirm("Delete category", "Really delete category '"+currentSelection.getDescription()+"'?",
                        new MessageBox.ConfirmationCallback()
                        {
                            public void onResult(boolean delete) {
                                if(delete)
                                {
                                    MessageBuilder.createMessage()
                                            .toSubject("CategoryManagement")
                                            .command("DELETE")
                                            .with("description", currentSelection.getDescription())
                                            .noErrorHandling()
                                            .sendNowWith(bus);

                                    ModelObserver.fireChange("EventCategory");
                                }
                            }
                        });
            }
        });

        actionPanel.add(deleteBtn);
        this.add(actionPanel, new BoxLayoutData(BoxLayoutData.FillStyle.VERTICAL));

    }

    
    public void update(EventCategory category)
    {
        currentSelection = category;
        properties.update(
                new String[] {
                        String.valueOf(category.getId()),
                        category.getDescription()
                }
        );
    }

    public void reset()
    {
        currentSelection = null;
        properties.clear();
    }
    
}
