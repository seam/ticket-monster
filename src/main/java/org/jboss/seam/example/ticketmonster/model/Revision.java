package org.jboss.seam.example.ticketmonster.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Stores a single revision of a document.  A document may have multiple
 * revisions, only one of them being active at one time.  This allows the
 * document to go through a drafting/approval process before any new revisions
 * are made "live".
 * 
 * @author Shane Bryzak
 *
 */
public class Revision implements Serializable
{
   private static final long serialVersionUID = 6197879518040782042L;

   private Long id;
   private Date created;
   private String createdBy;
   private String content;
   
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
   
   public String getContent()
   {
      return content;
   }
   
   public void setContent(String content)
   {
      this.content = content;
   }
}
