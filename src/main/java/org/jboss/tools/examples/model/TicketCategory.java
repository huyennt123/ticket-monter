/* tra cưu,đại diện cho các loại vé*/
package org.jboss.tools.examples.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class TicketCategory
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(unique = true)
   @NotEmpty
   private String description;

   /* Boilerplate getters and setters */

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   /* toString(), equals() and hashCode() for TicketCategory, using the natural identity of the object */

   @Override
   public String toString()
   {
      return description;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((description == null) ? 0 : description.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (!(obj instanceof TicketCategory))
         return false;
      TicketCategory other = (TicketCategory) obj;
      if (description == null)
      {
         if (other.description != null)
            return false;
      }
      else if (!description.equals(other.description))
         return false;
      return true;
   }
}