package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.IEntityDao;
import it._7bits.web.student.dao.hibernate.entity.DepartmentEntity;
import it._7bits.web.student.domain.Department;

/**
 * Department DAO implementation for Hibernate using JPA
 */
public class DepartmentDaoJpa extends EntityDaoJpa<DepartmentEntity,Department>
        implements IEntityDao<DepartmentEntity,Department> {

    public DepartmentDaoJpa (Class<DepartmentEntity> entityClass,
                             String entityName,
                             Class<Department> pojoClass) {
        super (entityClass, entityName, pojoClass);
    }
}
