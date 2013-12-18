package it._7bits.web.student.service;

import it._7bits.web.student.domain.Group;

import java.util.List;

/**
 * Service layer interface for Group
 */
public interface IGroupService {

    /**
     * List all Groups
     * @return list of Groups
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<Group> findAllGroups () throws ServiceGeneralException;

    /**
     * List all Groups in exact Department
     * @param departmentId Id of department
     * @return List of Groups
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<Group> findGroupsInDep (final Long departmentId)
            throws ServiceGeneralException;

    /**
     * Find Group using its id
     * @param id    Id of Group
     * @return Group instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public Group findGroupById (final Long id) throws ServiceGeneralException;

    /**
     * Adding group to source of information
     * @param group    Group instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void addGroup (final Group group) throws ServiceGeneralException;

    /**
     * Updating group
     * @param group    Group instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void updateGroup (final Group group) throws ServiceGeneralException;

    /**
     * Removing group
     * @param group    Group instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     * @throws ServiceConstraintViolationException if constraint violation occurs
     */
    public void deleteGroup (final Group group)
            throws ServiceGeneralException, ServiceConstraintViolationException;
}

