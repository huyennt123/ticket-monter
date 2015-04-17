package org.jboss.tools.examples.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "performance_id", "section_id" }))
@XmlRootElement
public class SectionAllocation implements Serializable
{
   public static final int EXPIRATION_TIME = 60 * 1000;

   @Id
   @GeneratedValue(strategy = IDENTITY)
   private Long id;

   @SuppressWarnings("unused")
   @Version
   private long version;

   @ManyToOne
   @NotNull
   private Performance performance;

   @ManyToOne
   @NotNull
   private Section section;

   @Lob
   private long[][] allocated;

   private int occupiedCount = 0;

   public SectionAllocation()
   {
   }

   public SectionAllocation(Performance performance, Section section)
   {
      this.performance = performance;
      this.section = section;
      this.allocated = new long[section.getNumberOfRows()][section.getRowCapacity()];
      for (long[] seatStates : allocated)
      {
         Arrays.fill(seatStates, 0l);
      }
   }

   @PostLoad
   void initialize()
   {
      if (this.allocated == null)
      {
         this.allocated = new long[this.section.getNumberOfRows()][this.section.getRowCapacity()];
         for (long[] seatStates : allocated)
         {
            Arrays.fill(seatStates, 0l);
         }
      }
   }

   public boolean isAllocated(Seat s)
   {
      // Examine the allocation matrix, using the row and seat number as indices
      return allocated[s.getRowNumber() - 1][s.getNumber() - 1] != 0;
   }

   public ArrayList<Seat> allocateSeats(int seatCount, boolean contiguous)
   {
      // The list of seats allocated
      ArrayList<Seat> seats = new ArrayList<Seat>();

      // The seat allocation algorithm starts by iterating through the rows in this section
      for (int rowCounter = 0; rowCounter < section.getNumberOfRows(); rowCounter++)
      {

         if (contiguous)
         {
            // identify the first block of free seats of the requested size
            int startSeat = findFreeGapStart(rowCounter, 0, seatCount);
            // if a large enough block of seats is available
            if (startSeat >= 0)
            {
               // Create the list of allocated seats to return
               for (int i = 1; i <= seatCount; i++)
               {
                  seats.add(new Seat(section, rowCounter + 1, startSeat + i));
               }
               // Seats are allocated now, so we can stop checking rows
               break;
            }
         }
         else
         {
            // As we aren't allocating contiguously, allocate each seat needed, one at a time
            int startSeat = findFreeGapStart(rowCounter, 0, 1);
            // if a seat is found
            if (startSeat >= 0)
            {
               do
               {
                  // Create the seat to return to the user
                  seats.add(new Seat(section, rowCounter + 1, startSeat + 1));
                  // Find the next free seat in the row
                  startSeat = findFreeGapStart(rowCounter, startSeat, 1);
               }
               while (startSeat >= 0 && seats.size() < seatCount);
               if (seats.size() == seatCount)
               {
                  break;
               }
            }
         }
      }
      // Simple check to make sure we could actually allocate the required number of seats

      if (seats.size() == seatCount)
      {
         for (Seat seat : seats)
         {
            allocate(seat.getRowNumber() - 1, seat.getNumber() - 1, 1, expirationTimestamp());
         }
         return seats;
      }
      else
      {
         return new ArrayList<Seat>(0);
      }
   }

   public void markOccupied(List<Seat> seats)
   {
      for (Seat seat : seats)
      {
         allocate(seat.getRowNumber() - 1, seat.getNumber() - 1, 1, -1);
      }
   }

   private int findFreeGapStart(int row, int startSeat, int size)
   {

      // An array of occupied seats in the row
      long[] occupied = allocated[row];
      int candidateStart = -1;

      // Iterate over the seats, and locate the first free seat block
      for (int i = startSeat; i < occupied.length; i++)
      {
         // if the seat isn't allocated
         long currentTimestamp = System.currentTimeMillis();
         if (occupied[i] >= 0 && currentTimestamp > occupied[i])
         {
            // then set this as a possible start
            if (candidateStart == -1)
            {
               candidateStart = i;
            }
            // if we've counted out enough seats since the possible start, then we are done
            if ((size == (i - candidateStart + 1)))
            {
               return candidateStart;
            }
         }
         else
         {
            candidateStart = -1;
         }
      }
      return -1;
   }

   private void allocate(int row, int start, int size, long finalState) throws SeatAllocationException
   {
      long[] occupied = allocated[row];
      if (size <= 0)
      {
         throw new SeatAllocationException("Number of seats must be greater than zero");
      }
      if (start < 0 || start >= occupied.length)
      {
         throw new SeatAllocationException("Seat number must be betwen 1 and " + occupied.length);
      }
      if ((start + size) > occupied.length)
      {
         throw new SeatAllocationException("Cannot allocate seats above row capacity");
      }

      // Now that we know we can allocate the seats, set them to occupied in the allocation matrix
      for (int i = start; i < (start + size); i++)
      {
         occupied[i] = finalState;
         occupiedCount++;
      }

   }

   /**
    * Dellocate a seat within this section for this performance.
    *
    * @param seat the seats that need to be deallocated
    */
   public void deallocate(Seat seat)
   {
      if (!isAllocated(seat))
      {
         throw new SeatAllocationException("Trying to deallocate an unallocated seat!");
      }
      this.allocated[seat.getRowNumber() - 1][seat.getNumber() - 1] = 0;
      occupiedCount--;
   }

   /* Boilerplate getters and setters */

   public int getOccupiedCount()
   {
      return occupiedCount;
   }

   public Performance getPerformance()
   {
      return performance;
   }

   public Section getSection()
   {
      return section;
   }

   public Long getId()
   {
      return id;
   }

   private long expirationTimestamp()
   {
      return System.currentTimeMillis() + EXPIRATION_TIME;
   }

}
