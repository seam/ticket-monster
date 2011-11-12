package org.jboss.jdf.example.ticketmonster.util;

/**
 * 
 * @author Shane Bryzak
 *
 */
public class AvailabilityUtils
{
   public static final String getAvailability(int capacity, int available)
   {
      double percent = (available * 1.0) / (capacity * 1.0) * 100.0;
      
      if (percent >= 40)
      {
         return "Tickets available";
      }
      else if (percent >= 10)
      {
         return "Limited tickets available";
      }
      else if (percent >= 0)
      {
         return "Almost sold out";
      }
      else
      {
         return "Sold out";
      }
         
   }
}
