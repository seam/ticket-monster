package org.jboss.seam.example.ticketmonster.action;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.seam.example.ticketmonster.model.Venue;

/**
 * Venue search actions
 * 
 * @author Shane Bryzak
 *
 */
public @Model class VenueSearch
{
   @Inject EntityManager entityManager;
   
   private List<Venue> venues;
   
   private void loadVenues()
   {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();      
      CriteriaQuery<Venue> query = builder.createQuery(Venue.class);

      Root<Venue> venue = query.from(Venue.class);      
      //List<Predicate> predicates = new ArrayList<Predicate>();
            
      venues = entityManager.createQuery(query).getResultList();         
   }
   
   public List<Venue> getVenues()
   {
      if (venues == null) loadVenues();
      return venues;
   }
}
