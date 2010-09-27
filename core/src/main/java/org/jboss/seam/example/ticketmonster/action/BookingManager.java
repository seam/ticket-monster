package org.jboss.seam.example.ticketmonster.action;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.infinispan.AdvancedCache;
import org.jboss.seam.example.ticketmonster.dto.RowAllocation;
import org.jboss.seam.example.ticketmonster.dto.SectionAllocation;
import org.jboss.seam.example.ticketmonster.model.Section;
import org.jboss.seam.example.ticketmonster.model.SectionRow;
import org.jboss.seam.example.ticketmonster.model.Show;
import org.jboss.seam.example.ticketmonster.qualifier.RowCache;
import org.jboss.seam.example.ticketmonster.qualifier.SectionCache;
import org.jboss.seam.example.ticketmonster.util.AvailabilityUtils;

/**
 * Magically coordinates all the ticket booking logic  
 * 
 * @author Shane Bryzak
 *
 */
public @ApplicationScoped class BookingManager
{   
   private static final int MAX_AVAILABLE_SEATS_LIMIT = 10;
   
   @Inject @RowCache AdvancedCache<String,RowAllocation> rowCache;
   @Inject @SectionCache AdvancedCache<String,SectionAllocation> sectionCache;
   
   @Inject EntityManager entityManager;
   
   private String getRowKey(Show show, SectionRow row)
   {
      return show.getId() + ":" + row.getId();
   }
      
   public RowAllocation getRowAllocation(Show show, SectionRow row)
   {
      final String key = getRowKey(show, row);
      if (!rowCache.containsKey(key)) loadRowAllocation(key);
      return rowCache.get(key);
   }
   
   private void loadRowAllocation(String key)
   {
      //rowCache.lock(key);
      if (!rowCache.containsKey(key))
      {
         // Load the row allocation here
      }
   }   
   
   private String getSectionKey(Show show, Section section)
   {
      return show.getId() + ":" + section.getId();
   }
   
   public String getSectionAvailability(Show show, Section section)
   {
      final String key = getSectionKey(show, section);
      
      if (!sectionCache.containsKey(key)) loadSectionAllocation(key, show, section);
      SectionAllocation sa = sectionCache.get(key);
      return AvailabilityUtils.getAvailability(sa.getCapacity(), sa.getSeatsAvailable());
   }

   public int getMaxSectionSeats(Show show, Section section)
   {
      final String key = getSectionKey(show, section);
      
      if (!sectionCache.containsKey(key)) loadSectionAllocation(key, show, section);
            
      return sectionCache.get(key).getMaxSeats();
   }
   
   protected void loadSectionAllocation(String key, Show show, Section section)
   {
      // sectionCache.lock(key);
      if (!sectionCache.containsKey(key))
      {
         Long allocated = (Long) entityManager.createQuery("select sum(a.quantity) from Allocation a " +
               "where a.show = :show and a.row.section = :section")
               .setParameter("show", show)
               .setParameter("section", section)
               .getSingleResult();
         
         SectionAllocation sa = new SectionAllocation(section.getCapacity(), 
               section.getCapacity() - (allocated != null ? allocated.intValue() : 0));
         
         sectionCache.put(key, sa);
      }
   }
   
}
