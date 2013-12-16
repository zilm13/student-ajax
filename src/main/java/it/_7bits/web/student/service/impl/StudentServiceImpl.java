package it._7bits.web.student.service.impl;

import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.IStudentDao;
import it._7bits.web.student.domain.Student;
import it._7bits.web.student.service.IStudentService;
import it._7bits.web.student.service.ServiceGeneralException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation for Student service layer
 */
@Service
public class StudentServiceImpl implements IStudentService {

    protected final Logger LOG = Logger.getLogger(getClass());

    @Autowired
    private IStudentDao studentDao;


    /**
     * List all Students
     *
     * @return list of Students
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<Student> findAllStudents() throws ServiceGeneralException {
        try {
            return studentDao.findAll();
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot list all Students: ", e);
        }
    }

    /**
     * List all Students in exact Group
     *
     * @param groupId Id of Group
     * @return List of Students
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<Student> findStudentsInGroup (Long groupId)
            throws ServiceGeneralException {
        try {
            if (groupId != null) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put ("groupId", groupId);
                return studentDao.
                        findByQuery("from " + studentDao.getEntityName() +
                                " where groupId = :groupId", parameters);
            } else {
                LOG.warn ("Cannot list Students in group: input Group Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get Students in Group: ", e);
        }
    }

    /**
     * List all Students in exact SubDepartment
     *
     * @param subDepartmentId Id of SubDepartment
     * @return List of Students
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<Student> findStudentsInSub (Long subDepartmentId)
            throws ServiceGeneralException {
        try {
            if (subDepartmentId != null) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put ("subDepartmentId", subDepartmentId);
                return studentDao.
                        findByQuery("from " + studentDao.getEntityName() +
                                " where subDepartmentId = :subDepartmentId", parameters);
            } else {
                LOG.warn ("Cannot list Students in SubDepartment: input Group Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get Students in SubDepartment: ", e);
        }
    }

    /**
     * List all Head students in exact Group
     *
     * @param groupId Id of Group
     * @return List of Head students
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public List<Student> findHeadStudentsInGroup (Long groupId)
            throws ServiceGeneralException {
        try {
            if (groupId != null) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put ("groupId", groupId);
                parameters.put("isHead", true);
                return studentDao.
                        findByQuery("from "+ studentDao.getEntityName() +
                                " where groupId = :groupId and isHead = :isHead", parameters);
            } else {
                LOG.warn ("Cannot list Student Heads in group: input Group Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get Student Heads in Group: ", e);
        }
    }

    /**
     * Find Student using its id
     *
     * @param id Id of Student
     * @return Student instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public Student findStudentById (Long id)
            throws ServiceGeneralException {
        try {
            if (id != null) {
                return studentDao.findById(id);
            } else {
                LOG.warn ("Cannot find Student: Id is null");
                return null;
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot get Student by id: ", e);
        }
    }

    /**
     * Adding student to source of information
     *
     * @param student Student instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void addStudent (Student student) throws ServiceGeneralException {
        try {
            if (student != null) {
                studentDao.add (student);
            } else {
                LOG.warn ("Cannot add Student, object is null");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot add Student: ", e);
        }
    }

    /**
     * Updating student
     *
     * @param student Student instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void updateStudent (Student student) throws ServiceGeneralException {
        try {
            if (student != null && student.getId() != null) {
                studentDao.update (student);
            } else {
                LOG.warn ("Cannot update Student, object is null or id is not set");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot update Student: ", e);
        }
    }

    /**
     * Removing student
     *
     * @param student Student instance
     * @throws ServiceGeneralException if error occurs while obtaining information
     */
    @Override
    @Transactional
    public void deleteStudent (Student student) throws ServiceGeneralException {
        try {
            if (student != null && student.getId() != null) {
                studentDao.remove (student);
            } else {
                LOG.warn ("Cannot delete Student, object is null or id is not set");
            }
        } catch (DaoGeneralException e) {
            throw new ServiceGeneralException ("Service cannot delete Student: ", e);
        }
    }

    /**
     * Default setter
     * @param studentDao IGenericStudentDao instance
     */
    public void setStudentDao(IStudentDao studentDao) {
        this.studentDao = studentDao;
    }
}
