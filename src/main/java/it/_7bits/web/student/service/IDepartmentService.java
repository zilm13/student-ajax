package it._7bits.web.student.service;

import it._7bits.web.student.domain.Department;

import java.util.List;

/**
 * Service layer interface for Department
 */
public interface IDepartmentService {

    /**
     * List all Departments
     * @return list of Departments
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<Department> findAllDepartments () throws ServiceGeneralException;

    /**
     * Find Department using its id
     * @param id    Id of Department
     * @return Department instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public Department findDepartmentById (final Long id) throws ServiceGeneralException;

    /**
     * Adding department to source of information
     * @param department    Department instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void addDepartment (final Department department) throws ServiceGeneralException;

    /**
     * Updating department
     * @param department    Department instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void updateDepartment (final Department department) throws ServiceGeneralException;

    /**
     * Removing department
     * @param department    Department instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     * @throws ServiceConstraintViolationException if constraint violation occurs
     */
    public void deleteDepartment (final Department department)
            throws ServiceGeneralException, ServiceConstraintViolationException;
}
