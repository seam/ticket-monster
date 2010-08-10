package org.jboss.seam.example.ticketmonster.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Used to store rich text entries used for venue descriptions, event descriptions,
 * front page announcements, etc.
 * 
 * @author Shane Bryzak
 *
 */
public class Document
{
   private Long id;
   private Date created;
   private String createdBy;
   private Date modified;
   private String modifiedBy;
   private Revision activeRevision;
   
   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }
   
   public void setId(Long id)
   {
      this.id = id;
   }

   public Date getCreated()
   {
      return created;
   }

   public void setCreated(Date created)
   {
      this.created = created;
   }

   public String getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(String createdBy)
   {
      this.createdBy = createdBy;
   }

   public Date getModified()
   {
      return modified;
   }

   public void setModified(Date modified)
   {
      this.modified = modified;
   }

   public String getModifiedBy()
   {
      return modifiedBy;
   }

   public void setModifiedBy(String modifiedBy)
   {
      this.modifiedBy = modifiedBy;
   }

   @OneToMany @JoinColumn(name = "REVISION_ID")
   public Revision getActiveRevision()
   {
      return activeRevision;
   }

   public void setActiveRevision(Revision activeRevision)
   {
      this.activeRevision = activeRevision;
   }
   
   
}
