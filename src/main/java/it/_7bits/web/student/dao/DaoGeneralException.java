package it._7bits.web.student.dao;

/**
 * DAO layer common exception
 * Is thrown in all fail circumstances
 */
public class DaoGeneralException extends Exception {
    /**
     * Dao general exception to throw in most common situations
     * @param message    Custom message for exception
     * @param e          Exception
     */
    public DaoGeneralException (String message, Exception e) {
        super (message, e);
    }
}
