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
import com.google.gwt.user.client.ui.HTML;
import org.gwt.mosaic.ui.client.DeckLayoutPanel;
import org.gwt.mosaic.ui.client.ScrollLayoutPanel;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.jboss.seam.example.ticketmonster.model.Event;


/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Oct 4, 2010
 */
public class DescriptionDeck extends DeckLayoutPanel {

    private RTEditor editor;
    private HTML preview;

    public DescriptionDeck() {
        super();

        preview = new HTML("<center>No item to show</center>");
        add(preview);

        editor = new RTEditor();        
        add(editor);

        showWidget(0);
    }

    public void edit(Event event)
    {
        preview.setHTML(event.getDescription().getActiveRevision().getContent());
        editor.edit(event);
    }

    public void reset()
    {
        preview.setHTML("<center>No item to show</center>");
        editor.reset();        
    }
}
