package org.jboss.jdf.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.jboss.jdf.example.ticketmonster.model.DocumentResource;

/**
 * Used to locate document resources.
 * 
 * @author Shane Bryzak
 *
 */
public @Model class DocumentResourceSearch
{
    @PersistenceContext EntityManager entityManager;
   
   public DocumentResource findResourceByKey(Long docId, String key)
   {
      try
      {
         return (DocumentResource) entityManager.createQuery(
            "select r from DocumentResource r where r.id = :id and r.key = :key")
            .setParameter("id", docId)
            .setParameter("key", key)
            .getSingleResult();
      }
      catch (NoResultException ex)
      {
         return null;
      }
   }
}
