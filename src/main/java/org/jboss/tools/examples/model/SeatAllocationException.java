package org.jboss.tools.examples.model;

import javax.ejb.ApplicationException;
@SuppressWarnings("serial")
@ApplicationException(rollback = true)
public class SeatAllocationException extends RuntimeException {

    public SeatAllocationException() {
    }

    public SeatAllocationException(String s) {
        super(s);
    }

    public SeatAllocationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SeatAllocationException(Throwable throwable) {
        super(throwable);
    }
}
