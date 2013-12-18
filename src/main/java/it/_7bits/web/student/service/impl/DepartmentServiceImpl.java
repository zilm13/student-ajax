package it._7bits.web.student.service.impl;

import it._7bits.web.student.dao.DaoConstraintViolationException;
import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.IEntityDao;
import it._7bits.web.student.domain.Department;
import it._7bits.web.student.service.IDepartmentService;
import it._7bits.web.student.service.ServiceConstraintViolationException;
import it._7bits.web.student.service.ServiceGeneralException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation for Department service layer
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    protected final Logger LOG = Logger.getLogger(getClass());

    @Autowired @Qualifier("departmentDao")
    private IEntityDao departmentDao;

    /**
     * List all Departments
     *
     * @return list of Departments
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<Department> findAllDepartments() throws ServiceGeneralException {
        try {
            return departmentDao.findAll();
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot list all Departments: ", e);
        }
    }

    /**
     * Find Department using its id
     *
     * @param id Id of Department
     * @return Department instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public Department findDepartmentById (Long id) throws ServiceGeneralException {
        try {
            if (id != null) {
                return (Department) departmentDao.findById(id);
            } else {
                LOG.warn ("Cannot find department: Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get Department by id: ", e);
        }
    }

    /**
     * Adding department to source of information
     *
     * @param department Department instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void addDepartment(Department department) throws ServiceGeneralException {
        try {
            if (department != null) {
                departmentDao.add (department);
            } else {
                LOG.warn ("Cannot add department, object is null");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot add Department: ", e);
        }
    }

    /**
     * Updating department
     *
     * @param department Department instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void updateDepartment(Department department) throws ServiceGeneralException {
        try {
            if (department != null && department.getId() != null) {
                departmentDao.update (department);
            } else {
                LOG.warn ("Cannot update department, object is null or id is not set");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot update Department: ", e);
        }
    }

    /**
     * Removing department
     *
     * @param department Department instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     * @throws ServiceConstraintViolationException if constraint violation occurs
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteDepartment(Department department)
            throws ServiceGeneralException, ServiceConstraintViolationException {
        try {
            if (department != null && department.getId() != null) {
                departmentDao.remove(department);
            } else {
                LOG.warn ("Cannot delete department, object is null or id is not set");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot delete Department: ", e);
        } catch (DaoConstraintViolationException e) {
            throw new ServiceConstraintViolationException
                    ("Service cannot delete Department because of constraint violation: ", e);
        }
    }
}
