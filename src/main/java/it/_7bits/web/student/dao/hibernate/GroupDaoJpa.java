package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.IGroupDao;
import it._7bits.web.student.dao.hibernate.entity.GroupEntity;
import it._7bits.web.student.domain.Group;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Group Dao implementation for Hibernate using JPA
 */
public class GroupDaoJpa implements IGroupDao {

    protected final Logger LOG = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;
    private Class entityClass = GroupEntity.class;

    /**
     * Find all Group occurrences
     *
     * @return List with all Groups found
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public List<Group> findAll() throws DaoGeneralException {
        try {
            List<GroupEntity> entityList = entityManager.
                    createQuery("from " + entityClass.getName(), entityClass)
                    .getResultList();
            List<Group> pojoList = new ArrayList<Group>();
            for (GroupEntity entity: entityList) {
                pojoList.add (new Group(entity));
            }
            return pojoList;
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list all groups via JPA: ", e);
        }
    }

    /**
     * Find all Groups using query
     *
     * @param query      Named query
     * @param parameters Parameters to use in query
     * @return List of all Groups returned from source using corresponding query
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public List<Group> findByQuery(String query, Map<String, Object> parameters)
            throws DaoGeneralException {
        if (query == null || query.isEmpty()) {
            return null;
        }
        try {
            TypedQuery<GroupEntity> typedQuery = entityManager.createQuery(query, entityClass);
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String,Object> parameter: parameters.entrySet()) {
                    typedQuery.setParameter (parameter.getKey(), parameter.getValue());
                }
            }
            List<GroupEntity> entityList = typedQuery.getResultList();
            List<Group> pojoList = new ArrayList<Group>();
            for (GroupEntity entity: entityList) {
                pojoList.add (new Group(entity));
            }
            return pojoList;
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list groups with custom query via JPA: ", e);
        }
    }

    /**
     * Find Group using its id
     *
     * @param id Id of Group
     * @return Group
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public Group findById(Long id) throws DaoGeneralException {
        if (id == null) return null;
        try {
            GroupEntity entity = (GroupEntity) entityManager.find(entityClass, id);
            return new Group(entity);
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot get group by Id via JPA: ", e);
        }
    }

    /**
     * Add new Group to source.
     * Assumes it does not exist in it.
     *
     * @param group new Group, id should be null if auto-generated
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void add(Group group) throws DaoGeneralException {
        try {
            if (group != null) {
                GroupEntity toAdd = (GroupEntity) entityClass
                        .getDeclaredConstructor (Group.class)
                        .newInstance (group);
                entityManager.persist (toAdd);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot add group via JPA: ", e);
        }
    }

    /**
     * Update Group which is already in source
     *
     * @param group Group to update
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void update(Group group) throws DaoGeneralException {
        try {
            if (group != null) {
                GroupEntity toUpdate = (GroupEntity) entityClass
                        .getDeclaredConstructor (Group.class)
                        .newInstance (group);
                entityManager.merge (toUpdate);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot update group via JPA: ", e);
        }
    }

    /**
     * Remove Group from the source
     *
     * @param group Group to remove
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void remove(Group group) throws DaoGeneralException {
        try {
            if (group != null) {
                GroupEntity toDelete = (GroupEntity) entityClass
                        .getDeclaredConstructor (Group.class)
                        .newInstance (group);
                if (entityManager.contains (toDelete)) {
                    entityManager.remove (toDelete);
                } else {
                    entityManager.remove (entityManager.merge (toDelete));
                }
                //entityManager.flush();
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot delete group via JPA: ", e);
        }
    }

    /**
     * Get Group Entity name from Dao.
     * Useful to create queries in Service layer
     *
     * @return Group Entity name
     */
    @Override
    public String getEntityName() {
        return entityClass.getName();
    }
}
