package org.jboss.seam.example.ticketmonster.action;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.Show;
import org.jboss.seam.example.ticketmonster.model.Venue;
import org.jboss.seam.servlet.http.HttpParam;

/**
 * Provides details for an event.
 * 
 * @author Shane Bryzak
 *
 */
public @Model class EventDetail
{
   @Inject EntityManager entityManager;   
   @Inject @HttpParam("eventId") String eventId;
     
   private Event event;
   
   private Venue selectedVenue;
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
   
   public Venue getSelectedVenue()
   {
      return selectedVenue;
   }
   
   public void setSelectedVenue(Venue selectedVenue)
   {
      this.selectedVenue = selectedVenue;
   }
   
   public List<Venue> getVenues()
   {
      if (venues == null)
      {
         venues = new ArrayList<Venue>();
         @SuppressWarnings("unchecked")
         List<Show> shows = entityManager.createQuery("select s from Show s where s.event = :event")
            .setParameter("event", event)
            .getResultList();         
         
         for (Show show : shows)
         {
            if (!venues.contains(show.getVenue())) venues.add(show.getVenue());
         }
      }
      return venues;
   }
   
   
}
