package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.EventCategory;

/**
 * Event search actions handled here
 * 
 * @author Shane Bryzak
 *
 */
public @Model class EventSearch
{
   @Inject EntityManager entityManager;
      
   private List<Event> events;
   
   private void loadEvents()
   {
      Object r = FacesContext.getCurrentInstance().getExternalContext().getRequest();
      
      Query query;
      
            		
      Long categoryId = 123L;
      if (categoryId != null)
      {
         query = entityManager.createQuery(
            "select e from Event e where e.category = :category")
            .setParameter("category", lookupCategory(categoryId));
      }
      else
      {
         query = entityManager.createQuery(
            "select e from Event e where e.major = true");
      }
           
      events = query.getResultList();            
   }
   
   @SuppressWarnings("unchecked")
   private void loadAllEvents()
   {
      events = (List<Event>) entityManager.createQuery("select e from Event e").getResultList();
   }
   
   public EventCategory lookupCategory(Long categoryId)
   {
      return entityManager.find(EventCategory.class, categoryId);
   }
   
   public List<Event> getEvents()
   {
      if (events == null) loadEvents();
      return events;
   }
   
   public List<Event> getAllEvents()
   {
      if (events == null) loadAllEvents();
      return events;
   }
}
