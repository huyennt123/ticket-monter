package org.jboss.tools.examples.rest;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import org.jboss.tools.examples.model.Venue;

@Path("/venues")
@Stateless
public class VenueService extends BaseEntityService<Venue> {

    public VenueService() {
        super(Venue.class);
    }

}
