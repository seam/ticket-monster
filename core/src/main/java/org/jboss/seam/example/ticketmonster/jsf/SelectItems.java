package org.jboss.seam.example.ticketmonster.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.example.ticketmonster.model.EventCategory;
import org.jboss.seam.example.ticketmonster.model.Venue;

public class SelectItems
{

   public static List<SelectItem> convertCategories(List<EventCategory> categories)
   {
      List<SelectItem> result = new ArrayList<SelectItem>();
      for (EventCategory category : categories)
      {
         result.add(new SelectItem(category, category.getDescription()));
      }
      return result;
   }
   
   public static List<SelectItem> convertVenues(List<Venue> venues)
   {
      List<SelectItem> result = new ArrayList<SelectItem>();
      for (Venue venue : venues)
      {
         result.add(new SelectItem(venue, venue.getName()));
      }
      return result;
   }

}
