package org.jboss.tools.examples.rest.dto;

import java.io.Serializable;
import org.jboss.tools.examples.model.Event;
import javax.persistence.EntityManager;
import org.jboss.tools.examples.rest.dto.NestedMediaItemDTO;
import org.jboss.tools.examples.rest.dto.NestedEventCategoryDTO;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EventDTO implements Serializable
{

   private Long id;
   private String name;
   private String description;
   private NestedMediaItemDTO mediaItem;
   private NestedEventCategoryDTO category;

   public EventDTO()
   {
   }

   public EventDTO(final Event entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
         this.name = entity.getName();
         this.description = entity.getDescription();
         this.mediaItem = new NestedMediaItemDTO(entity.getMediaItem());
         this.category = new NestedEventCategoryDTO(entity.getCategory());
      }
   }

   public Event fromDTO(Event entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Event();
      }
      entity.setName(this.name);
      entity.setDescription(this.description);
      if (this.mediaItem != null)
      {
         entity.setMediaItem(this.mediaItem.fromDTO(entity.getMediaItem(),
               em));
      }
      if (this.category != null)
      {
         entity.setCategory(this.category.fromDTO(entity.getCategory(), em));
      }
      entity = em.merge(entity);
      return entity;
   }

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public NestedMediaItemDTO getMediaItem()
   {
      return this.mediaItem;
   }

   public void setMediaItem(final NestedMediaItemDTO mediaItem)
   {
      this.mediaItem = mediaItem;
   }

   public NestedEventCategoryDTO getCategory()
   {
      return this.category;
   }

   public void setCategory(final NestedEventCategoryDTO category)
   {
      this.category = category;
   }
}