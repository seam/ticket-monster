package org.jboss.seam.example.ticketmonster.action;

import org.jboss.seam.example.ticketmonster.model.Event;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Event search actions handled here
 *
 * @author Shane Bryzak
 *
 */
public @Model class EventSearch extends BaseEventSearch
{
    /*@Inject @RequestParam("category")*/ String category;

    private List<Event> events;

    @SuppressWarnings("unchecked")
    private void loadEvents()
    {
        events = (category != null) ? getEventsByCategory(Long.valueOf(category)) : getMajorEvents();
    }    

    public List<Event> getEvents()
    {
        if (events == null) loadEvents();
        return events;
    }

    public List<Event> getAllEvents()
    {
        return getAllEvents();
    }
}
