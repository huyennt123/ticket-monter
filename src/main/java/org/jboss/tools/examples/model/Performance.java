package org.jboss.tools.examples.model;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "date", "show_id" }))
// TODO Document use of @JsonIgnoreProperties
@XmlRootElement
public class Performance implements Serializable
{
   @Id
   @GeneratedValue(strategy = IDENTITY)
   private Long id;

   @Temporal(TIMESTAMP)
   @NotNull
   private Date date;
   @ManyToOne
   @NotNull
   private Show show;

   /* Boilerplate getters and setters */

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public void setShow(Show show)
   {
      this.show = show;
   }

   public Show getShow()
   {
      return show;
   }

   public Date getDate()
   {
      return date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

   /* equals() and hashCode() for Performance, using the natural identity of the object */

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Performance that = (Performance) o;

      if (date != null ? !date.equals(that.date) : that.date != null)
         return false;
      if (show != null ? !show.equals(that.show) : that.show != null)
         return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      int result = date != null ? date.hashCode() : 0;
      result = 31 * result + (show != null ? show.hashCode() : 0);
      return result;
   }
}
