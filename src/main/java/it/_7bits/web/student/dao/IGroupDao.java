package it._7bits.web.student.dao;

import it._7bits.web.student.domain.Group;

import java.util.List;
import java.util.Map;

/**
 * Group DAO interface
 */
public interface IGroupDao {
    /**
     * Find all Group occurrences
     * @return List with all Groups found
     * @throws DaoGeneralException if operation fails
     */
    public List<Group> findAll ()
            throws DaoGeneralException;

    /**
     * Find all Groups using query
     * @param query         Named query
     * @param parameters    Parameters to use in query
     * @return List of all Groups returned from source using corresponding query
     * @throws DaoGeneralException if operation fails
     */
    public List<Group> findByQuery (final String query,
                                         final Map<String,Object> parameters)
            throws DaoGeneralException;

    /**
     * Find Group using its id
     * @param id    Id of Group
     * @return Group
     * @throws DaoGeneralException if operation fails
     */
    public Group findById (final Long id)
            throws DaoGeneralException;

    /**
     * Add new Group to source.
     * Assumes it does not exist in it.
     * @param group new Group, id should be null if auto-generated
     * @throws DaoGeneralException if operation fails
     */
    public void add (final Group group)
            throws DaoGeneralException;

    /**
     * Update Group which is already in source
     * @param group    Group to update
     * @throws DaoGeneralException if operation fails
     */
    public void update (final Group group)
            throws DaoGeneralException;

    /**
     * Remove Group from the source
     * @param group    Group to remove
     * @throws DaoGeneralException if operation fails
     */
    public void remove (final Group group)
            throws DaoGeneralException;

    /**
     * Get Group Entity name from Dao.
     * Useful to create queries in Service layer
     * @return Group Entity name
     */
    public String getEntityName ();
}
