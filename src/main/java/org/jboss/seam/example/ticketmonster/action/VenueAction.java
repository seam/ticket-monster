package org.jboss.seam.example.ticketmonster.action;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Venue;
import org.jboss.seam.persistence.transaction.Transactional;
import org.jboss.seam.servlet.http.HttpParam;

/**
 * Venue action bean
 * 
 * @author Shane Bryzak
 *
 */
public @Named @ConversationScoped class VenueAction implements Serializable
{
   private static final long serialVersionUID = 2646300975197236221L;
  
   @Inject Conversation conversation;
   @Inject EntityManager entityManager;
   @Inject @HttpParam("venueId") String venueId;
   
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
   
   public @Transactional String save()
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
   
   public void cancel()
   {
      conversation.end();
   }
   
   public Venue getVenue()
   {
      return venue;
   }
   
}
