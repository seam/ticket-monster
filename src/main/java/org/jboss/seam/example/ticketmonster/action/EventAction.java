package org.jboss.seam.example.ticketmonster.action;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.Venue;
import org.jboss.seam.persistence.transaction.Transactional;

/**
 * Event related operations
 * 
 * @author Shane Bryzak
 *
 */
public @Named @ConversationScoped class EventAction implements Serializable
{
   private static final long serialVersionUID = 1735221845795268934L;
   
   @Inject EntityManager entityManager;
   @Inject Conversation conversation;
   
   private Event event;
   private Long eventId;
   
   public void createEvent()
   {
      conversation.begin();
      event = new Event();
   }
   
   public void loadEvent()
   {
      // Only load the venue if a venueId has been provided
      if (eventId != null)
      {      
         conversation.begin();      
         event = entityManager.find(Event.class, eventId);
      }
   }   
   
   @Transactional
   public String save()
   {
      if (event.getId() != null)
      {
         entityManager.merge(event);
      }
      else
      {
         entityManager.persist(event);
      }
      
      conversation.end();
      return "success";
   }
   
   public void cancel()
   {
      conversation.end();
   }   

   public Event getEvent()
   {
      return event;
   }
   
   public Long getEventId()
   {
      return eventId;
   }
   
   public void setEventId(Long eventId)
   {
      this.eventId = eventId;
   }
}
