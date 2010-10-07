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
import com.google.gwt.user.client.ui.Button;
import org.gwt.mosaic.ui.client.*;
import org.jboss.seam.example.ticketmonster.client.common.SelectionListener;
import org.jboss.seam.example.ticketmonster.model.Event;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 30, 2010
 */
public class EventEditor extends CaptionLayoutPanel
        implements SelectionListener<Event>{

    private TabLayoutPanel tabPanel ;
    private PropertyDeck properties;
    private DescriptionDeck document;

    private boolean isDirty = false;    
    private Event entity = null;

    Button editButton;

    public EventEditor() {

        super("Event Details");
        super.setStyleName("bpm-detail-panel");

        editButton = new Button("Edit", new ClickHandler()
        {
            public void onClick(ClickEvent clickEvent) {
                String s = editButton.getText();
                if(s.equals("Edit"))
                {
                    editButton.setText("Done");
                    properties.showWidget(1);
                    document.showWidget(1);
                }
                else
                {
                    save();
                    editButton.setText("Edit");
                    properties.showWidget(0);
                    document.showWidget(0);
                }

                properties.layout();
                document.layout();
            }
        });
        editButton.setEnabled(false);
        
        this.getHeader().add(editButton, Caption.CaptionRegion.RIGHT);
        
        tabPanel = new DecoratedTabLayoutPanel(TabLayoutPanel.TabBarPosition.BOTTOM, false);

        properties = new PropertyDeck();
        document = new DescriptionDeck();
        
        tabPanel.add(properties, "Properties");
        tabPanel.add(document, "Description");
                
        this.add(tabPanel);
    }

    private void save()
    {
        System.out.println("Saved ...");
    }
    
    public void update(Event entity) {
        this.entity = entity;
        properties.edit(entity);
        document.edit(entity);
        editButton.setEnabled(true);
    }

    public void reset() {
        entity = null;
        properties.reset();
        document.reset();
        editButton.setEnabled(false);
    }


}