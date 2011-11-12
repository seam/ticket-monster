package org.jboss.jdf.example.ticketmonster.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.jdf.example.ticketmonster.model.Section;
import org.jboss.jdf.example.ticketmonster.model.SectionRow;
import org.jboss.jdf.example.ticketmonster.model.VenueLayout;

/**
 * 
 * @author Shane Bryzak
 *
 */
public @Stateful @ConversationScoped @Named class LayoutAction implements Serializable
{
   private static final long serialVersionUID = 2646300975197236221L;
   
   @Inject Conversation conversation;
   @PersistenceContext EntityManager entityManager;
   /*@Inject @RequestParam("layoutId")*/ String layoutId;

   private VenueLayout layout;   
   private List<Section> sections;
   
   private Section section;   
   private List<SectionRow> rows;
   private SectionRow row;
   
   public void createLayout()
   {
      conversation.begin();
      layout = new VenueLayout();
      sections = new ArrayList<Section>();
   }
 
   @SuppressWarnings("unchecked")
   public boolean isLoadLayout()
   {      
      // Only load the layout if a layoutId has been provided
      if (layout == null && layoutId != null && conversation.isTransient())
      {                      
         conversation.begin();      
         layout = entityManager.find(VenueLayout.class, Long.valueOf(layoutId));
         
         sections = entityManager.createQuery(
               "select s from Section s where s.layout = :layout")
               .setParameter("layout", layout)
               .getResultList();
      }
      
      return false;
   }
   
   public String addSection()
   {
      section = new Section();
      rows = new ArrayList<SectionRow>();
      return "success";
   }      
   
   public String editSection(Section section)
   {
      this.section = section;      
      return "success";
   }
   
   public void removeSection(Section section)
   {      
      sections.remove(section);      
      if (section.getId() != null) 
      {
         entityManager.remove(entityManager.getReference(Section.class, section.getId()));
      }
   }
   
   public Long getSectionCapacityTotal()
   {
      long total = 0;
      
      for (Section s : sections) 
      {
         total += s.getCapacity();
      }
      
      return total;
   }
   
   public String saveSection()
   {
      section.setLayout(layout);
      if (!sections.contains(section)) 
      {
         sections.add(section);     
      }
      return "success";
   }
   
   public String saveRow()
   {
      row.setSection(section);
      rows.add(row);
      return "success";
   }
   
   public String save()
   {
      if (layout.getCapacity() != getSectionCapacityTotal())
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
               FacesMessage.SEVERITY_ERROR, "Capacity mismatch", 
               "The layout capacity you have entered must equal the section capacity total.  Please correct this before saving."));
         return null;
      }
      
      if (layout.getId() != null)
      {
         entityManager.merge(layout);
      }
      else
      {
         entityManager.persist(layout);
      }      
      
      for (Section s : sections)
      {
         if (s.getId() != null)
         {
            entityManager.merge(s);
         }
         else
         {
            entityManager.persist(s);
         }
      }
      
      entityManager.flush();
      conversation.end();
      return "success";
   }
   
   public String cancel()
   {
      entityManager.clear();
      conversation.end();
      return "cancel";
   }
   
   public VenueLayout getLayout()
   {
      return layout;
   }
   
   public List<Section> getSections()
   {
      return sections;
   }
   
   public Section getSection()
   {
      return section;
   }
}
