package troubleticket;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * SQLConnection Singleton class to manage the connection to a MySQL
 * database instance depending on the environment indicated 
 * in environments.properties and the credentials to the desired database in the
 * dbconfig-*environment*.properties file
 * 
 * @author Israel Gonzalez <igonzals@nmsu.edu>
 *
 */
public class SqlConnection {

	private static SqlConnection instance = null;
	private String env = "";
	private String url = "";
	private String user = "";
	private String pass = "";
	private Connection connection = null;
	private FileInputStream dbconfigProperties;
	private Logger log = Logger.getLogger(SqlConnection.class.getName());

	/**
	 * Private constructor instantiates the connection according what properties file indicates and what environment Session instance indicates to use
	 */
	private SqlConnection() {
		try {
			env = Session.getInstance().getEnvironment();
			String propertiesLocation = "src/troubleticket/dbconfig-" + env + ".properties";
			dbconfigProperties = new FileInputStream(propertiesLocation);
			Properties dbProp = new Properties();
			dbProp.load(dbconfigProperties);
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				user = dbProp.getProperty("DB_USER");
				pass = dbProp.getProperty("DB_PASSWORD");
				url = "jdbc:mysql://" + dbProp.getProperty("DB_HOST") + ":" + dbProp.getProperty("DB_PORT") + "/"
						+ dbProp.getProperty("DB_NAME") + "?useSSL=false";
				try {
					this.connection = DriverManager.getConnection(url, user, pass);
				} catch (com.mysql.cj.jdbc.exceptions.CommunicationsException e) {
					log.debug(e);
				}
			} catch (ClassNotFoundException | SQLException e) {
				log.debug(e);
			}
		} catch (IOException e) {
			log.debug(e);
		}
	}

	/**
	 * Gets the single instance of this class
	 * @return MySQLConnection
	 */
	public static SqlConnection getInstance() {
		if (instance == null)
			instance = new SqlConnection();
		return instance;
	}

	/**
	 * Gets the java.sql.Connection
	 * @return Connection to the MySQL Database
	 */
	public Connection getConnection() {
		return instance.connection;
	}

	/**
	 * Closes the MySQL Database connection
	 */
	public void closeConnection() {
		if (instance.connection != null) {
			try {
				instance.connection.close();
			} catch (SQLException e) {
				log.debug(e);
			}
		}
	}
}
