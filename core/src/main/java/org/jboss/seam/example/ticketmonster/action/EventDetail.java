package org.jboss.seam.example.ticketmonster.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.dto.Availability;
import org.jboss.seam.example.ticketmonster.model.Allocation;
import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.PriceCategory;
import org.jboss.seam.example.ticketmonster.model.Section;
import org.jboss.seam.example.ticketmonster.model.Show;
import org.jboss.seam.example.ticketmonster.model.Venue;
import org.jboss.seam.remoting.annotations.WebRemote;
import org.jboss.seam.servlet.http.HttpParam;

/**
 * Provides data for the event booking screen.
 * 
 * @author Shane Bryzak
 *
 */
public @Model class EventDetail
{
   @Inject EntityManager entityManager;   
   @Inject @HttpParam("eventId") String eventId;
   @Inject BookingManager bookingManager;
   
   @Inject Instance<EventBooking> eventBooking;
     
   private Event event;
   private List<Venue> venues;
        
   private void loadEvent()
   {
      Long id = eventId != null ? Long.valueOf(eventId) : null;
      event = entityManager.find(Event.class, id);
   }
   
   public Event getEvent()
   {
      if (event == null) loadEvent();
      return event;
   }

   @SuppressWarnings("unchecked")
   public List<Venue> getVenues()
   {
      if (venues == null)
      {
         venues = new ArrayList<Venue>();
         
         for (Show show : (List<Show>) entityManager.createQuery(
               "select s from Show s where s.event = :event")
               .setParameter("event", event)
               .getResultList())
         {
            if (!venues.contains(show.getVenue())) venues.add(show.getVenue());
         }
      }
      
      return venues;
   }
   
   @WebRemote
   @SuppressWarnings("unchecked")
   public List<Show> getShows(Long eventId, Long venueId)
   {
      return entityManager.createQuery(
         "select s from Show s where s.event.id = :eventId and s.venue.id = :venueId")
         .setParameter("eventId", eventId)
         .setParameter("venueId", venueId)
         .getResultList();
   }
   
   @WebRemote
   @SuppressWarnings("unchecked")
   public Map<Section, Availability> getAvailability(Long showId)
   {
      Show show = entityManager.find(Show.class, showId);
      
      Map<Section, Availability> availability = new HashMap<Section, Availability>();
            
      List<PriceCategory> cats = entityManager.createQuery(
         "select pc from PriceCategory pc where pc.event = :event and pc.venue = :venue")
         .setParameter("event", show.getEvent())
         .setParameter("venue", show.getVenue())
         .getResultList();
      
      for (PriceCategory cat : cats)
      {
         if (!availability.containsKey(cat.getSection()))
         {
            int maxSeats = bookingManager.getMaxSectionSeats(show, cat.getSection());                        
            
            String description = bookingManager.getSectionAvailability(show, cat.getSection());
            
            availability.put(cat.getSection(), new Availability(
                  new ArrayList<PriceCategory>(), maxSeats, description));
         }
         availability.get(cat.getSection()).getPricing().add(cat);
      }
      
      return availability;
   }      
   
   /**
    * 
    * @param showId
    * @param sectionId
    * @param quantities A map containing [price category ID:quantity] pairs
    * @return
    */
   @WebRemote
   public boolean bookSeats(Long showId, Long sectionId, Map<Long,Integer> quantities)
   {
      Show show = entityManager.find(Show.class, showId);
      Section section = entityManager.find(Section.class, sectionId);
      
      int qty = 0;
      for (Long key : quantities.keySet())
      {
         qty += quantities.get(key);
      }
      
      Allocation allocation = bookingManager.reserve(show, section, qty);
      
      if (allocation != null)
      {
         //eventBooking.get().createBooking(allocation, quantities);
      }
      
      return allocation != null;
   }
}
