package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Venue;
import org.jboss.seam.example.ticketmonster.model.VenueLayout;
import org.jboss.seam.servlet.http.HttpParam;

/**
 * Provides venue layout search data
 * 
 * @author Shane Bryzak
 *
 */
public @Model class VenueLayoutSearch
{
   @Inject EntityManager entityManager;   
   @Inject @HttpParam("venueId") String venueId;
   
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
