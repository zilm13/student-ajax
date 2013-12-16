package it._7bits.web.student.dao;

import it._7bits.web.student.domain.SubDepartment;

import java.util.List;
import java.util.Map;

/**
 * SubDepartment DAO interface
 */
public interface ISubDepartmentDao {
    /**
     * Find all SubDepartment occurrences
     * @return List with all SubDepartments found
     * @throws DaoGeneralException if operation fails
     */
    public List<SubDepartment> findAll ()
            throws DaoGeneralException;

    /**
     * Find all SubDepartments using query
     * @param query         Named query
     * @param parameters    Parameters to use in query
     * @return List of all SubDepartments returned from source using corresponding query
     * @throws DaoGeneralException if operation fails
     */
    public List<SubDepartment> findByQuery (final String query,
                                         final Map<String,Object> parameters)
            throws DaoGeneralException;

    /**
     * Find SubDepartment using its id
     * @param id    Id of SubDepartment
     * @return SubDepartment
     * @throws DaoGeneralException if operation fails
     */
    public SubDepartment findById (final Long id)
            throws DaoGeneralException;

    /**
     * Add new SubDepartment to source.
     * Assumes it does not exist in it.
     * @param subDepartment new SubDepartment, id should be null if auto-generated
     * @throws DaoGeneralException if operation fails
     */
    public void add (final SubDepartment subDepartment)
            throws DaoGeneralException;

    /**
     * Update SubDepartment which is already in source
     * @param subDepartment    SubDepartment to update
     * @throws DaoGeneralException if operation fails
     */
    public void update (final SubDepartment subDepartment)
            throws DaoGeneralException;

    /**
     * Remove SubDepartment from the source
     * @param subDepartment    SubDepartment to remove
     * @throws DaoGeneralException if operation fails
     */
    public void remove (final SubDepartment subDepartment)
            throws DaoGeneralException;

    /**
     * Get SubDepartment Entity name from Dao.
     * Useful to create queries in Service layer
     * @return SubDepartment Entity name
     */
    public String getEntityName ();
}
