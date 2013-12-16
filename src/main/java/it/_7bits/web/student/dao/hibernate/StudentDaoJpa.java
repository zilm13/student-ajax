package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.IStudentDao;
import it._7bits.web.student.dao.hibernate.entity.StudentEntity;
import it._7bits.web.student.domain.Student;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * Student DAO implementation for Hibernate using JPA
 */
public class StudentDaoJpa implements IStudentDao {

    protected final Logger LOG = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;
    private Class entityClass = StudentEntity.class;

    /**
     * Find all Student occurrences
     *
     * @return List with all Students found
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public List<Student> findAll() throws DaoGeneralException {
        try {
            return (List<Student>)entityManager.
                    createQuery("from " + entityClass.getName(), entityClass)
                    .getResultList();
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list all students via JPA: ", e);
        }
    }

    /**
     * Find all Students using query
     *
     * @param query      Named query
     * @param parameters Parameters to use in query
     * @return List of all Students returned from source using corresponding query
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public List<Student> findByQuery(String query, Map<String, Object> parameters) throws DaoGeneralException {
        if (query == null || query.isEmpty()) {
            return null;
        }
        try {
            TypedQuery<StudentEntity> typedQuery = entityManager.createQuery(query, entityClass);
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String,Object> parameter: parameters.entrySet()) {
                    typedQuery.setParameter (parameter.getKey(), parameter.getValue());
                }
            }
            return (List<Student>)(List<?>) typedQuery.getResultList();
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list students with custom query via JPA: ", e);
        }
    }

    /**
     * Find Student using its id
     *
     * @param id Id of Student
     * @return Student
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public Student findById(Long id) throws DaoGeneralException {
        if (id == null) return null;
        try {

            return (Student) entityManager.find(entityClass, id);
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot get student by Id via JPA: ", e);
        }
    }

    /**
     * Add new Student to source.
     * Assumes it does not exist in it.
     *
     * @param student new Student, id should be null if auto-generated
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void add(Student student) throws DaoGeneralException {
        try {
            if (student != null) {
                StudentEntity toAdd = (StudentEntity) entityClass
                        .getDeclaredConstructor (Student.class)
                        .newInstance (student);
                entityManager.persist (toAdd);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot add student via JPA: ", e);
        }
    }

    /**
     * Update Student which is already in source
     *
     * @param student Student to update
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void update(Student student) throws DaoGeneralException {
        try {
            if (student != null) {
                StudentEntity toUpdate = (StudentEntity) entityClass
                        .getDeclaredConstructor (Student.class)
                        .newInstance (student);
                entityManager.merge (toUpdate);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot update student via JPA: ", e);
        }
    }

    /**
     * Remove Student from the source
     *
     * @param student Student to remove
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void remove(Student student) throws DaoGeneralException {
        try {
            if (student != null) {
                StudentEntity toDelete = (StudentEntity) entityClass
                        .getDeclaredConstructor (Student.class)
                        .newInstance (student);
                if (entityManager.contains (toDelete)) {
                    entityManager.remove (toDelete);
                } else {
                    entityManager.remove (entityManager.merge (toDelete));
                }
                //entityManager.flush();
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot delete student via JPA: ", e);
        }
    }

    /**
     * Get Student Entity name from Dao.
     * Useful to create queries in Service layer
     *
     * @return Student Entity name
     */
    @Override
    public String getEntityName() {
        return entityClass.getName();
    }
}
