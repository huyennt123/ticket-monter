package org.jboss.tools.examples.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.print.attribute.standard.Media;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Event
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   @NotNull
   @Size(min = 5, max = 50, message = "An event's name must contain between 5 and 50 characters")
   private String name;

   @NotNull
   @Size(min = 20, max = 1000, message = "An event's name must contain between 20 and 1000 characters")
   private String description;

   @ManyToOne
   private MediaItem mediaItem;

   @ManyToOne
   @NotNull
   private EventCategory category;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public MediaItem getMediaItem()
   {
      return mediaItem;
   }

   public void setMediaItem(MediaItem picture)
   {
      this.mediaItem = picture;
   }

   public EventCategory getCategory()
   {
      return category;
   }

   public void setCategory(EventCategory category)
   {
      this.category = category;
   }

   /* toString(), equals() and hashCode() for Event, using the natural identity of the object */

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Event event = (Event) o;

      if (name != null ? !name.equals(event.name) : event.name != null)
         return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      return name != null ? name.hashCode() : 0;
   }

   @Override
   public String toString()
   {
      return name;
   }
}