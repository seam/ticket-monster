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

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/*import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.MessageCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.framework.MessageBus;
import org.jboss.errai.bus.server.annotations.Service;*/
import org.jboss.seam.example.ticketmonster.action.CategorySearch;
import org.jboss.seam.example.ticketmonster.model.EventCategory;

/**
 * @author: Heiko Braun <hbraun@redhat.com>
 * @date: Sep 10, 2010
 */

//@Service
@RequestScoped
@Stateful
public class CategoryManagement { //implements MessageCallback {

    @Inject
    CategorySearch catSearch;

    //@Inject
    //MessageBus bus;

    @PersistenceContext
    EntityManager entityManager;

/*    public void callback(Message message) {

        switch (Crud.valueOf(message.getCommandType()))
        {
            case READ:
                List<EventCategory> value = catSearch.getCategories();
                MessageBuilder.createConversation(message)
                        .subjectProvided()
                        .command(Crud.READ)
                        .with("categories", value)
                        .done()
                        .sendNowWith(bus);                
                break;
            case CREATE:
                createCatgory(message.get(String.class, "description"));
                break;
            case DELETE:
                deleteCatgory(message.get(String.class, "description"));
                break;
            default:
                throw new IllegalArgumentException("Unknown command "+ message.getCommandType());
        }

    }*/

    private void createCatgory(String name)
    {
        EventCategory c = new EventCategory();
        c.setDescription(name);

        entityManager.persist(c);
    }

    private void deleteCatgory(String name)
    {
        Query query = entityManager.createQuery("from EventCategory where description=?1");
        query.setParameter(1, name);

        EventCategory c = (EventCategory)query.getSingleResult();
        entityManager.remove(c);
    }    
        
}

