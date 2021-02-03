/**
 * 
 */
package qworks.dataserver.web.security.realm;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import qworks.dataserver.web.security.jwt.JwtAuthenticationToken;
import qworks.dataserver.web.security.jwt.JwtAuthenticationToken.JwtPrincipalCollection;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class JwtRealm extends AuthorizingRealm implements Serializable {

	private static final long serialVersionUID = 6861463674381581114L;
	
	/** */
	public static final String REALM_NAME = "JwtRealm";
	
	/**
	 * 
	 */
	public JwtRealm() {
		setName(REALM_NAME);
        setCredentialsMatcher(new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME));
	}

	/**
	 *
	 */
	@Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtAuthenticationToken;
    }
	
	
	/**
	 *
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException{
		return ((JwtAuthenticationToken) authToken).getJwtAuthenticationInfo(REALM_NAME);
		
		/*
		User user = null;
		String tkUserId = null;
		//JwtAuthenticationToken token = (JwtAuthenticationToken) authToken;
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		
		try {
			user = getLoginService().findById(token.getUsername());
			// tkUserId = getJwtService().parseTokenClaims(token.getToken()).getSubject();
        } catch (Exception e) {
            throw new AuthenticationException("Authentication failed for user " + token.getUsername(), e);
        }
		
		if (tkUserId == user.getUserId()) {
			SimpleAccount account = new SimpleAccount(user, token.getToken(), getName());
	        account.addRole(user.getRole());
	        return account;
		}
		return null;
		*/
	}

	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return ((JwtPrincipalCollection) principals).getAuthorizationInfo();
    }

}
