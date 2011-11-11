package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.example.ticketmonster.model.EventCategory;

/**
 * Event Category search actions
 *
 * @author Shane Bryzak
 *
 */
@Model
public class CategorySearch {

    @PersistenceContext EntityManager entityManager;

    private List<EventCategory> categories;

    private void loadCategories()
    {
        categories = entityManager.createQuery("select c from EventCategory c").getResultList();
    }

    public List<EventCategory> getCategories()
    {
        if (categories == null)
        {
            loadCategories();
        }
        return categories;
    }   
}
