/**
 * 
 */
package qworks.dataserver.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import org.apache.shiro.authc.AuthenticationToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import qworks.dataserver.service.TokenService;

/**
 * @author jmgarcia
 *
 */
@SessionScoped
public class TokenServiceImpl  implements TokenService {

	private static final long serialVersionUID = -6810226099879965787L;
	private static final int TOKEN_EXPIRATION_SECONDS = 600;
	
	private Map<String,String> tokenMap = new HashMap<String,String>();
	
	/**
	 * 
	 */
	public TokenServiceImpl() {
		
	}

	/**
	 * @param user
	 * @return
	 * @see qworks.dataserver.service.TokenService#createToken(java.lang.String)
	 */
	@Override
	public String createToken(String user) {
		Calendar cal = new GregorianCalendar();
		cal.roll(Calendar.SECOND, TOKEN_EXPIRATION_SECONDS);
		String jwtToken = Jwts.builder()
                .setSubject(user)
                .setExpiration(cal.getTime())
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET.getBytes()
               ).compact();
		tokenMap.put(user, jwtToken);
		return jwtToken;
	}

	/**
	 * @param token
	 * @return
	 * @see qworks.dataserver.service.TokenService#getToken(java.lang.String)
	 */
	@Override
	public AuthenticationToken getToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param token
	 * @see qworks.dataserver.service.TokenService#delete(java.lang.String)
	 */
	@Override
	public void delete(String token) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @see qworks.dataserver.service.TokenService#deleteAll()
	 */
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
