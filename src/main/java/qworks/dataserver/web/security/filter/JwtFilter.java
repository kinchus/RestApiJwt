package qworks.dataserver.web.security.filter;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import qworks.dataserver.service.exception.ServiceError;
import qworks.dataserver.web.security.jwt.JwtService;
import qworks.dataserver.web.security.realm.JwtRealm;


/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public interface JwtFilter  {

	/** */
	static final String LOGIN_URL = "/api/user/login";
	/** */
	static final String UTF8 = "UTF-8";
	/** */
	static final String AUTHORIZATION_HEADER = "Authorization";
	/** */
	static final String AUTHENTICATION_SCHEME = "Bearer";
	/** */
	static final String REALM = JwtRealm.REALM_NAME;
	/** */
	static final String USER_ID = "userId";
	/** */
	static final String PASSWORD = "password";
	
	/**
	 * 
	 */
	@Inject
	static final JwtService jwtService = javax.enterprise.inject.spi.CDI.current().select(JwtService.class).get();

	/**
	 * @return the jwtService
	 * @throws ServiceError 
	 */
	default JwtService getJwtService() throws ServiceError {
		if (jwtService == null) {
			throw new ServiceError("Unable to inject the JwtService bean");
		}
		return jwtService;
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	default boolean isLoggedAttempt(ServletRequest request, ServletResponse response) {
	    String authzHeader = getAuthzHeader(request);
	    return authzHeader != null;
	}

	/**
	 * @param request
	 * @return
	 */
	default String getAuthzHeader(ServletRequest request) {
	    HttpServletRequest httpRequest = (HttpServletRequest)request;
	    return httpRequest.getHeader(AUTHORIZATION_HEADER);
	}

}