package it._7bits.web.student.dao;


import it._7bits.web.student.domain.Student;

import java.util.List;
import java.util.Map;

/**
 * Student Dao Interface
 */
public interface IStudentDao {
    /**
     * Find all Student occurrences
     * @return List with all Students found
     * @throws DaoGeneralException if operation fails
     */
    public List<Student> findAll ()
            throws DaoGeneralException;

    /**
     * Find all Students using query
     * @param query         Named query
     * @param parameters    Parameters to use in query
     * @return List of all Students returned from source using corresponding query
     * @throws DaoGeneralException if operation fails
     */
    public List<Student> findByQuery (final String query,
                                         final Map<String,Object> parameters)
            throws DaoGeneralException;

    /**
     * Find Student using its id
     * @param id    Id of Student
     * @return Student
     * @throws DaoGeneralException if operation fails
     */
    public Student findById (final Long id)
            throws DaoGeneralException;

    /**
     * Add new Student to source.
     * Assumes it does not exist in it.
     * @param student new Student, id should be null if auto-generated
     * @throws DaoGeneralException if operation fails
     */
    public void add (final Student student)
            throws DaoGeneralException;

    /**
     * Update Student which is already in source
     * @param student    Student to update
     * @throws DaoGeneralException if operation fails
     */
    public void update (final Student student)
            throws DaoGeneralException;

    /**
     * Remove Student from the source
     * @param student    Student to remove
     * @throws DaoGeneralException if operation fails
     */
    public void remove (final Student student)
            throws DaoGeneralException;

    /**
     * Get Student Entity name from Dao.
     * Useful to create queries in Service layer
     * @return Student Entity name
     */
    public String getEntityName ();
}
