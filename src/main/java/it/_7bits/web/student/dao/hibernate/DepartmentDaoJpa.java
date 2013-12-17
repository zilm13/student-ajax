package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.IDepartmentDao;
import it._7bits.web.student.dao.hibernate.entity.DepartmentEntity;
import it._7bits.web.student.domain.Department;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Department DAO implementation for Hibernate using JPA
 */
public class DepartmentDaoJpa implements IDepartmentDao {

    protected final Logger LOG = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;

    private Class entityClass = DepartmentEntity.class;

    /**
     * Find all Department occurrences
     *
     * @return List with all Departments found
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public List<Department> findAll() throws DaoGeneralException {
        try {
            List<DepartmentEntity> entityList = entityManager.
                    createQuery("from " + entityClass.getName(), entityClass)
                    .getResultList();
            List<Department> pojoList = new ArrayList<Department>();
            for (DepartmentEntity entity: entityList) {
                pojoList.add (new Department(entity));
            }
            return pojoList;
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list all departments via JPA: ", e);
        }
    }

    /**
     * Find all Departments using query
     *
     * @param query      Named query
     * @param parameters Parameters to use in query
     * @return List of all Departments returned from source using corresponding query
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public List<Department> findByQuery(String query, Map<String, Object> parameters)
            throws DaoGeneralException {
        if (query == null || query.isEmpty()) {
            return null;
        }
        try {
            TypedQuery<DepartmentEntity> typedQuery = entityManager.createQuery(query, entityClass);
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String,Object> parameter: parameters.entrySet()) {
                    typedQuery.setParameter (parameter.getKey(), parameter.getValue());
                }
            }
            List<DepartmentEntity> entityList =  typedQuery.getResultList();
            List<Department> pojoList = new ArrayList<Department>();
            for (DepartmentEntity entity: entityList) {
                pojoList.add (new Department(entity));
            }
            return pojoList;
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list departments with custom query via JPA: ", e);
        }
    }

    /**
     * Find Department using its id
     *
     * @param id Id of Department
     * @return Department
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public Department findById(Long id) throws DaoGeneralException {
        if (id == null) return null;
        try {
            DepartmentEntity departmentEntity = (DepartmentEntity) entityManager.find(entityClass, id);
            Department department = new Department (departmentEntity);
            return department;
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot get department by Id via JPA: ", e);
        }
    }

    /**
     * Add new Department to source.
     * Assumes it does not exist in it.
     *
     * @param department new Department, id should be null if auto-generated
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void add(Department department) throws DaoGeneralException {
        try {
            if (department != null) {
                DepartmentEntity toAdd = (DepartmentEntity) entityClass
                        .getDeclaredConstructor (Department.class)
                        .newInstance (department);
                entityManager.persist (toAdd);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot add department via JPA: ", e);
        }
    }

    /**
     * Update Department which is already in source
     *
     * @param department Department to update
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void update(Department department) throws DaoGeneralException {
        try {
            if (department != null) {
                DepartmentEntity toUpdate = (DepartmentEntity) entityClass
                        .getDeclaredConstructor (Department.class)
                        .newInstance (department);
                entityManager.merge (toUpdate);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot update department via JPA: ", e);
        }
    }

    /**
     * Remove Department from the source
     *
     * @param department Department to remove
     * @throws it._7bits.web.student.dao.DaoGeneralException
     *          if operation fails
     */
    @Override
    public void remove(Department department) throws DaoGeneralException {
        try {
            if (department != null) {
                DepartmentEntity toDelete = (DepartmentEntity) entityClass
                        .getDeclaredConstructor (Department.class)
                        .newInstance (department);
                if (entityManager.contains (toDelete)) {
                    entityManager.remove (toDelete);
                } else {
                    entityManager.remove (entityManager.merge (toDelete));
                }
                //entityManager.flush();
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot delete department via JPA: ", e);
        }
    }

    /**
     * Get Department Entity name from Dao.
     * Useful to create queries in Service layer
     *
     * @return Department Entity name
     */
    @Override
    public String getEntityName() {
        return entityClass.getName();
    }
}
