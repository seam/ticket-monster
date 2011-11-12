package org.jboss.jdf.example.ticketmonster.action;

import org.jboss.jdf.example.ticketmonster.model.Event;
import org.jboss.jdf.example.ticketmonster.model.EventCategory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@RequestScoped
@Named("BaseSearch")
public class BaseEventSearch {

    @PersistenceContext
    EntityManager entityManager;
    
    public List<Event> getMajorEvents() {
        return (List<Event>) entityManager.createQuery(
                "select e from Event e where e.major = true")
                .getResultList();
    }

    public List<Event> getEventsByCategory(Long categoryId) {
        return (List<Event>) entityManager.createQuery(
                "select e from Event e where e.category = :category")
                .setParameter("category", lookupCategory(categoryId))
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    List<Event> loadAllEvents() {
        return (List<Event>) entityManager.createQuery("select e from Event e").getResultList();
    }

    public EventCategory lookupCategory(Long categoryId) {
        return entityManager.find(EventCategory.class, categoryId);
    }    
}