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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import org.gwt.mosaic.core.client.Dimension;
import org.gwt.mosaic.ui.client.datepicker.DatePicker;
import org.gwt.mosaic.ui.client.layout.CustomGridLayout;
import org.gwt.mosaic.ui.client.layout.GridLayout;
import org.gwt.mosaic.ui.client.layout.GridLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.jboss.seam.example.ticketmonster.model.Event;

import java.util.Date;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 30, 2010
 */
public class EventForm extends LayoutPanel {

    private Event event;
    private boolean dirty = false;

    private TextBox name;
    private DatePicker start;
    private DatePicker end;

    private CheckBox isMajor;

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public EventForm() {
        super(new CustomGridLayout(3, 8));

        name = new TextBox();
        name.setVisibleLength(1);

        // Create a date picker

        start = new DatePicker();
        start.addValueChangeHandler(
                new ValueChangeHandler<Date>() {
                    public void onValueChange(ValueChangeEvent<Date> event) {
                        Date date = (Date)event.getValue();
                        String dateString = DateTimeFormat.getMediumDateFormat().format(date);
                    }
                });

        end = new DatePicker();
        end.addValueChangeHandler(
                new ValueChangeHandler<Date>() {
                    public void onValueChange(ValueChangeEvent<Date> event) {
                        Date date = (Date)event.getValue();
                        String dateString = DateTimeFormat.getMediumDateFormat().format(date);
                    }
                });


        isMajor = new CheckBox();

        add(new Label("Name:"));
        add(name, new GridLayoutData(2,1));

        add(new Label("Start / End Date:"),new GridLayoutData(1,6));                
        add(start, new GridLayoutData(1,6));
        add(end, new GridLayoutData(1,6));

        add(new Label("Is Major?"));
        add(isMajor, new GridLayoutData(2,1));
        
    }

    public boolean isDirty() {
        return dirty;
    }

    public void edit(Event event)
    {
        this.event = event;

        name.setText(event.getName());

        start.setValue(event.getStartDate(), true);
        start.setCurrentMonth(event.getStartDate());
        end.setValue(event.getEndDate(), true);
        end.setCurrentMonth(event.getEndDate());

        
        if(event.isMajor())
            isMajor.setValue(true);

    }


    public void reset() {
        event = null;
        name.setText("");

        isMajor.setValue(false);
        dirty = false;
    }    
}
