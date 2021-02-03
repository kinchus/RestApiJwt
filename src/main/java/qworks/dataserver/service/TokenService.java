/**
 * 
 */
package qworks.dataserver.service;

import java.io.Serializable;
import java.util.UUID;

import org.apache.shiro.authc.AuthenticationToken;


/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public interface TokenService extends Serializable {

	/** */
	final static String SECRET = UUID.randomUUID().toString();
	
	
	/**
	 * @param userId
	 * @return
	 */
	String createToken(String userId);

	
	/**
	 * @param token
	 * @return
	 */
	AuthenticationToken getToken(String token);
	
	
	/**
	 * @param token
	 */
	void delete(String token);
	
	
	/**
	 * 
	 */
	void deleteAll();
	
}
