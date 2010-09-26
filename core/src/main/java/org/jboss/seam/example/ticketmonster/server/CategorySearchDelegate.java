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
package org.jboss.seam.example.ticketmonster.server;

import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.MessageCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.framework.MessageBus;
import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.seam.example.ticketmonster.action.CategorySearch;
import org.jboss.seam.example.ticketmonster.model.EventCategory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 10, 2010
 */

@Service("CategorySearch")
@RequestScoped
public class CategorySearchDelegate implements MessageCallback {

    @Inject
    CategorySearch catSearch;

    @Inject
    MessageBus bus;

    public void callback(Message message) {

        List<EventCategory> value = catSearch.getCategories();

        MessageBuilder.createConversation(message)
                .subjectProvided()
                .command("getCategories")
                .with("categories", value)
                .done()
                .sendNowWith(bus);
    }
}

