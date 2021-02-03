package qworks.dataserver.web.security.filter;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qworks.dataserver.model.User;
import qworks.dataserver.service.exception.ServiceError;
import qworks.dataserver.util.JsonString;
import qworks.dataserver.web.security.jwt.JwtResponse;

/**
 * Authenticate the request to url /login by POST with json body '{ username, password }'.
 * If successful, response the client with header 'Authorization: Bearer jwt-token'.
 *
 * @author shuaicj 2018/07/27
 */
public class JwtUsernamePasswordAuthFilter extends OncePerRequestFilter implements JwtFilter {

	private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
    
    /**
     * 
     */
    public JwtUsernamePasswordAuthFilter() {
        
    }

    @Override
    protected boolean isEnabled(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getMethod().equalsIgnoreCase("POST") && req.getServletPath().equals(LOGIN_URL);
    }

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        
    	// try authentication
    	HttpServletResponse rsp = (HttpServletResponse) response;
        Subject subject = SecurityUtils.getSubject();
        String json = IOUtils.toString(request.getInputStream(), Charset.forName(UTF8));
        if (json == null || json.isEmpty()) {
        	LOG.error("Empty login data");
        	rsp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        LOG.debug("Filtered login request ({})", json);
        
        JsonString object = new JsonString(json);
        String username = object.getValue(USER_ID, String.class);
        String password = object.getValue(PASSWORD, String.class);
        
        try {
        	LOG.debug("Attempting login for given credentials");
            subject.login(new UsernamePasswordToken(username, password));
        } catch (AuthenticationException e) {
            LOG.error("Authentication failed:  {}", e.getMessage());
            rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        User user = (User)subject.getPrincipal();
        // AuthorizationInfo info = realm.getAuthorizationInfo(subject);
        JwtResponse token = null;
		try {
			token = getJwtService().createToken(user);
			rsp.addHeader(AUTHORIZATION_HEADER, AUTHENTICATION_SCHEME + " " + token);
		} catch (ServiceError e) {
			LOG.error("Authentication failed: {}", e.getMessage());
			rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
        
    }

}