package qworks.dataserver.web.security.jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import qworks.dataserver.model.User;


/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class JwtAuthenticationToken implements AuthenticationToken {

    private static final long serialVersionUID = -5967183712237455887L;
	
    private final User principal;
    private final String token;
    private final Set<String> roles;
    private final Set<String> permissions;


    /**
     * @param principal
     * @param token
     */
    public JwtAuthenticationToken(User principal, String token) {
        this.principal = principal;
        this.token = token;
        this.roles = null;
        this.permissions = null;
    }
    
    /**
     * @param principal
     * @param token
     * @param roles
     * @param permissions
     */
    public JwtAuthenticationToken(User principal, String token, Collection<String> roles, Collection<String> permissions) {
        this.principal = principal;
        this.token = token;
        this.roles = Collections.unmodifiableSet(new HashSet<>(roles));
        this.permissions = Collections.unmodifiableSet(new HashSet<>(permissions));
    }

    /**
     * @return
     * @see org.apache.shiro.authc.AuthenticationToken#getPrincipal()
     */
    @Override
    public Object getPrincipal() {
        return principal;
    }

    /**
     * @return
     * @see org.apache.shiro.authc.AuthenticationToken#getCredentials()
     */
    @Override
    public Object getCredentials() {
        return token;
    }
    
    
    /**
     * @param realmName
     * @return
     */
    public AuthenticationInfo getJwtAuthenticationInfo(String realmName) {
        return new JwtAuthenticationInfo(realmName);
    }



	/**
	 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
	 *
	 */
	public class JwtAuthenticationInfo implements AuthenticationInfo {
		
		private static final long serialVersionUID = 1144645612257597199L;
		private final PrincipalCollection principals;

        /**
         * @param realmName
         */
        public JwtAuthenticationInfo(String realmName) {
            this.principals = new JwtPrincipalCollection(realmName);
        }

        /**
         * @return
         * @see org.apache.shiro.authc.AuthenticationInfo#getPrincipals()
         */
        @Override
        public PrincipalCollection getPrincipals() {
            return principals;
        }

        /**
         * @return
         * @see org.apache.shiro.authc.AuthenticationInfo#getCredentials()
         */
        @Override
        public Object getCredentials() {
            return token;
        }
    }


	
	/** 
	 */
	public class JwtPrincipalCollection extends SimplePrincipalCollection {

   		private static final long serialVersionUID = -2701692613935507684L;

   		/**
   		 * @param realmName
   		 */
   		public JwtPrincipalCollection(String realmName) {
            super(principal, realmName);
        }

		/**
		 * @return
		 */
		public AuthorizationInfo getAuthorizationInfo() {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRoles(roles);
            info.addStringPermissions(permissions);
            return info;
        }
    }
}