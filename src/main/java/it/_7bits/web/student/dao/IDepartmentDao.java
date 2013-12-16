package it._7bits.web.student.dao;

import it._7bits.web.student.domain.Department;

import java.util.List;
import java.util.Map;

/**
 * Department Dao Interface
 */
public interface IDepartmentDao {
    /**
     * Find all Department occurrences
     * @return List with all Departments found
     * @throws DaoGeneralException if operation fails
     */
    public List<Department> findAll ()
            throws DaoGeneralException;

    /**
     * Find all Departments using query
     * @param query         Named query
     * @param parameters    Parameters to use in query
     * @return List of all Departments returned from source using corresponding query
     * @throws DaoGeneralException if operation fails
     */
    public List<Department> findByQuery (final String query,
                                     final Map<String,Object> parameters)
            throws DaoGeneralException;

    /**
     * Find Department using its id
     * @param id    Id of Department
     * @return Department
     * @throws DaoGeneralException if operation fails
     */
    public Department findById (final Long id)
            throws DaoGeneralException;

    /**
     * Add new Department to source.
     * Assumes it does not exist in it.
     * @param department new Department, id should be null if auto-generated
     * @throws DaoGeneralException if operation fails
     */
    public void add (final Department department)
            throws DaoGeneralException;

    /**
     * Update Department which is already in source
     * @param department    Department to update
     * @throws DaoGeneralException if operation fails
     */
    public void update (final Department department)
            throws DaoGeneralException;

    /**
     * Remove Department from the source
     * @param department    Department to remove
     * @throws DaoGeneralException if operation fails
     */
    public void remove (final Department department)
            throws DaoGeneralException;

    /**
     * Get Department Entity name from Dao.
     * Useful to create queries in Service layer
     * @return Department Entity name
     */
    public String getEntityName ();
}