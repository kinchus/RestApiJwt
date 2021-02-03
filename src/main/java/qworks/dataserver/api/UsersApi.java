/**
 * 
 */
package qworks.dataserver.api;


import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qworks.dataserver.model.User;
import qworks.dataserver.service.UserService;
import qworks.dataserver.service.exception.ObjectExistsException;
import qworks.dataserver.service.exception.ServiceError;

/**
 * 
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 */
@Path("/users")
public class UsersApi {
	
	private static final Logger LOG = LoggerFactory.getLogger(UsersApi.class);
	
	@Inject 
	private UserService	userService;
	
	/**
	 * @return
	 */
	@GET
	@RequiresAuthentication
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll()
    {
		LOG.debug("API CALL: Get system users");
	    
		List<User> users = null;
		try {
			users = getUserService().getAll();
		} catch (ServiceError e) {
			LOG.error("Exception thrown: {}", e);
			e.printStackTrace();
		}
		
		if (users.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
    	}
		else {
			return Response.ok(users).build();
		}
    }
    
	
	/**
	 * @param user
	 * @return
	 */
	@POST
	@RequiresAuthentication
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user)
    {
		Objects.requireNonNull(user);
		
		LOG.debug("API CALL: Create a new user");
	    
		try {
			getUserService().create(user);
		} catch (ObjectExistsException e) {
			LOG.error("The requested user already exists in the system: {}", e.getMessage());
			return Response.status(Response.Status.CONFLICT).build();
		} catch (ServiceError e) {
			LOG.error("Unable to create user: {}", e.getMessage());
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return Response.ok().build();
    	
    }
    

	/**
	 * @return the loginService
	 */
	private UserService getUserService() {
		if (userService == null)  {
			userService = javax.enterprise.inject.spi.CDI.current().select(UserService.class).get();
		}
		return userService;
	}


	
}
