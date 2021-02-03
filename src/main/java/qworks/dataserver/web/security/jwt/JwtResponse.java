package qworks.dataserver.web.security.jwt;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class JwtResponse {

    private String token;

    /**
     * @param token
     */
    public JwtResponse(String token) {
        this.token = token;
    }
    
    /**
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

}