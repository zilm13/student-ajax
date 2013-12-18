package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.IEntityDao;
import it._7bits.web.student.dao.hibernate.entity.StudentEntity;
import it._7bits.web.student.domain.Student;

/**
 * Student DAO implementation for Hibernate using JPA
 */
public class StudentDaoJpa extends EntityDaoJpa<StudentEntity,Student>
        implements IEntityDao<StudentEntity,Student> {

    public StudentDaoJpa (Class<StudentEntity> entityClass,
                             String entityName,
                             Class<Student> pojoClass) {
        super (entityClass, entityName, pojoClass);
    }
}
