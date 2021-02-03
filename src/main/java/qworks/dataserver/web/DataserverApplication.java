package qworks.dataserver.web;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.shiro.web.jaxrs.ShiroFeature;

import qworks.dataserver.api.ApplicationDataApi;
import qworks.dataserver.api.LoginApi;
import qworks.dataserver.api.UsersApi;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
@ApplicationPath("api")
public class DataserverApplication extends Application {
	
	private Set<Object> singletons = null;
	private Set<Class<?>> classes = null;
	 
	
	/**
	 * Constructor 
	 */
	public DataserverApplication() {
		singletons = new HashSet<Object>();
		singletons.add(new LoginApi());
		singletons.add(new UsersApi());
		singletons.add(new ApplicationDataApi());
		classes = new HashSet<>();
        classes.add(ShiroFeature.class);
	}
	
    /**
     * @return
     * @see javax.ws.rs.core.Application#getSingletons()
     */
    @Override
    public Set<Object> getSingletons() {
    	return singletons;
    }
	
    /**
     * @return
     * @see javax.ws.rs.core.Application#getClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
 
}