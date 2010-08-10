package org.jboss.seam.example.ticketmonster.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Used to store rich text entries used for venue descriptions, event descriptions,
 * front page announcements, etc. A document may have multiple revisions, only
 * one of which is the 'active' revision.  This allows document changes to
 * go through an approval process before being made live.
 * 
 * @author Shane Bryzak
 *
 */
public class Document
{
   private Long id;
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
