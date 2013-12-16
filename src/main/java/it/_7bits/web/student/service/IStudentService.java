package it._7bits.web.student.service;


import it._7bits.web.student.domain.Student;

import java.util.List;

/**
 * Service layer interface for Student
 */
public interface IStudentService {

    /**
     * List all Students
     * @return list of Students
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<Student> findAllStudents () throws ServiceGeneralException;

    /**
     * List all Students in exact Group
     * @param groupId Id of Group
     * @return List of Students
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<Student> findStudentsInGroup (final Long groupId)
            throws ServiceGeneralException;

    /**
     * List all Students in exact SubDepartment
     * @param subDepartmentId Id of SubDepartment
     * @return List of Students
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<Student> findStudentsInSub (final Long subDepartmentId)
            throws ServiceGeneralException;

    /**
     * List all Head students in exact Group
     * @param groupId Id of Group
     * @return List of Head students
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public List<Student> findHeadStudentsInGroup (final Long groupId)
            throws ServiceGeneralException;

    /**
     * Find Student using its id
     * @param id    Id of Student
     * @return Student instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public Student findStudentById (final Long id) throws ServiceGeneralException;

    /**
     * Adding student to source of information
     * @param student    Student instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void addStudent (final Student student) throws ServiceGeneralException;

    /**
     * Updating student
     * @param student    Student instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void updateStudent (final Student student) throws ServiceGeneralException;

    /**
     * Removing student
     * @param student    Student instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    public void deleteStudent (final Student student) throws ServiceGeneralException;
}

