package org.jboss.jdf.example.ticketmonster.dto;

import java.util.Date;
import java.util.List;

import org.jboss.jdf.example.ticketmonster.action.BookingManager;
import org.jboss.jdf.example.ticketmonster.model.Allocation;

/**
 * Represents the ticket allocation state for a single row at a show
 * 
 * @author Shane Bryzak
 *
 */
public class RowAllocation
{        
   public static final int RESERVATION_TIMEOUT_MS = 1000 * 60 * 5; // 5 minutes
   
   /**
    * Tracks the allocated seats
    */
   private boolean[] allocatedSeats;     
   
   private Date[] seatReservations;
   
   public RowAllocation(int capacity, List<Allocation> allocations)
   {
      this.allocatedSeats = new boolean[capacity];
      this.seatReservations = new Date[capacity];
      
      // Populate the seat allocations
      for (Allocation allocation : allocations)
      {
         int startSeat = Integer.valueOf(allocation.getStartSeat());
         int endSeat = Integer.valueOf(allocation.getEndSeat());         
         
         for (int i = startSeat; i <= endSeat; i++)
         {
            allocatedSeats[i - 1] = true;
         }
      }
   }
   
   protected void clearExpiredReservations()
   {
      long limit = System.currentTimeMillis() - RESERVATION_TIMEOUT_MS;
      
      for (int i = 0; i < seatReservations.length; i++)
      {
         if (seatReservations[i] != null && seatReservations[i].getTime() < limit)
         {
            seatReservations[i] = null;
         }
      }
   }

   public int getMaxAvailable()
   {
      clearExpiredReservations();
      
      int max = 0;
      
      int count = 0;
      for (int i = 0; i < allocatedSeats.length; i++)
      {
         if (!allocatedSeats[i] && seatReservations[i] == null) 
         {
            count++;
         }
         else
         {
            if (count > max) max = count;
            count = 0;
         }
      }
      
      if (count > max) max = count;

      return max > BookingManager.MAX_AVAILABLE_SEATS_LIMIT ? 
            BookingManager.MAX_AVAILABLE_SEATS_LIMIT : max;
   }
   
   /**
    * Attempts to reserve the specified quantity of seats.  This method is not
    * thread-safe! Only use it when cache entry containing this row allocation
    * is locked.
    * 
    * @param quantity
    * @return
    */
   public int reserve(int quantity)
   {
      clearExpiredReservations();
      
      // the "best" starting seat number, for the quantity of tickets selected
      // (basically, the set of seats closest to the centre of the row)
      int best = ((allocatedSeats.length - quantity) % 2 == 0) ? 
            ((allocatedSeats.length - quantity) / 2) :
            ((allocatedSeats.length - quantity - 1) / 2);
      
      int startIdx = -1;
      int bestScore = -1;
            
      for (int i = 0; i < (allocatedSeats.length - quantity + 1); i++)
      {
         boolean blockAvailable = true;
         
         for (int j = i; j < i + quantity; j++)
         {
            if (allocatedSeats[j] || seatReservations[j] != null)
            {
               blockAvailable = false;
               break;
            }
         }
         
         if (blockAvailable)
         {
            int score = Math.abs(best - i);
            
            if (bestScore == -1 || score < bestScore)
            {
               bestScore = score;
               startIdx = i + 1;
            }            
         }
      }
      
      if (startIdx != -1)
      {
         Date now = new Date();
         for (int i = 0; i < quantity; i++)
         {
            seatReservations[startIdx + i - 1] = now;
         }
      }
      
      return startIdx;
   }
   
   public void unreserve(int startSeat, int quantity)
   {
      clearExpiredReservations();
      
   }
   
   public void confirm(int startSeat, int quantity)
   {
      clearExpiredReservations();
      
   }
}
