/**
 * 
 */
package qworks.dataserver.api;


import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qworks.dataserver.model.User;
import qworks.dataserver.web.security.jwt.JwtService;

/**
 * 
 * @author jmgarcia
 */
@Path("/user")
public class LoginApi {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginApi.class);
	
	@Inject
	private JwtService jwtService;

	/**
	 * @return
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll 
    public Response login()
    {
		LOG.debug("User Login API call ");
	    
		Response.ResponseBuilder rb = null;
		
		try {
			final User user = (User) SecurityUtils.getSubject().getPrincipal();
			rb = Response.ok(getJwtService().createToken(user));
		}
		catch (Exception ex) {
			rb = Response.status(Response.Status.UNAUTHORIZED);
		}
		
	
        return rb.build();
    }
    
	/**
	 * @return 
	 *
	 */
	@POST
    @Path("/logout")
	@RequiresAuthentication
    public Response logout() {
		
		LOG.debug("User Logout API call ");
	    
		final Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			subject.logout();
			return Response.ok().build();
		}
		
		return Response.status(Response.Status.UNAUTHORIZED).build();
		
    }
	
	/**
	 * @return the loginService
	 */
	public JwtService getJwtService() {
		if (jwtService == null)  {
			jwtService = javax.enterprise.inject.spi.CDI.current().select(JwtService.class).get();
		}
		return jwtService;
	}


	
}
