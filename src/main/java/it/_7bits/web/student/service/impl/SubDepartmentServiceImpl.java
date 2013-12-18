package it._7bits.web.student.service.impl;

import it._7bits.web.student.dao.DaoConstraintViolationException;
import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.IEntityDao;
import it._7bits.web.student.domain.SubDepartment;
import it._7bits.web.student.service.ISubDepartmentService;
import it._7bits.web.student.service.ServiceConstraintViolationException;
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
 * Implementation for SubDepartment service layer
 */
@Service
public class SubDepartmentServiceImpl implements ISubDepartmentService {

    protected final Logger LOG = Logger.getLogger(getClass());

    @Autowired @Qualifier("subDepartmentDao")
    private IEntityDao subDepartmentDao;

    /**
     * List all SubDepartments
     *
     * @return list of SubDepartments
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<SubDepartment> findAllSubDepartments() throws ServiceGeneralException {
        try {
            return subDepartmentDao.findAll();
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot list all SubDepartments: ", e);
        }
    }

    /**
     * List all SubDepartments in exact Department
     *
     * @param departmentId Id of department
     * @return List of SubDepartments
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<SubDepartment> findSubDepartmentsInDep(Long departmentId)
            throws ServiceGeneralException {
        try {
            if (departmentId != null) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put ("departmentId", departmentId);
                return subDepartmentDao.
                        findByQuery("from " + subDepartmentDao.getEntityName() +
                                " where departmentId = :departmentId", parameters);
            } else {
                LOG.warn ("Cannot list SubDepartments in department: input Department Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get SubDepartments in Department: ", e);
        }
    }

    /**
     * Find SubDepartment using its id
     *
     * @param id Id of SubDepartment
     * @return SubDepartment instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public SubDepartment findSubDepartmentById(Long id) throws ServiceGeneralException {
        try {
            if (id != null) {
                return (SubDepartment) subDepartmentDao.findById (id);
            } else {
                LOG.warn ("Cannot find SubDepartment: Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get SubDepartment by id: ", e);
        }
    }

    /**
     * Adding subDepartment to source of information
     *
     * @param subDepartment SubDepartment instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void addSubDepartment(SubDepartment subDepartment) throws ServiceGeneralException {
        try {
            if (subDepartment != null) {
                subDepartmentDao.add (subDepartment);
            } else {
                LOG.warn ("Cannot add SubDepartment, object is null");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot add SubDepartment: ", e);
        }
    }

    /**
     * Updating subDepartment
     *
     * @param subDepartment SubDepartment instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void updateSubDepartment(SubDepartment subDepartment) throws ServiceGeneralException {
        try {
            if (subDepartment != null && subDepartment.getId() != null) {
                subDepartmentDao.update (subDepartment);
            } else {
                LOG.warn ("Cannot update SubDepartment, object is null or id is not set");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot update SubDepartment: ", e);
        }
    }

    /**
     * Removing subDepartment
     *
     * @param subDepartment SubDepartment instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     * @throws ServiceConstraintViolationException if constraint violation occurs
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSubDepartment(SubDepartment subDepartment) throws ServiceGeneralException,
            ServiceConstraintViolationException {
        try {
            if (subDepartment != null && subDepartment.getId() != null) {
                subDepartmentDao.remove(subDepartment);
            } else {
                LOG.warn ("Cannot delete SubDepartment, object is null or id is not set");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot delete SubDepartment: ", e);
        } catch (DaoConstraintViolationException e) {
            throw new ServiceConstraintViolationException
                    ("Service cannot delete SubDepartment because of constraint violation: ", e);
        }
    }
}
