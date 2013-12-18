package it._7bits.web.student.dao;

import java.util.List;
import java.util.Map;

/**
 * Common DAO Interface
 */
public interface IEntityDao<Entity,Pojo> {

    /**
     * Lists all entities from Source and converts them to Pojo objects
     * @return List with Pojo objects
     * @throws DaoGeneralException is smth goes wrong with Source
     */
    public List<Pojo> findAll() throws DaoGeneralException;

    /**
     * Lists entities from Source using parametrized query,
     * and converts them to Pojo objects
     * @param query Parametrized query
     * @param parameters    Parameters for query
     * @return List with Pojo objects
     * @throws DaoGeneralException is smth goes wrong with Source
     */
    public List<Pojo> findByQuery (final String query,
                                  final Map<String,Object> parameters) throws DaoGeneralException;

    /**
     * Find entity by its id in Source, convert to Pojo and return
     * @param id    id of Entity
     * @return Pojo object converted from Entity with requested id
     * @throws DaoGeneralException is msth goes wrong with Source
     */
    public Pojo findById (final Long id) throws DaoGeneralException;

    /**
     * Add object to database. Accepts Pojo object, converts it to Entity,
     * and then adds it to Source
     * @param pojo    Pojo object
     * @throws DaoGeneralException is smth goes wrong with Source
     */
    public void add (final Pojo pojo) throws DaoGeneralException;

    /**
     * Update object in database. Accepts Pojo object, converts it to Entity,
     * and then update it in Source
     * @param pojo    Pojo object
     * @throws DaoGeneralException if smth goes wrong with Source
     */
    public void update (final Pojo pojo) throws DaoGeneralException;

    /**
     * Remove object from database. Accepts Pojo object, converts it to Entity,
     * and then remove it from Source
     * @param pojo    Pojo object
     * @throws DaoGeneralException if smth goes wrong with Source
     */
    public void remove (final Pojo pojo) throws DaoGeneralException;


    /**
     * Returns entity name
     * Useful to form queries in service layer
     * @return
     */
    public String getEntityName();
}
