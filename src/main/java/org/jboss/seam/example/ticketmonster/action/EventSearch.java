package org.jboss.seam.example.ticketmonster.action;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Document;
import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.Revision;

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
      
      events = new ArrayList<Event>();
      
      Event e = new Event();
      
      Revision r = new Revision();
      r.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur imperdiet libero vel mi tincidunt sed dapibus tellus pretium. Nam a massa mi, id egestas dui. Mauris orci magna, congue vitae sodales quis, congue nec odio. In sagittis pretium malesuada. Vivamus eu nunc at lectus sodales fermentum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec dictum bibendum tincidunt. Nam ut dolor mauris. Sed massa orci, viverra non tristique eget, laoreet ut eros. Integer ante sapien, porta quis tincidunt vitae, varius quis elit. Nullam mi nisi, convallis sit amet consectetur dictum, scelerisque ut nisl. Etiam at erat eu diam lacinia mattis et sed ipsum.");
      Document d = new Document();
      d.setActiveRevision(r);
      
      e.setName("Cirque de Test");
      e.setDescription(d);
      
      events.add(e);
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
