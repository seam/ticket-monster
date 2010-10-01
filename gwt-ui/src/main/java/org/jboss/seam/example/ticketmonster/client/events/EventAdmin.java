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

import org.gwt.mosaic.ui.client.layout.BorderLayout;
import org.gwt.mosaic.ui.client.layout.BorderLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.jboss.errai.workspaces.client.api.ProvisioningCallback;
import org.jboss.errai.workspaces.client.api.WidgetProvider;
import org.jboss.errai.workspaces.client.api.annotations.LoadTool;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 30, 2010
 */
@LoadTool(name = "Events", group="Data Model")
public class EventAdmin implements WidgetProvider {

    private EventList list;
    private EventEditor editor;

    public void provideWidget(ProvisioningCallback callback) {
        LayoutPanel panel = new LayoutPanel(new BorderLayout());        

        list = new EventList();
        panel.add(list);

        editor = new EventEditor();
        panel.add(editor, new BorderLayoutData(BorderLayout.Region.SOUTH, "300px", "300px", "400px"));
        list.setListener(editor);

        callback.onSuccess(panel);
    }
}
