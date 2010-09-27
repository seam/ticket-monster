package org.jboss.seam.example.ticketmonster.dto;

/**
 * Tracks section allocation state
 * 
 * @author Shane Bryzak
 *
 */
public class SectionAllocation
{
   private int capacity;
   private int seatsAvailable;
   
   private int maxSeats;
   
   public SectionAllocation(int capacity, int seatsAvailable)
   {
      this.capacity = capacity;
      this.seatsAvailable = seatsAvailable;
   }
   
   public int getCapacity()
   {
      return capacity;
   }
   
   public int getSeatsAvailable()
   {
      return seatsAvailable;
   }
   
   public int getMaxSeats()
   {
      return maxSeats;
   }
}
