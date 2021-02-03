package qworks.dataserver.web;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qworks.dataserver.aws.AWSCognitoService;
import qworks.dataserver.dao.mongo.MongoManager;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
@WebListener
public class AppInitializer implements ServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
	
	private static final String AWS_PROPERTIES_FILE = "aws.properties";
	private static final String MONGO_PROPERTIES_FILE = "mongo.properties";
	
	
    /**
     * Start those services that take some time to initialize.
     * Doing it into a spare thread reduces the impact on the server statup.
     * @param sce
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	new Thread(new Runnable() {
    		
    		@Override
			public void run() {
				
    			LOG.debug("Initialing AWS SDK");
				Properties awsProps = loadProperties(AWS_PROPERTIES_FILE);
				if (awsProps != null) {
					String poolId = awsProps.getProperty(AWSCognitoService.AWS_COGNITO_USERPOOLID);
					String clientId = awsProps.getProperty(AWSCognitoService.AWS_COGNITO_POOLCLIENTID);
					AWSCognitoService.init(poolId, clientId);
				}
				
				LOG.debug("Initialing MongodDB");
				MongoManager.getConfig(MONGO_PROPERTIES_FILE);
				MongoManager.getInstance().getClient();
				MongoManager.getInstance().getDatastore();
				
				LOG.info("Initialization Finished!");
			}
			
			/** Read a properties file */
			private Properties loadProperties(String filename) {
				InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
				try {
					if ((is) != null) {
						Properties props = new Properties();
						props.load(is);
						return props;
					}
				} catch (IOException e) {
					LOG.error("Error reading file {}: {}", filename, e.getMessage());
				}
				return null;
			}
    		
    	}).start();
    	
    }

    /**
     * @param sce
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}