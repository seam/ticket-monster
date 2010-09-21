package org.jboss.seam.example.ticketmonster.action;

import javax.enterprise.inject.Produces;

import org.infinispan.config.Configuration;
import org.jboss.seam.example.ticketmonster.qualifier.RowCache;
import org.jboss.seam.infinispan.Infinispan;

/**
 * Configures various caches used throughout the app.
 * 
 * @author Shane Bryzak
 * 
 */
public class CacheConfig
{
   @Produces
   @Infinispan("rowCache")
   @RowCache
   public Configuration getRowCacheConfiguration()
   {
      Configuration configuration = new Configuration();
      return configuration;
   }
}
