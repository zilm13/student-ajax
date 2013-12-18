package it._7bits.web.student.dao.hibernate;

import it._7bits.web.student.dao.DaoGeneralException;
import it._7bits.web.student.dao.IEntityDao;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of common DAO using Hibernate with JPA
 */
public class EntityDaoJpa<Entity extends Pojo,Pojo> implements IEntityDao<Entity, Pojo> {
    private String entityName;
    private Class entityClass;
    private Class pojoClass;

    protected final Logger LOG = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Default constructor required by Java
     * @throws DaoGeneralException every time is used, no default constructor can be available
     */
    public EntityDaoJpa () throws DaoGeneralException {
        throw new DaoGeneralException ("Class cannot be initiated w/o parameters", new Exception());
    }

    /**
     * Class constructor to get all data we should have here
     * entityName can be read from entityClass but I'm too young for it :)
     * @param entityClass    Entity Class
     * @param entityName     Name of Entity
     * @param pojoClass      Pojo Class w/o annotations and all other heavy stuff
     */
    public EntityDaoJpa (Class<Entity> entityClass, String entityName, Class<Pojo> pojoClass) {
        this.entityClass = entityClass;
        this.entityName = entityName;
        this.pojoClass = pojoClass;
    }

    /**
     * Lists all entities from DataBase and converts them to Pojo objects
     * @return List with Pojo objects
     * @throws DaoGeneralException is smth goes wrong with DB
     */
    @Override
    public List<Pojo> findAll() throws DaoGeneralException {
        try {
            List<Entity> entityList = entityManager
                    .createQuery("from " + entityName, entityClass)
                    .getResultList();
            return entityListToPojoList(entityList);
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list all entities (" +
                    entityName + ") via JPA: ", e);
        }
    }

    /**
     * Lists entities from DataBase using parametrized query,
     * and converts them to Pojo objects
     * @param query Parametrized query
     * @param parameters    Parameters for query
     * @return List with Pojo objects
     * @throws DaoGeneralException is smth goes wrong with DB
     */
    @Override
    public List<Pojo> findByQuery(String query, Map<String, Object> parameters) throws DaoGeneralException {
        if (query == null || query.isEmpty()) {
            return null;
        }
        try {
            TypedQuery<Entity> typedQuery = entityManager.createQuery(query, entityClass);
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String,Object> parameter: parameters.entrySet()) {
                    typedQuery.setParameter (parameter.getKey(), parameter.getValue());
                }
            }
            List<Entity> entityList =  typedQuery.getResultList();
            return entityListToPojoList(entityList);
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot list entities (" + 
                   entityName + ") with custom query via JPA: ", e);
        }

    }

    /**
     * Find entity by its id in DB, convert to Pojo and return
     * @param id    id of Entity
     * @return Pojo object converted from Entity with requested id
     * @throws DaoGeneralException is msth goes wrong with DB
     */
    @Override
    public Pojo findById(Long id) throws DaoGeneralException {
        if (id == null) return null;
        try {
            Entity entity = (Entity) entityManager.find (entityClass, id);
            return entityToPojo (entity);
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot get entity (" +
                    entityName + ") by Id via JPA: ", e);
        }
    }

    /**
     * Add object to database. Accepts Pojo object, converts it to Entity,
     * and then adds it to DB
     * @param pojo    Pojo object
     * @throws DaoGeneralException is smth goes wrong with DB
     */
    @Override
    public void add (Pojo pojo) throws DaoGeneralException {
        try {
            Entity entity = pojoToEntity (pojo);
            if (entity != null) {
                entityManager.persist (entity);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot add entity (" +
                    entityName + ") via JPA: ", e);
        }
    }

    /**
     * Update object in database. Accepts Pojo object, converts it to Entity,
     * and then update it in DB
     * @param pojo    Pojo object
     * @throws DaoGeneralException if smth goes wrong with DB
     */
    @Override
    public void update (Pojo pojo) throws DaoGeneralException {
        try {
            Entity entity = pojoToEntity (pojo);
            if (entity != null) {
                entityManager.merge (entity);
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot update entity (" +
                    entityName + " ) via JPA: ", e);
        }
    }

    /**
     * Remove object from database. Accepts Pojo object, converts it to Entity,
     * and then remove it from DB
     * @param pojo    Pojo object
     * @throws DaoGeneralException if smth goes wrong with DB
     */
    @Override
    public void remove (Pojo pojo) throws DaoGeneralException {
        try {
            Entity entity = pojoToEntity (pojo);
            if (entity !=  null) {
                if (entityManager.contains (entity)) {
                    entityManager.remove (entity);
                } else {
                    entityManager.remove (entityManager.merge (entity));
                }
            }
        } catch (Exception e) {
            throw new DaoGeneralException ("Cannot delete entity (" +
                    entityName + ") via JPA: ", e);
        }
    }

    /**
     * Returns entity name
     * Useful to form queries in service layer
     * @return
     */
    @Override
    public String getEntityName() {
        return entityName;
    }

    /**
     * Method converts Entity List to Pojo Array List
     * @param entityList    List with Entities
     * @return List with Pojos
     */
    private List<Pojo> entityListToPojoList (final List<Entity> entityList) {
        try {
            List<Pojo> pojoList = new ArrayList<>();
            for (Entity entity: entityList) {
                Pojo pojo = (Pojo) pojoClass
                        .getDeclaredConstructor(pojoClass)
                        .newInstance(entity);
                pojoList.add (pojo);
            }
            return pojoList;
        } catch (Exception e) {
            LOG.error ("Dao could not convert entity list to pojo list");
            return null;
        }
    }

    /**
     * Method converts Entity object to Pojo one
     * @param entity    Entity object
     * @return Pojo object
     */
    private Pojo entityToPojo (final Entity entity) {
        try {
            Pojo pojo = (Pojo) pojoClass
                    .getDeclaredConstructor(pojoClass)
                    .newInstance(entity);
            return pojo;
        } catch (Exception e) {
            LOG.error ("Dao could not convert entity to pojo");
            return null;
        }
    }

    /**
     * Method converts Pojo object to Entity one
     * @param pojo    Pojo object
     * @return Entity object
     */
    private Entity pojoToEntity (final Pojo pojo) {
        try {
            if (pojo != null) {
                Entity entity = (Entity) entityClass
                        .getDeclaredConstructor(pojoClass)
                        .newInstance(pojo);
                return entity;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.error ("Dao could not convert pojo to entity");
            return null;
        }
    }
}
