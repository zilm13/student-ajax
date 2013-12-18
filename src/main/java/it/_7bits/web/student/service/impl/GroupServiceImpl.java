package it._7bits.web.student.service.impl;

import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.IEntityDao;
import it._7bits.web.student.domain.Group;
import it._7bits.web.student.service.IGroupService;
import it._7bits.web.student.service.ServiceGeneralException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation for Group service layer
 */
@Service
public class GroupServiceImpl implements IGroupService {

    protected final Logger LOG = Logger.getLogger(getClass());

    @Autowired @Qualifier("groupDao")
    private IEntityDao groupDao;

    /**
     * List all Groups
     *
     * @return list of Groups
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<Group> findAllGroups() throws ServiceGeneralException {
        try {
            return groupDao.findAll();
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot list all Groups: ", e);
        }
    }

    /**
     * List all Groups in exact Department
     *
     * @param departmentId Id of department
     * @return List of Groups
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<Group> findGroupsInDep(Long departmentId) throws ServiceGeneralException {
        try {
            if (departmentId != null) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put ("departmentId", departmentId);
                return groupDao.
                        findByQuery("from " + groupDao.getEntityName() +
                                " where departmentId = :departmentId", parameters);
            } else {
                LOG.warn ("Cannot list Groups in department: input Department Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get Groups in Department: ", e);
        }
    }

    /**
     * Find Group using its id
     *
     * @param id Id of Group
     * @return Group instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public Group findGroupById(Long id) throws ServiceGeneralException {
        try {
            if (id != null) {
                return (Group) groupDao.findById(id);
            } else {
                LOG.warn ("Cannot find Group: Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get Group by id: ", e);
        }
    }

    /**
     * Adding group to source of information
     *
     * @param group Group instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void addGroup(Group group) throws ServiceGeneralException {
        try {
            if (group != null) {
                groupDao.add (group);
            } else {
                LOG.warn ("Cannot add Group, object is null");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot add Group: ", e);
        }
    }

    /**
     * Updating group
     *
     * @param group Group instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void updateGroup(Group group) throws ServiceGeneralException {
        try {
            if (group != null && group.getId() != null) {
                groupDao.update (group);
            } else {
                LOG.warn ("Cannot update Group, object is null or id is not set");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot update Group: ", e);
        }
    }

    /**
     * Removing group
     *
     * @param group Group instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void deleteGroup(Group group) throws ServiceGeneralException {
        try {
            if (group != null && group.getId() != null) {
                groupDao.remove (group);
            } else {
                LOG.warn ("Cannot delete Group, object is null or id is not set");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot delete Group: ", e);
        }
    }
}
