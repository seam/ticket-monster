package org.jboss.seam.example.ticketmonster.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jboss.errai.bus.server.annotations.ExposeEntity;

/**
 * Picketlink IDM SPI class, stores users, groups, etc.
 * 
 * @author Shane Bryzak
 *
 */
@ExposeEntity
@Entity
public class IdentityObject implements Serializable
{
   private static final long serialVersionUID = 358942643388948968L;
   
   private Long id;
   private String name;
   private IdentityObjectType type;
   
   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }
   
   public void setId(Long id)
   {
      this.id = id;
   }
   
   public String getName()
   {
      return name;
   }
   
   public void setName(String name)
   {
      this.name = name;
   }
   
   @ManyToOne //@IdentityProperty(PropertyType.TYPE)
   @JoinColumn(name = "IDENTITY_OBJECT_TYPE_ID")
   public IdentityObjectType getType()
   {
      return type;
   }
   
   public void setType(IdentityObjectType type)
   {
      this.type = type;
   }

}
