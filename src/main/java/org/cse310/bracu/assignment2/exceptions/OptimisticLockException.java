package org.cse310.bracu.assignment2.exceptions;

import java.sql.SQLException;

public class OptimisticLockException extends SQLException {

    public OptimisticLockException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public OptimisticLockException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public OptimisticLockException(String reason) {
        super(reason);
    }

    public OptimisticLockException() {
    }

    public OptimisticLockException(Throwable cause) {
        super(cause);
    }

    public OptimisticLockException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public OptimisticLockException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public OptimisticLockException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }
}
