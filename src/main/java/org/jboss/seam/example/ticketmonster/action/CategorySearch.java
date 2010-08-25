package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
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
   
   private class CategoryConverter implements Converter
   {
      @Override
      public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
      {
         return entityManager.find(EventCategory.class, Long.valueOf(arg2));
      }

      @Override
      public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
      {
         return ((EventCategory) arg2).getId().toString();
      }      
   }
   
   private CategoryConverter converter;
   
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
   
   public Converter getConverter()
   {
      if (converter == null) converter = new CategoryConverter();
      return converter;
   }
}
