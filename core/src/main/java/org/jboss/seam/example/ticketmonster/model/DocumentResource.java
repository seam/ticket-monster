package org.jboss.seam.example.ticketmonster.model;

import org.jboss.errai.bus.server.annotations.ExposeEntity;

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
@ExposeEntity
@Entity
public class DocumentResource implements Serializable
{
   private static final long serialVersionUID = -5416383824678077922L;
   
   private Long id;
   private Document document;
   private String key;
   private String contentType;
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
   
   public String getKey()
   {
      return key;
   }
   
   public void setKey(String key)
   {
      this.key = key;
   }
   
   public String getContentType()
   {
      return contentType;
   }
   
   public void setContentType(String contentType)
   {
      this.contentType = contentType;
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
