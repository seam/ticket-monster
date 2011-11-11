package org.jboss.seam.example.ticketmonster.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * @author Shane Bryzak
 */
@Entity
public class User implements Serializable
{
   private static final long serialVersionUID = -4501716573185869164L;
   
   private Long id;
   private IdentityObject identity;
   private String firstName;
   private String lastName;
   
   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }
   
   public void setId(Long id)
   {
      this.id = id;
   }
   
   @OneToOne
   public IdentityObject getIdentity()
   {
      return identity;
   }
   
   public void setIdentity(IdentityObject identity)
   {
      this.identity = identity;
   }
   
   public String getFirstName()
   {
      return firstName;
   }
   
   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }
   
   public String getLastName()
   {
      return lastName;
   }
   
   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }
}
