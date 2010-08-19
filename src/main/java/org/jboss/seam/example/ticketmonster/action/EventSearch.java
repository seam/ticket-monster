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

import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.EventCategory;
import org.jboss.seam.example.ticketmonster.model.meta.Event_;

/**
 * Event search actions handled here
 * 
 * @author Shane Bryzak
 *
 */
public @Model class EventSearch
{
   @Inject EntityManager entityManager;
   
   private Long categoryId;
   
   private List<Event> events;
   
   public void loadEvents()
   {
      // If there is no categoryId set, load major events
      
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();      
      CriteriaQuery<Event> criteria = builder.createQuery(Event.class);
      Root<Event> event = criteria.from(Event.class);
      
      List<Predicate> predicates = new ArrayList<Predicate>();
      
      if (categoryId == null)
      {
         predicates.add(builder.equal(event.get(Event_.major),
               true));
      }
      else
      {
         predicates.add(builder.equal(event.get(Event_.category), 
               lookupCategory(categoryId)));
      }
      
      criteria.where(predicates.toArray(new Predicate[0]));
      
      events = entityManager.createQuery(criteria).getResultList();            
   }
   
   public EventCategory lookupCategory(Long categoryId)
   {
      return entityManager.find(EventCategory.class, categoryId);
   }
   
   public List<Event> getEvents()
   {
      return events;
   }

   public Long getCategoryId()
   {
      return categoryId;
   }
   
   public void setCategoryId(Long categoryId)
   {
      this.categoryId = categoryId;
   }
}
