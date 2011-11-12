package org.jboss.jdf.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.jdf.example.ticketmonster.model.Venue;
import org.jboss.jdf.example.ticketmonster.model.VenueLayout;

/**
 * Provides venue layout search data
 * 
 * @author Shane Bryzak
 *
 */
public @Model class VenueLayoutSearch
{
    @PersistenceContext EntityManager entityManager;   
   /*@Inject @RequestParam("venueId")*/ String venueId;
   
   private Venue venue;
   private List<VenueLayout> layouts;
   
   @SuppressWarnings("unchecked")
   private void loadLayouts()
   {
      venue = entityManager.find(Venue.class, Long.valueOf(venueId));
      
      layouts = entityManager.createQuery(
            "select l from VenueLayout l where l.venue = :venue")
            .setParameter("venue", venue)
            .getResultList();
   }
   
   public List<VenueLayout> getLayouts()
   {
      if (layouts == null) loadLayouts();
      return layouts;
   }
   
   public Venue getVenue()
   {
      if (venue == null) loadLayouts();
      return venue;
   }
}
