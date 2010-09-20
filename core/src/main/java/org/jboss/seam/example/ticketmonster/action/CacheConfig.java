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
   public @Produces @Infinispan("rowCache") @RowCache Configuration getRowCacheConfiguration()
   {
      Configuration configuration = new Configuration();
      return configuration;
   }   
}
