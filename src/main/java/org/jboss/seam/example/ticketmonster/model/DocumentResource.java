package org.jboss.seam.example.ticketmonster.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Stores resources such as images, that are associated with a single document.  
 * Different revisions of a document may embed these resources within their content.
 * 
 * @author Shane Bryzak
 *
 */
@Entity
public class DocumentResource implements Serializable
{
   private static final long serialVersionUID = -5416383824678077922L;
   
   private Long id;
   private Document document;
   private String title;
   private String resourceType;
   private byte[] data;
   
   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }
   
   public void setId(Long id)
   {
      this.id = id;
   }
   
   @ManyToOne @JoinColumn(name = "DOCUMENT_ID")
   public Document getDocument()
   {
      return document;
   }
   
   public void setDocument(Document document)
   {
      this.document = document;
   }
   
   public String getTitle()
   {
      return title;
   }
   
   public void setTitle(String title)
   {
      this.title = title;
   }
   
   public String getResourceType()
   {
      return resourceType;
   }
   
   public void setResourceType(String resourceType)
   {
      this.resourceType = resourceType;
   }
   
   @Lob
   public byte[] getData()
   {
      return data;
   }
   
   public void setData(byte[] data)
   {
      this.data = data;
   }
}
