package it._7bits.web.student.dao;

/**
 * DAO layer constraint violation exception
 * Is thrown in case of constraint violation
 */
public class DaoConstraintViolationException extends Exception {
    /**
     * Dao constraint violation exception
     * @param message    Custom message for exception
     * @param e          Exception
     */
    public DaoConstraintViolationException (String message, Exception e) {
        super (message, e);
    }
}