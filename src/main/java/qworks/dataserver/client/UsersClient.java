package qworks.dataserver.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import qworks.dataserver.model.User;
import qworks.dataserver.util.AplicacionBase;

/**
 * 
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 */
public class UsersClient extends AplicacionBase {
	
	private Client client;
	private WebTarget webTarget;
	private String token;

    /**
     * Constructor 
     */
    public UsersClient() {
    	client = ClientBuilder.newBuilder().build();
    	webTarget = client.target("http://127.0.0.1:8080/dataserver/").path("api/users");
    	
    }
    
    /**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param userName
	 * @param email
	 * @return
	 */
	public Response createUser(String userName, String email) {
    	User user = new User(userName);
    	user.setEmail(email);
    	
    	Invocation.Builder invocationBuilder = webTarget
                .request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Bearer", getToken());
        return  invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
    	
    }
	
	/**
	 * @return
	 */
	public Response getUsers() {
    	
    	Invocation.Builder invocationBuilder = webTarget
                .request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Bearer", getToken());
        return  invocationBuilder.get();
    	
    }
    
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

    	
    	UsersClient client = new UsersClient();
        
    	String userName = readString("Nuevo usuario: ");
    	String userEmail = readString("Nuevo email: ");
    	
    	Response res = client.createUser(userName, userEmail);
    	System.out.println("Response: " + res.getStatus());
    	
    	res = client.getUsers();
    	System.out.println("Response: " + res.getStatus());
    }
   
    
}
        