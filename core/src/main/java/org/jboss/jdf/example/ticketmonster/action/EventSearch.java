package org.jboss.jdf.example.ticketmonster.action;

import org.jboss.jdf.example.ticketmonster.model.Event;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
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
    private List<Event> events;

    @SuppressWarnings("unchecked")
    private void loadEvents()
    {
        String category = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("category");
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
