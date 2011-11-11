package org.jboss.seam.example.ticketmonster.action;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.example.ticketmonster.model.Venue;

/**
 * Venue action bean
 * 
 * @author Shane Bryzak
 *
 */
public @Stateful @Named @ConversationScoped class VenueAction implements Serializable
{
   private static final long serialVersionUID = 2646300975197236221L;
  
   @Inject Conversation conversation;
   @PersistenceContext EntityManager entityManager;
   /*@Inject @RequestParam("venueId")*/ String venueId;
   
   private Venue venue;   
   
   public void createVenue()
   {
      conversation.begin();
      venue = new Venue();
   }
 
   public boolean isLoadVenue()
   {
      // Only load the venue if a venueId has been provided
      if (venue == null && venueId != null && conversation.isTransient())
      {      
         conversation.begin();      
         venue = entityManager.find(Venue.class, Long.valueOf(venueId));
      }
      
      // A hack, ignore this
      return false;
   }
   
   public String save()
   {
      if (venue.getId() != null)
      {
         entityManager.merge(venue);
      }
      else
      {
         entityManager.persist(venue);
      }      
      
      conversation.end();
      return "success";
   }
   
   public String cancel()
   {
      conversation.end();
      return "cancel";
   }
   
   public Venue getVenue()
   {
      return venue;
   }
   
}
