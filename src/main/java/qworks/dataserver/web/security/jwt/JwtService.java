/**
 * 
 */
package qworks.dataserver.web.security.jwt;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import qworks.dataserver.model.User;
import qworks.dataserver.util.LRUCache;

/**
 * @author jmgarcia
 *
 */
@ApplicationScoped
public class JwtService implements Serializable {

	private static final long serialVersionUID = -4279438335618426965L;
	private static final Logger LOG = LoggerFactory.getLogger(JwtService.class);
	private static final Calendar currentCalendar = Calendar.getInstance();
	private static final String SECRET = UUID.randomUUID().toString();
	
	/** */
	public static final int TOKEN_VALIDITY_MINUTES = 30; 
	/** */
	public static final int MAX_USERS = 20; 
	
	// private LRUCache<String, User> cache = null;
	private Map<String, User> cache = null;
	
	/**
	 * 
	 */
	public JwtService() {
		super();
		cache = new LRUCache<String, User>(TOKEN_VALIDITY_MINUTES*60, MAX_USERS);
	}


	/**
	 * Creation of valid JWT Token
	 * @param user
	 * @return
	 */
	public JwtResponse createToken(User user) {
		
		LOG.debug("Creating JWT token for {}", user.getUsername());
		
		String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(getCurrentDate())
                .setExpiration(getExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
		
    	cache.put(jwt, user);
    	
        return new JwtResponse(jwt);
		
    }

	
	/**
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public Claims parseTokenClaims(String jwt) throws Exception {
		
		LOG.debug("Validating JWT token");
		
		return Jwts.parser()
		            .setSigningKey(SECRET.getBytes())
		            .parseClaimsJws(jwt).getBody();
    	
    }
	
	/**
	 * @param jwt
	 * @return
	 */
	public User validateToken(String jwt) {
		Claims claims = null;
		
		try {
			claims = Jwts.parser()
		         .setSigningKey(SECRET.getBytes())
		         .parseClaimsJws(jwt).getBody();	
		}
		catch (Exception e) {
			return null;
		}
		String subj = claims.getSubject();
		User user = cache.get(jwt);
		if ((user != null) && (user.getUsername().equals(subj))) {
			return user;
		}
		else {
			return null;
		}
	
	}
	
	
	
	private Date getCurrentDate() {
		return currentCalendar.getTime();
	}
	
	private Date getExpirationDate() {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MINUTE, TOKEN_VALIDITY_MINUTES);
		return cal.getTime();
	}
}
