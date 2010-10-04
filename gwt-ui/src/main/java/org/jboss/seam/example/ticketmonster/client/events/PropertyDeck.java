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

import com.google.gwt.i18n.client.DateTimeFormat;
import org.gwt.mosaic.ui.client.DeckLayoutPanel;
import org.jboss.seam.example.ticketmonster.client.common.PropertyGrid;
import org.jboss.seam.example.ticketmonster.model.Event;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Oct 4, 2010
 */
public class PropertyDeck extends DeckLayoutPanel {

    private EventForm form;
    private PropertyGrid props;

    public PropertyDeck() {

        super();        

        props = new PropertyGrid(new String[]{"Name", "Start", "End", "Major"});
        add(props);

        form = new EventForm();
        add(form);

        showWidget(0);
    }

    public void edit(Event event)
    {
        props.update(new String[] {
                event.getName(),
                DateTimeFormat.getMediumDateFormat().format(event.getStartDate()),
                DateTimeFormat.getMediumDateFormat().format(event.getEndDate()),
                String.valueOf(event.isMajor())
        });
        form.edit(event);
    }

    public void reset()
    {
        props.clear();
        form.reset();        
    }
}
