package qworks.dataserver.service.impl;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qworks.dataserver.aws.AWSCognitoService;
import qworks.dataserver.aws.exception.AwsException;
import qworks.dataserver.dao.UserDao;
import qworks.dataserver.dao.entity.UserEntity;
import qworks.dataserver.model.User;
import qworks.dataserver.service.UserLoginService;
import qworks.dataserver.service.exception.PermissionException;
import qworks.dataserver.service.mapper.ServiceMapper;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
@SessionScoped
public class UserLoginServiceImpl implements UserLoginService {

	private static final long serialVersionUID = -6746131196709358679L;
	private static final Logger LOG = LoggerFactory.getLogger(UserLoginServiceImpl.class);

	@Inject 
	private AWSCognitoService awsCognito;
	@Inject 
	private UserDao	userDao;

	/**
	 * 
	 */
	public UserLoginServiceImpl() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws PermissionException
	 * @see qworks.dataserver.service.UserLoginService#login(java.lang.String, char[])
	 */
	@Override
	public User login(String username, char[] password) throws PermissionException {
		
		User ret = null;
		LOG.trace("Validating user {}", username);
		
		try {
			awsCognito.validate(username, password);
		} catch (AwsException e) {
			LOG.error("User {} is not authorized to login: {}", username, e.getMessage());
			throw new PermissionException("Unauthorized login from " + username, e);
		}
		
		UserEntity userEnt = userDao.findByUsername(username);
		if (userEnt != null) {
			ret = getMapper().mapUser(userEnt);
		}
		else {
			LOG.warn("User {} was not found in the database", username);
			ret = new User(username);
		}
		return ret;
	}


	/**
	 * @param id
	 * @see qworks.dataserver.service.UserLoginService#logout(java.lang.String)
	 */
	@Override
	public void logout(String id) {
		try {
			awsCognito.logout(id);
		} catch (AwsException e) {
			LOG.error("An exception was thrown: {}", e.getMessage());
		}
		
	}
	
	private ServiceMapper getMapper()  {
		return ServiceMapper.INSTANCE;
	}
	

}
