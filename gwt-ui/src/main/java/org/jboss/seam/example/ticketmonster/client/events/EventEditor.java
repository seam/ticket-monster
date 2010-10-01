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
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import org.gwt.mosaic.ui.client.*;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.jboss.seam.example.ticketmonster.client.common.RichTextToolbar;
import org.jboss.seam.example.ticketmonster.client.common.SelectionListener;
import org.jboss.seam.example.ticketmonster.model.Event;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 30, 2010
 */
public class EventEditor extends CaptionLayoutPanel implements SelectionListener<Event> {


    private TabLayoutPanel tabPanel ;
    private EventForm details;
    private LayoutPanel document;

    private RichTextArea area;

    public EventEditor() {

        super("Event Details");
        super.setStyleName("bpm-detail-panel");
        
        tabPanel = new DecoratedTabLayoutPanel(TabLayoutPanel.TabBarPosition.BOTTOM, false);

        details = new EventForm();        

        document = new LayoutPanel();
        document.add(createRichtext());


        tabPanel.add(details, "Properties");
        tabPanel.add(document, "Description");

        this.add(tabPanel);
    }

    public void update(Event entity) {
        details.edit(entity);
        area.setHTML(entity.getDescription().getActiveRevision().getContent());
    }

    public void reset() {
        details.reset();
        area.setHTML("");
        //document.reset();
    }

    private LayoutPanel createRichtext()
    {
        LayoutPanel panel = new LayoutPanel(new BoxLayout(BoxLayout.Orientation.VERTICAL));

        // Create the text area and toolbar
        area = new RichTextArea();
        area.setSize("98%", "14em");        

        DOM.setStyleAttribute(area.getElement(), "background", "#E8E8E8");

        RichTextToolbar toolbar = new RichTextToolbar(area);

        panel.setPadding(0);
        panel.setWidgetSpacing(0);
        panel.add(toolbar, new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));
        panel.add(new WidgetWrapper(area), new BoxLayoutData(BoxLayoutData.FillStyle.BOTH));

        Button buttonOK = new Button("OK");
        buttonOK.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

            }
        });

        Button buttonCancel = new Button("Cancel");
        buttonCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

            }
        });


        DeferredCommand.addCommand(new Command() {
            public void execute() {
                area.setFocus(true);
            }
        });


        return panel;

    }
}