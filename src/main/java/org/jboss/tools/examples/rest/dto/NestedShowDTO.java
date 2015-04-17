package org.jboss.tools.examples.rest.dto;

import java.io.Serializable;
import org.jboss.tools.examples.model.Show;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedShowDTO implements Serializable
{

   private Long id;

   public NestedShowDTO()
   {
   }

   public NestedShowDTO(final Show entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
      }
   }

   public Show fromDTO(Show entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Show();
      }
      if (this.id != null)
      {
         TypedQuery<Show> findByIdQuery = em.createQuery(
               "SELECT DISTINCT s FROM Show s WHERE s.id = :entityId",
               Show.class);
         findByIdQuery.setParameter("entityId", this.id);
         try
         {
            entity = findByIdQuery.getSingleResult();
         }
         catch (javax.persistence.NoResultException nre)
         {
            entity = null;
         }
         return entity;
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
}