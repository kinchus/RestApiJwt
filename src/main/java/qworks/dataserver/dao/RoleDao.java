package qworks.dataserver.dao;

import java.util.List;

import qworks.dataserver.dao.entity.RoleEntity;

/**
 * DAO for Role entities 
* @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public interface RoleDao extends Dao<RoleEntity, Long> {

	
	/**
	 * Get the system-defined roles 
	 * @return List containing the System roles
	 */
	List<RoleEntity> getSystemRoles();
	
	
	/**
	 * @param roleName
	 * @return Matching Role entity or NULL if none found
	 */
	RoleEntity findByName(String roleName);

}
