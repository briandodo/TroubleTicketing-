package troubleticket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Session Singleton class stores the environment data needed across modules
 *
 * @author Israel Gonzalez <igonzals@nmsu.edu>
 */
public class Session {
	public static Session session = null;
	private Main app = null;
	private String environment = null;
	private FileInputStream environmentsProperties;
	private String initAs = null;

	/**
	 * Private constructor required by the Singleton pattern (empty)
	 */
	private Session() {}
	
	/**
	 * Gets the single instance of this class
	 * @return
	 */
	public static Session getInstance() {
		if (session == null)
			session = new Session();
		return session;
	}

	/**
	 * Gets the Main app
	 * @return Main app
	 */
	public Main getApp() {
		return app;
	}

	/**
	 * Sets a reference to the main app
	 * @param Main app
	 */
	public void setApp(Main app) {
		this.app = app;
	}

	/**
	 * Gets the Environments Properties
	 * @return EnvironmentsProperties File
	 * @throws FileNotFoundException
	 */
	public FileInputStream getEnvironmentsProperties() throws FileNotFoundException {
		return environmentsProperties;
	}

	/**
	 * Sets the Environments Properties
	 * @throws FileNotFoundException
	 */
	public void setEnvironmentsProperties() throws FileNotFoundException {
		this.environmentsProperties = new FileInputStream("src/troubleticket/environments.properties");
	}

	/**
	 * Gets the ENVIRONMENT property value
	 * @return environment to be used
	 * @throws IOException
	 */
	public String getEnvironment() throws IOException {
		setEnvironmentsProperties();
		Properties envProp = new Properties();
		envProp.load(environmentsProperties);
		environment = envProp.getProperty("ENVIRONMENT");
		return environment;
	}

	/**
	 * Sets the Environment to the Session
	 * @param environment set in the environment.properties file
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	/**
	 * Gets the INIT_AS property value (staff or user)
	 * @return initAs to be used
	 * @throws IOException
	 */
	public String getInitAs() throws IOException {
		setEnvironmentsProperties();
		Properties envProp = new Properties();
		envProp.load(environmentsProperties);
		initAs = envProp.getProperty("INIT_AS");
		return initAs;
	}

}
