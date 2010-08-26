package org.jboss.seam.example.ticketmonster.util;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.example.ticketmonster.action.DocumentResourceSearch;
import org.jboss.seam.example.ticketmonster.model.DocumentResource;

/**
 * This servlet serves document resources
 * 
 * @author Shane Bryzak
 *
 */
public class DocumentResourceServlet extends HttpServlet
{
   private static final long serialVersionUID = -6136834513054052813L;
   
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
            DocumentResource resource = resourceSearch.findResourceByKey(Long.valueOf(docId), key);
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
}
