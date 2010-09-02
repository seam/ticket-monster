package org.jboss.seam.example.ticketmonster.action;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Event;

/**
 * Provides details for an event.
 * 
 * @author Shane Bryzak
 *
 */
public @Model class EventDetail
{
   @Inject EntityManager entityManager;
   
   private Long eventId;
   
   private Event event;
   
   public Long getEventId()
   {
      return eventId;
   }
   
   public void setEventId(Long eventId)
   {
      this.eventId = eventId;
   }
   
   private void loadEvent()
   {
      event = entityManager.find(Event.class, eventId);
   }
   
   public Event getEvent()
   {
      if (event == null) loadEvent();
      return event;
   }
}
