package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.example.ticketmonster.model.Venue;

/**
 * Venue search actions
 * 
 * @author Shane Bryzak
 *
 */
public @Model class VenueSearch
{
   @PersistenceContext EntityManager entityManager;
   
   private List<Venue> venues;
   
   private void loadVenues()
   {           
      venues = entityManager.createQuery("select v from Venue v").getResultList();         
   }
   
   public List<Venue> getVenues()
   {
      if (venues == null) loadVenues();
      return venues;
   }
}
