package org.jboss.tools.examples.rest.dto;

import java.io.Serializable;
import org.jboss.tools.examples.model.Performance;
import javax.persistence.EntityManager;
import org.jboss.tools.examples.rest.dto.NestedShowDTO;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PerformanceDTO implements Serializable
{

   private Long id;
   private NestedShowDTO show;
   private Date date;

   public PerformanceDTO()
   {
   }

   public PerformanceDTO(final Performance entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
         this.show = new NestedShowDTO(entity.getShow());
         this.date = entity.getDate();
      }
   }

   public Performance fromDTO(Performance entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Performance();
      }
      if (this.show != null)
      {
         entity.setShow(this.show.fromDTO(entity.getShow(), em));
      }
      entity.setDate(this.date);
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

   public NestedShowDTO getShow()
   {
      return this.show;
   }

   public void setShow(final NestedShowDTO show)
   {
      this.show = show;
   }

   public Date getDate()
   {
      return this.date;
   }

   public void setDate(final Date date)
   {
      this.date = date;
   }
}