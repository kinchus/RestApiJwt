/**
 * 
 */
package qworks.dataserver.dao.mongo;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import dev.morphia.Datastore;
import qworks.dataserver.dao.GenericDao;
import qworks.dataserver.dao.UserDao;
import qworks.dataserver.dao.entity.UserEntity;

/**
* @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
@Named
@SessionScoped
public class UserDaoImpl extends GenericDao<UserEntity, Long> implements UserDao {

	private static final long serialVersionUID = -7004856610947020380L;

	/**
	 * 
	 */
	public UserDaoImpl() {
		super(UserEntity.class);
	}
	
	/**
	 * @param datastore
	 */
	public UserDaoImpl(Datastore datastore) {
		super(UserEntity.class, datastore);
	}

	/**
	 * @param username
	 * @return
	 * @see qworks.dataserver.dao.UserDao#findByUsername(java.lang.String)
	 */
	@Override
	public UserEntity findByUsername(String username) {
		return getQuery().field("username").equal(username).first();
	}

	/**
	 * @param email
	 * @return
	 * @see qworks.dataserver.dao.UserDao#findByEmail(java.lang.String)
	 */
	@Override
	public UserEntity findByEmail(String email) {
		return getQuery().field("username").equal(email).first();
	}
	
	/**
	 * @param entities
	 * @see qworks.dataserver.dao.UserDao#deleteAll(java.lang.Iterable)
	 */
	@Override
	public void deleteAll(Iterable<? extends UserEntity> entities) {
		
		
	}


}
