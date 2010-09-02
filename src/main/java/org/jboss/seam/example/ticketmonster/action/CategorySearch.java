package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
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
      categories = entityManager.createQuery("select c from EventCategory c").getResultList();         
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
