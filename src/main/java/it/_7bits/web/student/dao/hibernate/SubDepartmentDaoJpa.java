package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.ISubDepartmentDao;
import it._7bits.web.student.dao.hibernate.entity.SubDepartmentEntity;
import it._7bits.web.student.domain.SubDepartment;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SubDepartment DAO implementation for Hibernate using JPA
 */
public class SubDepartmentDaoJpa implements ISubDepartmentDao {

    protected final Logger LOG = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;
    private Class entityClass = SubDepartmentEntity.class;

    /**
     * Find all SubDepartment occurrences
     *
     * @return List with all SubDepartments found
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public List<SubDepartment> findAll() throws DaoGeneralException {
        try {
            List<SubDepartmentEntity> entityList = entityManager.
                    createQuery("from " + entityClass.getName(), entityClass)
                    .getResultList();
            List<SubDepartment> pojoList = new ArrayList<SubDepartment>();
            for (SubDepartmentEntity entity: entityList) {
                pojoList.add (new SubDepartment (entity));
            }
            return pojoList;
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list all subdepartments via JPA: ", e);
        }
    }

    /**
     * Find all SubDepartments using query
     *
     * @param query      Named query
     * @param parameters Parameters to use in query
     * @return List of all SubDepartments returned from source using corresponding query
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public List<SubDepartment> findByQuery(String query, Map<String, Object> parameters)
            throws DaoGeneralException {
        if (query == null || query.isEmpty()) {
            return null;
        }
        try {
            TypedQuery<SubDepartmentEntity> typedQuery = entityManager.createQuery(query, entityClass);
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String,Object> parameter: parameters.entrySet()) {
                    typedQuery.setParameter (parameter.getKey(), parameter.getValue());
                }
            }
            List<SubDepartmentEntity> entityList = typedQuery.getResultList();
            List<SubDepartment> pojoList = new ArrayList<SubDepartment>();
            for (SubDepartmentEntity entity: entityList) {
                pojoList.add (new SubDepartment (entity));
            }
            return pojoList;
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list subdepartments with custom query via JPA: ", e);
        }
    }

    /**
     * Find SubDepartment using its id
     *
     * @param id Id of SubDepartment
     * @return SubDepartment
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public SubDepartment findById(Long id) throws DaoGeneralException {
        if (id == null) return null;
        try {
            SubDepartmentEntity entity = (SubDepartmentEntity) entityManager.find(entityClass, id);
            return new SubDepartment (entity);
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot get subdepartment by Id via JPA: ", e);
        }
    }

    /**
     * Add new SubDepartment to source.
     * Assumes it does not exist in it.
     *
     * @param subDepartment new SubDepartment, id should be null if auto-generated
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void add(SubDepartment subDepartment) throws DaoGeneralException {
        try {
            if (subDepartment != null) {
                SubDepartmentEntity toAdd = (SubDepartmentEntity) entityClass
                        .getDeclaredConstructor (SubDepartment.class)
                        .newInstance (subDepartment);
                entityManager.persist (toAdd);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot add subdepartment via JPA: ", e);
        }
    }

    /**
     * Update SubDepartment which is already in source
     *
     * @param subDepartment SubDepartment to update
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void update(SubDepartment subDepartment) throws DaoGeneralException {
        try {
            if (subDepartment != null) {
                SubDepartmentEntity toUpdate = (SubDepartmentEntity) entityClass
                        .getDeclaredConstructor (SubDepartment.class)
                        .newInstance (subDepartment);
                entityManager.merge (toUpdate);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot update subdepartment via JPA: ", e);
        }
    }

    /**
     * Remove SubDepartment from the source
     *
     * @param subDepartment SubDepartment to remove
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void remove(SubDepartment subDepartment) throws DaoGeneralException {
        try {
            if (subDepartment != null) {
                SubDepartmentEntity toDelete = (SubDepartmentEntity) entityClass
                        .getDeclaredConstructor (SubDepartment.class)
                        .newInstance (subDepartment);
                if (entityManager.contains (toDelete)) {
                    entityManager.remove (toDelete);
                } else {
                    entityManager.remove (entityManager.merge (toDelete));
                }
                //entityManager.flush();
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot delete subdepartment via JPA: ", e);
        }
    }

    /**
     * Get SubDepartment Entity name from Dao.
     * Useful to create queries in Service layer
     *
     * @return SubDepartment Entity name
     */
    @Override
    public String getEntityName() {
        return entityClass.getName();
    }
}
