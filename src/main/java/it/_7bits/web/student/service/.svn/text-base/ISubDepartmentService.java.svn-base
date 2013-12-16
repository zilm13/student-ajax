package it._7bits.web.student.service;

import it._7bits.web.student.domain.SubDepartment;

import java.util.List;

/**
 * Service layer interface for SubDepartment
 */
public interface ISubDepartmentService {

    /**
     * List all SubDepartments
     * @return list of SubDepartments
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<SubDepartment> findAllSubDepartments ()
            throws ServiceGeneralException;

    /**
     * List all SubDepartments in exact Department
     * @param departmentId Id of department
     * @return List of SubDepartments
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<SubDepartment> findSubDepartmentsInDep (final Long departmentId)
            throws ServiceGeneralException;

    /**
     * Find SubDepartment using its id
     * @param id    Id of SubDepartment
     * @return SubDepartment instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public SubDepartment findSubDepartmentById (final Long id)
            throws ServiceGeneralException;

    /**
     * Adding subDepartment to source of information
     * @param subDepartment    SubDepartment instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void addSubDepartment (final SubDepartment subDepartment)
            throws ServiceGeneralException;

    /**
     * Updating subDepartment
     * @param subDepartment    SubDepartment instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void updateSubDepartment (final SubDepartment subDepartment)
            throws ServiceGeneralException;

    /**
     * Removing subDepartment
     * @param subDepartment    SubDepartment instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void deleteSubDepartment (final SubDepartment subDepartment)
            throws ServiceGeneralException;
}
