package org.jboss.seam.example.ticketmonster.model.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.EventCategory;

@StaticMetamodel(Event.class)
public class Event_
{
   public static volatile SingularAttribute<Event, Boolean> major;
   public static volatile SingularAttribute<Event, EventCategory> category;
}
