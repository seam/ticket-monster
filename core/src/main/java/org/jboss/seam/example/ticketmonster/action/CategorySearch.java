package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
      categories = entityManager.createQuery("select c from EventCategory c").getResultList();
   }

   public List<EventCategory> getCategories()
   {
      if (categories == null)
      {
         loadCategories();
      }
      return categories;
   }

}
