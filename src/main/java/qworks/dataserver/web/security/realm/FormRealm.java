package qworks.dataserver.web.security.realm;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;

import qworks.dataserver.model.User;
import qworks.dataserver.service.UserLoginService;
import qworks.dataserver.service.exception.ServiceError;


/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
@SessionScoped
public class FormRealm extends AuthenticatingRealm implements Serializable {

	private static final long serialVersionUID = 4276133837693362209L;
	/** */
	public static final String REALM_NAME = "FormRealm";
	
	@Inject
    private UserLoginService loginService;

    
	/**
	 * 
	 */
	public FormRealm() {
		setName(REALM_NAME);
		setCredentialsMatcher(new SimpleCredentialsMatcher());
	}

    /**
     *
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof UsernamePasswordToken;
    }

    /**
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        
    	UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        
        User user;
		try {
			user = getUserLoginService().login(upToken.getUsername(), upToken.getPassword());
			return new SimpleAccount(user, upToken.getCredentials(), getName());
		} catch (ServiceError e) {
			throw new AuthenticationException("Authentication failed for user " + upToken.getUsername(), e);
		}
       
        // AuthorizationInfo info = realm.getAuthorizationInfo(subject);
        // return new SimpleAccount(upToken.getPrincipal().toString(), upToken.getCredentials(), getName());
    }
    
    /**
	 * @return the userLoginService
	 */
	private UserLoginService getUserLoginService() throws ServiceError {
		if (loginService == null) {
			loginService = javax.enterprise.inject.spi.CDI.current().select(UserLoginService.class).get();
		}
		if (loginService == null) {
			throw new ServiceError("Couldn't get UserLoginService Bean!!");
		}
		return loginService;
	}

}