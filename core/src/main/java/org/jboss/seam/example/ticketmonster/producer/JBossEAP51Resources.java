package org.jboss.seam.example.ticketmonster.producer;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;

import org.jboss.seam.persistence.SeamManaged;

/**
 * Entity manager producer bean
 * 
 * @author Shane Bryzak
 *
 */
@Alternative
public class JBossEAP51Resources
{
   @Resource(mappedName="java:/ticketMonsterEntityManagerFactory")
   private EntityManagerFactory emf;
   
   
   @RequestScoped
   @Produces
   @SeamManaged
   @Alternative
   public EntityManagerFactory createEmf()
   {
      return emf;
   }
}
