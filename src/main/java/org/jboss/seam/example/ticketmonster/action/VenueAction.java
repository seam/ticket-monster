package org.jboss.seam.example.ticketmonster.action;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Venue;

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
   
   private Long venueId;
   private Venue venue;   
 
   public void loadVenue()
   {
      conversation.begin();
      
      venue = entityManager.find(Venue.class, venueId); 
   }
   
   public Venue getVenue()
   {
      return venue;
   }
   
   public Long getVenueId()
   {
      return venueId;
   }
   
   public void setVenueId(Long venueId)
   {
      this.venueId = venueId;
   }
   
}
