/**
 * 
 */
package qworks.dataserver.dao;

import qworks.dataserver.dao.entity.UserEntity;

/**
 * @author Admin2
 *
 */
public interface UserDao extends Dao<UserEntity, Long> {

	
	/**
	 * Gets the user with the given username (or null if not found)
	 * @param username
	 * @return User entity or NULL if none found
	 */
	UserEntity findByUsername(String username);

	/**
	 * Gets the user with the given email (or null if not found)
	 * @param email
	 * @return User entity or NULL if none found
	 */
	UserEntity findByEmail(String email);

	/**
	 * Batch deletion of the given User entities
	 * @param entities
	 */
	void deleteAll(Iterable<? extends UserEntity> entities);

	
	
}
