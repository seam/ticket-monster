package org.jboss.seam.example.ticketmonster;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.infinispan.AdvancedCache;
import org.jboss.seam.example.ticketmonster.dto.RowAllocation;
import org.jboss.seam.example.ticketmonster.model.SectionRow;
import org.jboss.seam.example.ticketmonster.model.Show;
import org.jboss.seam.example.ticketmonster.qualifier.RowCache;

/**
 * Magically coordinates all the ticket booking logic  
 * 
 * @author Shane Bryzak
 *
 */
public @ApplicationScoped class BookingManager
{
   @Inject @RowCache AdvancedCache<String,RowAllocation> rowCache;
   @Inject EntityManager entityManager;
   
   private String getRowKey(Show show, SectionRow row)
   {
      return show.getId() + ":" + row.getId();
   }
   
   public RowAllocation getRowAllocation(Show show, SectionRow row)
   {
      String key = getRowKey(show, row);
      if (!rowCache.containsKey(key)) loadRowAllocation(key);
      return rowCache.get(key);
   }
   
   private void loadRowAllocation(String key)
   {
      rowCache.lock(key);
      if (!rowCache.containsKey(key))
      {
         // Load the row allocation here
      }
   }
   
}
