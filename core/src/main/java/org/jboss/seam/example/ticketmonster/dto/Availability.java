package org.jboss.seam.example.ticketmonster.dto;

import java.util.List;

import org.jboss.seam.example.ticketmonster.model.PriceCategory;

/**
 * DTO for transferring event availability info
 * 
 * @author Shane Bryzak
 *
 */
public class Availability
{
   private List<PriceCategory> pricing;
   private int maxSeats;
   private String description;
   
   public Availability(List<PriceCategory> pricing, int maxSeats, String description)
   {
      this.pricing = pricing;
      this.maxSeats = maxSeats;
      this.description = description;
   }
   
   public List<PriceCategory> getPricing()
   {
      return pricing;
   }
   
   public int getMaxSeats()
   {
      return maxSeats;
   }
   
   public String getDescription()
   {
      return description;
   }
}
