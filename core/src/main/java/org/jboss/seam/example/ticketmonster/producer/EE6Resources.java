package org.jboss.seam.example.ticketmonster.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.persistence.SeamManaged;

/**
 * Entity manager producer bean
 * 
 * @author Shane Bryzak
 *
 */
@Alternative
public class EE6Resources
{
   @PersistenceUnit
   @RequestScoped
   @Produces
   @SeamManaged
   @Alternative
   EntityManagerFactory emf; 
}
