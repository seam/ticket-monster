package org.jboss.jdf.example.ticketmonster.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.jdf.example.ticketmonster.model.Allocation;
import org.jboss.jdf.example.ticketmonster.model.PriceCategory;

/**
 * Handles the event booking process after the temporary seat allocation has 
 * been granted
 * 
 * @author Shane Bryzak
 *
 */
public @ConversationScoped @Named class EventBooking implements Serializable
{
   @Inject Conversation conversation;
   @PersistenceContext EntityManager entityManager;
   
   private Allocation allocation;
   
   private Map<PriceCategory, Integer> tickets;
   
   public void createBooking(Allocation allocation, Map<Long,Integer> quantities)
   {
      this.allocation = allocation;
      
      tickets = new HashMap<PriceCategory, Integer>();
      
      for (Long categoryId : quantities.keySet())
      {
         PriceCategory cat = entityManager.find(PriceCategory.class, categoryId);
         tickets.put(cat, quantities.get(categoryId));
      }
      
      conversation.begin();
   }
   
   public Map<PriceCategory,Integer> getTickets()
   {
      return tickets;
   }
   
   
}
