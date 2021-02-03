package qworks.dataserver.web.security.filter;

import java.nio.charset.Charset;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qworks.dataserver.model.User;
import qworks.dataserver.service.exception.ServiceError;
import qworks.dataserver.util.JsonString;
import qworks.dataserver.web.security.jwt.JwtAuthenticationToken;


/**
 * @author jmgarcia
 *
 */
public class JwtAuthenticationFilter extends AuthenticatingFilter implements JwtFilter {

	private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	

	/**
	 * 
	 */
	public JwtAuthenticationFilter() {
        setLoginUrl(LOGIN_URL);
    }
  
   
	/**
	 *
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		
		AuthenticationToken retToken = null;
		
		// User is trying to log in
		if (isLoginRequest(request, response)) {
			LOG.debug("Processing login request");
            String json = IOUtils.toString(request.getInputStream(), Charset.forName(UTF8));
            if (json == null || json.isEmpty()) {
            	LOG.error("Empty login data");
            	retToken = new UsernamePasswordToken();
            }
            else {
	            JsonString object = new JsonString(json);
	            String username = object.getValue(USER_ID, String.class);
	            String password = object.getValue(PASSWORD, String.class);
	            retToken = new UsernamePasswordToken(username, password);
            }
        }
		
		// User is already (supposedly) logged in
		else if (isLoggedAttempt(request, response)) {
        	LOG.debug("Processing (already) logged request");
            String header = getAuthzHeader(request);
            if ((header != null) && (header.startsWith(AUTHENTICATION_SCHEME))) {
            	String token = header.substring(AUTHENTICATION_SCHEME.length()).trim();
            	retToken = buildAuthenticationToken(token);
            }
            else {
            	retToken =  new UsernamePasswordToken();
            }
        }

		return retToken;
	}

	/**
	 *
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		LOG.trace("Access denied");
		Boolean loggedIn = false;
		if (isLoginRequest(request, response) || isLoggedAttempt(request, response)) {
            loggedIn = executeLogin(request, response);
        }

        if (!loggedIn) {
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return loggedIn;
	}
	
	
	private JwtAuthenticationToken buildAuthenticationToken(String token) {
		
		LOG.debug("Processing token {}", token);
        try {
			User user = getJwtService().validateToken(token);
			return new JwtAuthenticationToken(user, token);
		} catch (ServiceError e) {
			LOG.error("Authentication failed: {}", e.getMessage());
		}
        
        throw new AuthenticationException("Token not valid");
 	 }
	
    

}