package it._7bits.web.student.service;


/**
 * Constraint Violation exception for service layer
 */
public class ServiceConstraintViolationException extends Exception {
    /**
     * Constraint violation type exception for service layer
     * with custom defined message
     * @param message Custom message
     * @param e Standard exception
     */
    public ServiceConstraintViolationException (final String message, Exception e) {
        super (message, e);
    }
}
