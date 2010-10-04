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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.RichTextArea;
import org.gwt.mosaic.ui.client.WidgetWrapper;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.jboss.seam.example.ticketmonster.client.common.RichTextToolbar;
import org.jboss.seam.example.ticketmonster.model.Event;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Oct 4, 2010
 */
public class RTEditor extends LayoutPanel {

    private RichTextArea area;

    public RTEditor() {

        super(new BoxLayout(BoxLayout.Orientation.VERTICAL));

        // Create the text area and toolbar
        area = new RichTextArea();
        area.setSize("98%", "14em");

        DOM.setStyleAttribute(area.getElement(), "background", "#E8E8E8");

        RichTextToolbar toolbar = new RichTextToolbar(area);

        setPadding(0);
        setWidgetSpacing(0);
        add(toolbar, new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));
        add(new WidgetWrapper(area), new BoxLayoutData(BoxLayoutData.FillStyle.BOTH));

        DeferredCommand.addCommand(new Command() {
            public void execute() {
                area.setFocus(true);
            }
        });
    }

    public void edit(Event event)
    {
        area.setHTML(event.getDescription().getActiveRevision().getContent());
    }

    public void reset()
    {
        area.setHTML("");
    }
}
