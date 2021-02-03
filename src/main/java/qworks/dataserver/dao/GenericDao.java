/**
 * 
 */
package qworks.dataserver.dao;

import java.io.Serializable;
import java.util.List;

import com.mongodb.WriteResult;

import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;
import dev.morphia.query.internal.MorphiaCursor;
import qworks.dataserver.dao.entity.id.IdEntity;
import qworks.dataserver.dao.mongo.MongoAdaptor;
import qworks.dataserver.dao.mongo.MongoManager;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 * @param <T>
 * @param <K>
 */
public class GenericDao<T extends IdEntity<K>,K extends Serializable> 
		extends MongoAdaptor 
		implements Dao<T, K>, Serializable {

	private static final long serialVersionUID = 6314714981317571793L;
	
	private Class<T> persistentClass = null;
	
	/**
	 * 
	 */
	public GenericDao() {
		super();
	}

	/**
	 * @param clazz
	 */
	public GenericDao(Class<T> clazz) {
		super(MongoManager.getInstance().getDatastore());
		this.persistentClass = clazz;
	}
	
	/**
	 * @param clazz
	 * @param datastore
	 */
	public GenericDao(Class<T> clazz, Datastore datastore) {
		super(datastore);
		this.persistentClass = clazz;
	}
	
	/**
	 * @return
	 * @see qworks.dataserver.dao.Dao#findAll()
	 */
	@Override
	public List<T> findAll() {
		// QueryResults<T> res = getDao().find();
		MorphiaCursor<T> cur = getQuery().find();
		// cur.getServerAddress();
		// cur.getServerCursor();
		return cur.toList();
	}

	/**
	 * @param id
	 * @return
	 * @see qworks.dataserver.dao.Dao#findById(java.io.Serializable)
	 */
	@Override
	public T findById(K id) {
		return getQuery().field("_id").equal(id).first();
	}
	
	
	/**
	 * @param ids
	 * @return
	 */
	@Override
	public List<T> findByIds(List<K> ids) {
		return list(getQuery().field("_id").in(ids));
	}

	/**
	 * @param object
	 * @return
	 * @see qworks.dataserver.dao.Dao#save(qworks.dataserver.dao.entity.id.IdEntity)
	 */
	@Override
	public T save(T object) {
		
		getDatastore().save(object);
		return object;
	}
	
	/**
	 * @param object
	 * @return
	 * @see qworks.dataserver.dao.Dao#delete(qworks.dataserver.dao.entity.id.IdEntity)
	 */
	@Override
	public int delete(T object) {
		WriteResult res = getDatastore().delete(object);
		return res.getN();
	}
	
	/**
	 * @param objects
	 * @return
	 * @see qworks.dataserver.dao.Dao#delete(java.util.List)
	 */
	@Override
	public int delete(List<T> objects) {
		WriteResult res = getDatastore().delete(objects);
		return res.getN();
	}
		
		

	/**
	 * @return
	 */
	protected Query<T> getQuery() {
		return getQuery(persistentClass);
	}

	
	/**
	 * @return
	 */
	protected UpdateOperations<T> createUpdateOperations() {
		return createUpdateOperations(persistentClass);
	}

}
