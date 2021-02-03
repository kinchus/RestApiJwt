/**
 * 
 */
package qworks.dataserver.dao.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import qworks.dataserver.dao.GenericDao;
import qworks.dataserver.dao.RoleDao;
import qworks.dataserver.dao.entity.RoleEntity;


/**
* @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
@SessionScoped
public class RoleDaoImpl extends GenericDao<RoleEntity, Long> implements RoleDao {

	private static final long serialVersionUID = -54126115278005502L;

	private static Map<String, RoleEntity> rolesMap = new HashMap<String, RoleEntity>();
	private static List<RoleEntity> systemRoles = new ArrayList<RoleEntity>();
	static {
		rolesMap.put(RoleEntity.ADMINISTRATOR.getName(), RoleEntity.ADMINISTRATOR);
		rolesMap.put(RoleEntity.CUSTOMER.getName(), RoleEntity.CUSTOMER);
		systemRoles.add(RoleEntity.ADMINISTRATOR);
		systemRoles.add(RoleEntity.CUSTOMER);
	}
	
	/**
	 * @return
	 * @see qworks.dataserver.dao.RoleDao#getSystemRoles()
	 */
	@Override
	public List<RoleEntity> getSystemRoles() {
		return systemRoles;
	}
	
	
	/**
	 * @param roleName
	 * @return
	 * @see qworks.dataserver.dao.RoleDao#findByName(java.lang.String)
	 */
	@Override
	public RoleEntity findByName(String roleName) {
		return rolesMap.get(roleName);
	}
	
	
}
