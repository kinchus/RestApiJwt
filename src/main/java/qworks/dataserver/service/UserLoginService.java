/**
 * 
 */
package qworks.dataserver.service;

import java.io.Serializable;

import qworks.dataserver.model.User;
import qworks.dataserver.service.exception.ObjectNotFoundException;
import qworks.dataserver.service.exception.PermissionException;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public interface UserLoginService extends Serializable {
	
	/**
	 * @param id
	 * @param password
	 * @return
	 * @throws ObjectNotFoundException
	 * @throws PermissionException
	 */
	User login(String id, char []password)	throws ObjectNotFoundException, PermissionException;
	
	/**
	 * @param id
	 */
	void logout(String id);


}
