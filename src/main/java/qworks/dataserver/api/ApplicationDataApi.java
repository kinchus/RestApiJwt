package qworks.dataserver.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qworks.dataserver.model.ApplicationData;
import qworks.dataserver.model.User;


/**
 * 
 * @author jmgarcia *
 */
@Path("/application-data")
public class ApplicationDataApi {

	private static Logger LOG = LoggerFactory.getLogger(ApplicationDataApi.class);

	
	/**
	 * @param application
	 * @param devEui
	 * @param from
	 * @param to
	 * @return
	 */
	@GET
	@Path("/{application}/{devEui}")
	@RequiresAuthentication
    @Produces(MediaType.APPLICATION_JSON)
	public Response getData(
    		@PathParam("application") String application, 
    		@PathParam("devEui") String devEui, 
    		@QueryParam("dateFrom") Date from, 
    		@QueryParam("dateTo") Date to) {
		
		LOG.info("GET application data from {}:{} ", application, devEui);
		
		final User user = (User) SecurityUtils.getSubject().getPrincipal();
		String accountId = user.getAccountId();
		
		LOG.debug("Requested from user {}/{}", accountId, user.getUsername());
		
		
		List<ApplicationData> ret = new ArrayList<ApplicationData>();
//		if (ret != null) {
			return Response.ok(ret).build();
//		} 
//		else {
//			return Response.status(Response.Status.NOT_FOUND).build();
//		}	
	}
	
	
	/**
	 * @param application
	 * @param devEui
	 * @param date
	 * @param to
	 * @param jsonData
	 * @return
	 */
	@PUT
	@Path("/{application}/{devEui}")
	@RequiresAuthentication
    @Produces(MediaType.APPLICATION_JSON)
	public Response putData(
    		@PathParam("application") String application,
    		@PathParam("devEui") String devEui,
    		@QueryParam("from") Date date, 
    		@QueryParam("to") Date to,
    		String jsonData) {
		
		LOG.info("PUT {}:{} data \"{}\"", application, devEui, jsonData);
		
		final User user = (User) SecurityUtils.getSubject().getPrincipal();
		String accountId = user.getAccountId();

		LOG.debug("Requested from user {}/{}", accountId, user.getUsername());
		
		return Response.ok().build();
	}
	


}
