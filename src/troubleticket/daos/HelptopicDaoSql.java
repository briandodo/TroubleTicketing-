package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import troubleticket.SqlConnection;
import troubleticket.models.Helptopic;

/**
 * Data Object Access Class for Helptopic using SqlConnection
 */
public class HelptopicDaoSql implements HelptopicDaoI {

	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());

	private final String GET_ALL = "select * from helptopic";
	private final String GET_BY_NAME = "select * from helptopic where name = ? limit 1";

	/**
	 * Gets all helptopics
	 */
	public ArrayList<Helptopic> getAll() {
		ArrayList<Helptopic> answer = new ArrayList<Helptopic>();
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
				Helptopic obj = new Helptopic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				answer.add(obj);
			}
		} catch (SQLException e) {
			log.debug(e);
		}
		return answer;
	}

	/**
	 * Gets one help topic searching by name
	 * @param name
	 * @return Helptopic object
	 */
	public Helptopic getByName(String name) {
		Helptopic obj = new Helptopic();
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
			obj.setId(rs.getInt("id"));
			obj.setName(rs.getString("name"));
		} catch (SQLException e) {
			log.debug(e);
		}
		return obj;
	}

}
