package org.jboss.examples.ticketmonster.test.rest;

import javax.ws.rs.core.MultivaluedHashMap;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.tools.examples.model.Booking;
import org.jboss.tools.examples.rest.BaseEntityService;
import org.jboss.tools.examples.service.SeatAllocationService;
import org.jboss.tools.examples.test.TicketMonsterDeployment;

public class RESTDeployment {

    public static WebArchive deployment() {
        return TicketMonsterDeployment.deployment()
                .addPackage(Booking.class.getPackage())
                .addPackage(BaseEntityService.class.getPackage())
                .addPackage(MultivaluedHashMap.class.getPackage())
                .addPackage(SeatAllocationService.class.getPackage());
    }

}
