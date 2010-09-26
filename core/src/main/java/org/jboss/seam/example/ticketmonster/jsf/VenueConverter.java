/**
 *
 */
package org.jboss.seam.example.ticketmonster.jsf;

import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Venue;
import org.jboss.weld.extensions.beanManager.BeanManagerAware;

public class VenueConverter extends BeanManagerAware implements Converter
{
   
   public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
   {
      return getEnityManager().find(Venue.class, Long.valueOf(arg2));
   }

   public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
   {
      return ((Venue) arg2).getId().toString();
   }

   private EntityManager getEnityManager()
   {
      Bean<?> bean = getBeanManager().resolve(getBeanManager().getBeans(EntityManager.class));
      if (bean == null)
      {
         throw new UnsatisfiedResolutionException("Cannot resolve @Default EntityManager");
      }
      return (EntityManager) getBeanManager().getReference(bean, EntityManager.class, getBeanManager().createCreationalContext(bean));
   }

}