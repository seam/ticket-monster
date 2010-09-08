package org.jboss.seam.example.ticketmonster.model;

import org.jboss.errai.bus.server.annotations.ExposeEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Seat allocations for a show. An allocation consists of one or more seats
 * within a SectionRow.
 * 
 * @author Shane Bryzak
 *
 */
@ExposeEntity
@Entity
public class Allocation implements Serializable
{
   private static final long serialVersionUID = 8738724150877088864L;
   
   private Long id;
   private Date assigned;
   
   /**
    * Indicates this is a temporary allocation, while the user enters payment
    * information.  This generally must be done within a short timeframe.
    */
   private boolean temporary;
   
   private User user;
   private Show show;
   private SectionRow row;
   private int quantity;
   private String startSeat;
   private String endSeat;
   
   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }
   
   public void setId(Long id)
   {
      this.id = id;
   }
   
   public Date getAssigned()
   {
      return assigned;
   }
   
   public void setAssigned(Date assigned)
   {
      this.assigned = assigned;
   }
   
   public boolean isTemporary()
   {
      return temporary;
   }
   
   public void setTemporary(boolean temporary)
   {
      this.temporary = temporary;
   }
   
   @ManyToOne
   public User getUser()
   {
      return user;
   }
   
   public void setUser(User user)
   {
      this.user = user;
   }
   
   @ManyToOne
   public Show getShow()
   {
      return show;
   }
   
   public void setShow(Show show)
   {
      this.show = show;
   }
   
   @ManyToOne
   public SectionRow getRow()
   {
      return row;
   }
   
   public void setRow(SectionRow row)
   {
      this.row = row;
   }
   
   public int getQuantity()
   {
      return quantity;
   }
   
   public void setQuantity(int quantity)
   {
      this.quantity = quantity;
   }
   
   public String getStartSeat()
   {
      return startSeat;
   }
   
   public void setStartSeat(String startSeat)
   {
      this.startSeat = startSeat;
   }
   
   public String getEndSeat()
   {
      return endSeat;
   }
   
   public void setEndSeat(String endSeat)
   {
      this.endSeat = endSeat;
   }
}
