package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import troubleticket.SqlConnection;
import troubleticket.models.User;

/**
 * Data Object Access Class for User using SqlConnection
 */
public class UserDaoSql implements UserDaoI {

	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());

	private final String GET_BY_NAME = "select * from users u where u.name = ? limit 1";
	private final String GET_ALL = """
			select
			  u.id as id,
			  u.name,
			  o.name as org_name,
			  u.fullname as fullname,
			  u.email,
			  u.created as created,
			  u.updated as updated
			from
			  users as u,
			  organizations as o
			where
			  u.organization_id = o.id;
			""";

	/**
	 * Gets all users
	 */
	public ArrayList<User> getAll() {
		ArrayList<User> list = new ArrayList<User>();

		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET_ALL);

		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setOrganization(rs.getString("org_name"));
				user.setFullname(rs.getString("fullname"));
				user.setEmail(rs.getString("email"));
				user.setCreated(rs.getTimestamp("created"));
				user.setUpdated(rs.getTimestamp("updated"));
				list.add(user);
			}
		} catch (SQLException e) {
			log.debug(e);
		}
		return list;
	}

	/**
	 * Gets one user searching by name
	 * @param name
	 * @return User object
	 */
	public User getByName(String name) {
		User user = new User();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET_BY_NAME);
			stmt.setString(1, name);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			rs.next();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setOrganization(rs.getString("org_name"));
			user.setFullname(rs.getString("fullname"));
			user.setEmail(rs.getString("email"));
			user.setCreated(rs.getTimestamp("created"));
			user.setUpdated(rs.getTimestamp("updated"));
		} catch (SQLException e) {
			log.debug(e);
		}
		return user;
	}
}
