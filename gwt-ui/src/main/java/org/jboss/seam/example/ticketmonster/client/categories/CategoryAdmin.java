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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import org.gwt.mosaic.ui.client.layout.BorderLayout;
import org.gwt.mosaic.ui.client.layout.BorderLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.jboss.errai.bus.client.ErraiBus;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.MessageCallback;
import org.jboss.errai.bus.client.framework.MessageBus;
import org.jboss.errai.workspaces.client.api.ProvisioningCallback;
import org.jboss.errai.workspaces.client.api.WidgetProvider;
import org.jboss.errai.workspaces.client.api.annotations.LoadTool;
import org.jboss.seam.example.ticketmonster.client.common.ModelObserver;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Apr 6, 2010
 */
@LoadTool(name = "Categories", group="Data Model")
public class CategoryAdmin implements WidgetProvider, MessageCallback
{
    private final MessageBus bus = ErraiBus.get();

    private CategoryList list;
    private CategoryDetail details;

    public void provideWidget(ProvisioningCallback callback)
    {
        LayoutPanel panel = new LayoutPanel(new BorderLayout());

        details = new CategoryDetail();
        panel.add(details, new BorderLayoutData(BorderLayout.Region.SOUTH, 150));
        callback.onSuccess(panel);

        list = new CategoryList();
        list.setListener(details);
        panel.add(list);

        ModelObserver.register("EventCategory", this);
    }

    public void callback(Message message) {
        DeferredCommand.addCommand(
            new Command()
            {
                public void execute() {
                    details.reset();
                    list.refresh();
                }
            }
        );

    }
}

