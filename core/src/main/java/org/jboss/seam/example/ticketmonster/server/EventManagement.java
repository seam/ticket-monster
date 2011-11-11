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

/*import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.MessageCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.framework.MessageBus;
import org.jboss.errai.bus.server.annotations.Service;*/
import org.jboss.seam.example.ticketmonster.action.BaseEventSearch;
import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.EventCategory;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 30, 2010
 */
//@Service
@RequestScoped
@Stateful
public class EventManagement {//implements MessageCallback {

    //@Inject
    //MessageBus bus;
    
    @Inject @Named("BaseSearch")
    BaseEventSearch eventSearch;

    @PersistenceContext
    EntityManager entityManager;
    
    /*public void callback(Message message) {

        switch(Crud.valueOf(message.getCommandType()))
        {
            case READ:
                String description = message.get(String.class, "category");
                List<Event> events = eventSearch.getEventsByCategory(
                        categoryForName(description).getId()
                );
                
                MessageBuilder.createConversation(message)
                        .subjectProvided()
                        .command(Crud.READ)
                        .with("events", events)
                        .noErrorHandling()
                        .sendNowWith(bus);

                break;
        }
    }*/

    private EventCategory categoryForName(String name)
    {
        Query query = entityManager.createQuery("from EventCategory where description=?1");
        query.setParameter(1, name);
        return (EventCategory)query.getSingleResult();
    }

}
