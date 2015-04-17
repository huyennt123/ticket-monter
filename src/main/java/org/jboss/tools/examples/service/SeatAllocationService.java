package org.jboss.tools.examples.service;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

import org.jboss.tools.examples.model.Performance;
import org.jboss.tools.examples.model.Seat;
import org.jboss.tools.examples.model.SeatAllocationException;
import org.jboss.tools.examples.model.Section;
import org.jboss.tools.examples.model.SectionAllocation;
import org.jboss.tools.examples.service.AllocatedSeats;

@SuppressWarnings("serial")
public class SeatAllocationService {

    @Inject
    EntityManager entityManager;

    public AllocatedSeats allocateSeats(Section section, Performance performance, int seatCount, boolean contiguous) {
        SectionAllocation sectionAllocation = retrieveSectionAllocationExclusively(section, performance);
        List<Seat> seats = sectionAllocation.allocateSeats(seatCount, contiguous);
        return new AllocatedSeats(sectionAllocation, seats);
    }

    public void deallocateSeats(Section section, Performance performance, List<Seat> seats) {
        SectionAllocation sectionAllocation = retrieveSectionAllocationExclusively(section, performance);
        for (Seat seat : seats) {
            if (!seat.getSection().equals(section)) {
                throw new SeatAllocationException("All seats must be in the same section!");
            }
            sectionAllocation.deallocate(seat);
        }
    }

    private SectionAllocation retrieveSectionAllocationExclusively(Section section, Performance performance) {
        SectionAllocation sectionAllocationStatus = null;
        try {
            sectionAllocationStatus = (SectionAllocation) entityManager.createQuery(
                "select s from SectionAllocation s where " +
                    "s.performance.id = :performanceId and " +
                    "s.section.id = :sectionId")
                .setParameter("performanceId", performance.getId())
                .setParameter("sectionId", section.getId())
                .getSingleResult();
        } catch (NoResultException noSectionEx) {
            // Create the SectionAllocation since it doesn't exist
            sectionAllocationStatus = new SectionAllocation(performance, section);
            entityManager.persist(sectionAllocationStatus);
            entityManager.flush();
        }
        entityManager.lock(sectionAllocationStatus, LockModeType.PESSIMISTIC_WRITE);
        return sectionAllocationStatus;
    }
}
