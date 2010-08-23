package org.jboss.seam.example.ticketmonster.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Event.class)
public class Event_
{
   public static volatile SingularAttribute<Event, Long> id;
   public static volatile SingularAttribute<Event, String> name;
   public static volatile SingularAttribute<Event, Document> description;
   public static volatile SingularAttribute<Event, Date> startDate;
   public static volatile SingularAttribute<Event, Date> endDate;   
   public static volatile SingularAttribute<Event, EventCategory> category;
   public static volatile SingularAttribute<Event, Boolean> major;   
}
