package org.jboss.tools.examples.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.URL;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * A reference to a media object such as images, sound bites, video recordings, that can be used in the application.
 * </p>
 * 
 * <p>
 * A media item contains the type of the media, which is required to render it correctly, as well as the URL at which the media
 * should be sourced.
 * </p>
 * 
 * @author Marius Bogoevici
 * @author Pete Muir
 */
/*
 * We suppress the warning about not specifying a serialVersionUID, as we are still developing this app, and want the JVM to
 * generate the serialVersionUID for us. When we put this app into production, we'll generate and embed the serialVersionUID
 */
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class MediaItem implements Serializable
{
   @Id
   @GeneratedValue(strategy = IDENTITY)
   private Long id;
   @Enumerated(STRING)
   private MediaType mediaType;
   @Column(unique = true)
   @URL
   private String url;

   public Long getId()
   {
      return id;
   }

   public MediaType getMediaType()
   {
      return mediaType;
   }

   public void setMediaType(MediaType mediaType)
   {
      this.mediaType = mediaType;
   }

   public String getUrl()
   {
      return url;
   }

   public void setUrl(String url)
   {
      this.url = url;
   }

   @Override
   public String toString()
   {
      return "[" + mediaType.getDescription() + "] " + url;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((url == null) ? 0 : url.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      MediaItem other = (MediaItem) obj;
      if (url == null)
      {
         if (other.url != null)
            return false;
      }
      else if (!url.equals(other.url))
         return false;
      return true;
   }

}
