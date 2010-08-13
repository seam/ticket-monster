package org.jboss.seam.example.ticketmonster.producer;

import javax.enterprise.context.RequestScoped;
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
public class EntityManagerProducer
{
   @PersistenceUnit
   @RequestScoped
   @Produces
   @SeamManaged
   EntityManagerFactory emf; 
}
