package org.jboss.seam.example.ticketmonster.util;

import java.io.IOException;

import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.example.ticketmonster.action.DocumentResourceSearch;
import org.jboss.seam.example.ticketmonster.model.DocumentResource;
import org.jboss.weld.extensions.beanManager.BeanManagerAccessor;

/**
 * This servlet serves document resources
 * 
 * @author Shane Bryzak
 *
 */
public class DocumentResourceServlet extends HttpServlet
{
   private static final long serialVersionUID = -6136834513054052813L;
   
   // Not working in EAP 5.1, see WELD-665
   @Inject DocumentResourceSearch resourceSearch;
   
   
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
      String[] parts = request.getRequestURI().split("/");
      
      if (parts.length < 2)
      {
         response.sendError(404, "Resource not found - document ID and key must be specified");
         return;
      }
      
      
      String contentType = null;
      byte[] data = null;
      
      try
      {
         Long docId = Long.parseLong(parts[parts.length - 2]);
         
         String key = parts[parts.length - 1];                    
           
         if (docId != null && key != null)
         {
            DocumentResource resource = getResourceSearch().findResourceByKey(Long.valueOf(docId), key);
            if (resource != null)
            {
               contentType = resource.getContentType();
               data = resource.getData();
            }
         }         
      }
      catch (NumberFormatException ex)
      {
         response.sendError(500, "Invalid document ID specified");
         return;
      }

           
      if (data != null)
      {
         response.setContentType(contentType);
         response.getOutputStream().write(data);
         response.getOutputStream().flush();
      }
      else
      {
         response.sendError(404, "Resource not found");
      }

           
   }
   
   private DocumentResourceSearch getResourceSearch()
   {
      if (resourceSearch != null)
      {
         return resourceSearch;
      }
      BeanManager beanManager = BeanManagerAccessor.getManager();
      Bean<?> bean = beanManager.resolve(beanManager.getBeans(DocumentResourceSearch.class));
      if (bean == null)
      {
         throw new UnsatisfiedResolutionException("Unable to resolve @Default DocumentResourceSearch");
      }
      return DocumentResourceSearch.class.cast(beanManager.getReference(bean, DocumentResourceSearch.class, beanManager.createCreationalContext(bean)));
   }
}
