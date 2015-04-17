package org.jboss.tools.examples.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "section_id", "show_id", "ticketcategory_id" }))
@XmlRootElement
public class TicketPrice implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @NotNull
   private Show show;

   @ManyToOne
   @NotNull
   private Section section;

   @ManyToOne
   @NotNull
   private TicketCategory ticketCategory;

   private float price;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Show getShow()
   {
      return show;
   }

   public void setShow(Show show)
   {
      this.show = show;
   }

   public Section getSection()
   {
      return section;
   }

   public void setSection(Section section)
   {
      this.section = section;
   }

   public TicketCategory getTicketCategory()
   {
      return ticketCategory;
   }

   public void setTicketCategory(TicketCategory ticketCategory)
   {
      this.ticketCategory = ticketCategory;
   }

   public float getPrice()
   {
      return price;
   }

   public void setPrice(float price)
   {
      this.price = price;
   }

   /* equals() and hashCode() for TicketPrice, using the natural identity of the object */

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      TicketPrice that = (TicketPrice) o;

      if (section != null ? !section.equals(that.section) : that.section != null)
         return false;
      if (show != null ? !show.equals(that.show) : that.show != null)
         return false;
      if (ticketCategory != null ? !ticketCategory.equals(that.ticketCategory) : that.ticketCategory != null)
         return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      int result = show != null ? show.hashCode() : 0;
      result = 31 * result + (section != null ? section.hashCode() : 0);
      result = 31 * result + (ticketCategory != null ? ticketCategory.hashCode() : 0);
      return result;
   }

   @Override
   public String toString()
   {
      return "$ " + price + " for " + ticketCategory + " in " + section;
   }
}