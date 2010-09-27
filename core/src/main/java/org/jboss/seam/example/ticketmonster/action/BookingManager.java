package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.infinispan.AdvancedCache;
import org.jboss.seam.example.ticketmonster.dto.RowAllocation;
import org.jboss.seam.example.ticketmonster.dto.SectionAllocation;
import org.jboss.seam.example.ticketmonster.model.Allocation;
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
   public static final int MAX_AVAILABLE_SEATS_LIMIT = 10;
   
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
      if (!rowCache.containsKey(key)) loadRowAllocation(key, show, row);
      return rowCache.get(key);
   }
   
   private void loadRowAllocation(String key, Show show, SectionRow row)
   {
      //rowCache.lock(key);
      if (!rowCache.containsKey(key))
      {
         List<Allocation> allocations = entityManager.createQuery(
               "select a from Allocation a where a.show = :show and a.row = :row")
               .setParameter("show", show)
               .setParameter("row", row)
               .getResultList();
         
         RowAllocation rowAllocation = new RowAllocation(row.getCapacity(), allocations);                
         rowCache.put(key, rowAllocation);
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
            
      SectionAllocation sa = sectionCache.get(key); 
      int maxSeats = sa.getMaxSeats(); 
      
      if (maxSeats == -1)
      {
         // sectionCache.lock(key);
         List<SectionRow> rows = entityManager.createQuery(
               "select r from SectionRow r where r.section = :section")
               .setParameter("section", section)
               .getResultList();
         
         for (SectionRow row : rows)
         {
            RowAllocation ra = getRowAllocation(show, row);
            if (maxSeats == -1 || ra.getMaxAvailable() > maxSeats)
            {
               maxSeats = ra.getMaxAvailable();
            }
            
            if (maxSeats >= MAX_AVAILABLE_SEATS_LIMIT) break; 
         }         

         sa.setMaxSeats((maxSeats >= MAX_AVAILABLE_SEATS_LIMIT) ? 
               MAX_AVAILABLE_SEATS_LIMIT : maxSeats);
         
         maxSeats = sa.getMaxSeats();
      }
      return maxSeats;
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
         
         int available = section.getCapacity() - (allocated != null ? allocated.intValue() : 0);
         
         SectionAllocation sa = new SectionAllocation(section.getCapacity(), 
               available);
         
         sectionCache.put(key, sa);
      }
   }
   
}
