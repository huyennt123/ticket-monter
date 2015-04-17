package org.jboss.tools.examples.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Booking implements Serializable
{

   @Id
   @GeneratedValue(strategy = IDENTITY)
   private Long id;

   @OneToMany(fetch = EAGER, cascade = ALL)
   @JoinColumn
   @NotEmpty
   @Valid
   private Set<Ticket> tickets = new HashSet<Ticket>();

   @ManyToOne
   private Performance performance;

   @NotEmpty
   private String cancellationCode;

   @NotNull
   private Date createdOn = new Date();

   @NotEmpty
   @Email(message = "Not a valid email format")
   private String contactEmail;

   /**
    * Compute the total price of all tickets in this booking.
    */
   public float getTotalTicketPrice()
   {
      float totalPrice = 0.0f;
      for (Ticket ticket : tickets)
      {
         totalPrice += (ticket.getPrice());
      }
      return totalPrice;
   }

   /* Boilerplate getters and setters */

   public Long getId()
   {
      return id;
   }

   public Set<Ticket> getTickets()
   {
      return tickets;
   }

   public void setTickets(Set<Ticket> tickets)
   {
      this.tickets = tickets;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   public void setCreatedOn(Date createdOn)
   {
      this.createdOn = createdOn;
   }

   public String getCancellationCode()
   {
      return cancellationCode;
   }

   public void setCancellationCode(String cancellationCode)
   {
      this.cancellationCode = cancellationCode;
   }

   public String getContactEmail()
   {
      return contactEmail;
   }

   public void setContactEmail(String contactEmail)
   {
      this.contactEmail = contactEmail;
   }

   public Performance getPerformance()
   {
      return performance;
   }

   public void setPerformance(Performance performance)
   {
      this.performance = performance;
   }

   /* equals() and hashCode() for Booking, using the synthetic identity of the object */

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Booking booking = (Booking) o;

      if (id != null ? !id.equals(booking.id) : booking.id != null)
         return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      return id != null ? id.hashCode() : 0;
   }
}
