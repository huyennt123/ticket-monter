package org.jboss.tools.examples.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.print.attribute.standard.Media;

import org.hibernate.validator.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Venue
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   @NotEmpty
   private String name;

   @Embedded
   private Address address = new Address();

   private String description;

   @OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "venue")
   private Set<Section> sections = new HashSet<Section>();

   private int capacity;

   @ManyToOne
   private MediaItem mediaItem;

   /* Boilerplate getters and setters */

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

   public Address getAddress()
   {
      return address;
   }

   public void setAddress(Address address)
   {
      this.address = address;
   }

   public MediaItem getMediaItem()
   {
      return mediaItem;
   }

   public void setMediaItem(MediaItem description)
   {
      this.mediaItem = description;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public Set<Section> getSections()
   {
      return sections;
   }

   public void setSections(Set<Section> sections)
   {
      this.sections = sections;
   }

   public int getCapacity()
   {
      return capacity;
   }

   public void setCapacity(int capacity)
   {
      this.capacity = capacity;
   }

   /* toString(), equals() and hashCode() for Venue, using the natural identity of the object */

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Venue venue = (Venue) o;

      if (address != null ? !address.equals(venue.address) : venue.address != null)
         return false;
      if (name != null ? !name.equals(venue.name) : venue.name != null)
         return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      int result = name != null ? name.hashCode() : 0;
      result = 31 * result + (address != null ? address.hashCode() : 0);
      return result;
   }

   @Override
   public String toString()
   {
      return name;
   }
}