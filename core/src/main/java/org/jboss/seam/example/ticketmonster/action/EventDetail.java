package org.jboss.seam.example.ticketmonster.action;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Event;
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
   
   
}
