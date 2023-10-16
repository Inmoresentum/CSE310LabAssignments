package org.cse310.bracu.assignment2.exceptions;

public class OutOfSeatException extends Exception{
    public OutOfSeatException() {
    }

    public OutOfSeatException(String message) {
        super(message);
    }

    public OutOfSeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfSeatException(Throwable cause) {
        super(cause);
    }

    public OutOfSeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
