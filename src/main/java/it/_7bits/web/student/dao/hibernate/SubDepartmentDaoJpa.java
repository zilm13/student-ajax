package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.IEntityDao;
import it._7bits.web.student.dao.hibernate.entity.SubDepartmentEntity;
import it._7bits.web.student.domain.SubDepartment;

/**
 * SubDepartment DAO implementation for Hibernate using JPA
 */
public class SubDepartmentDaoJpa extends EntityDaoJpa<SubDepartmentEntity,SubDepartment>
        implements IEntityDao<SubDepartmentEntity,SubDepartment> {

    public SubDepartmentDaoJpa (Class<SubDepartmentEntity> entityClass,
                             String entityName,
                             Class<SubDepartment> pojoClass) {
        super (entityClass, entityName, pojoClass);
    }
}
