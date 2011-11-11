package org.jboss.seam.example.ticketmonster.action;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.example.ticketmonster.dto.RowAllocation;
import org.jboss.seam.example.ticketmonster.dto.SectionAllocation;
import org.jboss.seam.example.ticketmonster.model.Allocation;
import org.jboss.seam.example.ticketmonster.model.Section;
import org.jboss.seam.example.ticketmonster.model.SectionRow;
import org.jboss.seam.example.ticketmonster.model.Show;
import org.jboss.seam.example.ticketmonster.qualifier.RowCache;
import org.jboss.seam.example.ticketmonster.qualifier.SectionCache;
import org.jboss.seam.example.ticketmonster.qualifier.SectionRowCache;
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
   
   //@Inject @SectionRowCache AdvancedCache<String, List<SectionRow>> sectionRowCache;
   
   @PersistenceContext EntityManager entityManager;
   
   private String getRowKey(Show show, SectionRow row)
   {
      return show.getId() + ":" + row.getId();
   }
      
   public RowAllocation getRowAllocation(Show show, SectionRow row)
   {
      final String key = getRowKey(show, row);
      //if (!rowCache.containsKey(key)) loadRowAllocation(key, show, row);
      //return rowCache.get(key);
      return null;
   }
   
   private void loadRowAllocation(String key, Show show, SectionRow row)
   {
      //rowCache.lock(key);
      /*if (!rowCache.containsKey(key))
      {
         List<Allocation> allocations = entityManager.createQuery(
               "select a from Allocation a where a.show = :show and a.row = :row")
               .setParameter("show", show)
               .setParameter("row", row)
               .getResultList();
         
         RowAllocation rowAllocation = new RowAllocation(row.getCapacity(), allocations);                
         rowCache.put(key, rowAllocation);
      }*/
   }   
   
   private String getSectionKey(Show show, Section section)
   {
      return show.getId() + ":" + section.getId();
   }
   
   public String getSectionAvailability(Show show, Section section)
   {
      final String key = getSectionKey(show, section);
      
      //if (!sectionCache.containsKey(key)) loadSectionAllocation(key, show, section);
      //SectionAllocation sa = sectionCache.get(key);
      //return AvailabilityUtils.getAvailability(sa.getCapacity(), sa.getSeatsAvailable());
      return null;
   }

   public int getMaxSectionSeats(Show show, Section section)
   {
      final String key = getSectionKey(show, section);
      
      //if (!sectionCache.containsKey(key)) loadSectionAllocation(key, show, section);
            
      //SectionAllocation sa = sectionCache.get(key); 
      //int maxSeats = sa.getMaxSeats(); 
      /*
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
      return maxSeats; */
      
      return 0;
   }
   
   protected void loadSectionAllocation(String key, Show show, Section section)
   {
      // sectionCache.lock(key);
     /* if (!sectionCache.containsKey(key))
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
      }*/
   }
   
   protected List<SectionRow> findSectionRows(Long sectionId)
   {
      //String key = sectionId.toString();
      
      //if (!sectionRowCache.containsKey(key))
      //{
         //sectionRowCache.lock(sectionId);
         
         //if (!sectionRowCache.containsKey(key))
         //{
            List<SectionRow> rows = entityManager.createQuery(
                  "select r from SectionRow r where r.section.id = :id")
                  .setParameter("id", sectionId)
                  .getResultList();
           // sectionRowCache.put(key, rows);
         //}
      //}
      
      //return sectionRowCache.get(key);
      return rows;
   }
   
   public Allocation reserve(Show show, Section section, int quantity)
   {      
      List<SectionRow> rows = findSectionRows(section.getId());
      
      Random rnd = new Random(System.currentTimeMillis());
     
      Set<SectionRow> rowsTried = new HashSet<SectionRow>();
      int attempts = 0;
      int idx = 0;
      
      while (rowsTried.size() < rows.size())
      {
         SectionRow row = null;
         
         // We'll try up to 10 times to pick a row randomly
         if (attempts < 10)
         {
            row = rows.get(rnd.nextInt(rows.size()));
         }
         // Otherwise we'll iterate through all the rows
         else
         {
            row = rows.get(idx);
         }
         
         if (!rowsTried.contains(row))
         {
            attempts++;
            rowsTried.add(row);
            
            RowAllocation ra = getRowAllocation(show, row);
            if (ra.getMaxAvailable() >= quantity)
            {
               // Lock the row
               String key = getRowKey(show, row);
               //rowCache.lock(key);
               
               if (ra.getMaxAvailable() >= quantity)
               {
                  int startSeat = ra.reserve(quantity);
                  Allocation result = new Allocation();
                  result.setShow(show);
                  result.setStartSeat(startSeat);
                  result.setEndSeat(startSeat + quantity - 1);
                  result.setQuantity(quantity);
                  result.setRow(row);                  
                  return result;
               }
            }
         }
      }
      
      return null;
   }
   
}
