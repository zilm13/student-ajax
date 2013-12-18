package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.IEntityDao;
import it._7bits.web.student.dao.hibernate.entity.GroupEntity;
import it._7bits.web.student.domain.Group;

/**
 * Group Dao implementation for Hibernate using JPA
 */
public class GroupDaoJpa extends EntityDaoJpa<GroupEntity,Group>
        implements IEntityDao<GroupEntity,Group> {

    public GroupDaoJpa (Class<GroupEntity> entityClass,
                             String entityName,
                             Class<Group> pojoClass) {
        super (entityClass, entityName, pojoClass);
    }
}
