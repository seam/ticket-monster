package org.jboss.seam.example.ticketmonster.action;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.servlet.http.HttpParam;

/**
 * Provides details for an event.
 * 
 * @author Shane Bryzak
 *
 */
public @Model class EventDetail
{
   @Inject EntityManager entityManager;   
   @Inject @HttpParam("eventId") String eventId;
     
   private Event event;
     
   private void loadEvent()
   {
      Long id = eventId != null ? Long.valueOf(eventId) : null;
      event = entityManager.find(Event.class, id);
   }
   
   public Event getEvent()
   {
      if (event == null) loadEvent();
      return event;
   }
}
