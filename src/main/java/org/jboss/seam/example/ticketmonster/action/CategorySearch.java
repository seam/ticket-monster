package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.seam.example.ticketmonster.model.EventCategory;

/**
 * Event Category search actions
 * 
 * @author Shane Bryzak
 *
 */
public @Model class CategorySearch
{
   @Inject EntityManager entityManager;
   
   private List<EventCategory> categories;
   
   private void loadCategories()
   {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();      
      CriteriaQuery<EventCategory> query = builder.createQuery(EventCategory.class);

      Root<EventCategory> venue = query.from(EventCategory.class);      
            
      categories = entityManager.createQuery(query).getResultList();         
   }
   
   public List<EventCategory> getCategories()
   {
      if (categories == null) loadCategories();
      return categories;
   }
}
