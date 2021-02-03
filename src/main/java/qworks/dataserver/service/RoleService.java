/**
 * 
 */
package qworks.dataserver.service;

import qworks.dataserver.model.Role;
import qworks.dataserver.service.exception.ServiceError;

/**
 * @author jmgarcia
 *
 */
public interface RoleService extends Service<Role, Long> {

	/**
	 * @param name
	 * @return
	 * @throws ServiceError 
	 */
	Role getByName(String name) throws ServiceError;
}
