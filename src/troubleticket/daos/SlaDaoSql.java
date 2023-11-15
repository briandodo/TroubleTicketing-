package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import troubleticket.SqlConnection;
import troubleticket.models.Sla;

/**
 * Data Object Access Class for Sla using SqlConnection
 */
public class SlaDaoSql implements SlaDaoI {

	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());

	private final String GET_ALL = "select * from slas";
	private final String GET_BY_NAME = "select * from slas where name = ? limit 1";

	/**
	 * Gets all slas
	 */
	public ArrayList<Sla> getAll() {
		ArrayList<Sla> answer = new ArrayList<Sla>();
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
				Sla obj = new Sla();
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
	 * Gets one sla searching by name
	 * @param name
	 * @return Sla object
	 */
	public Sla getByName(String name) {
		Sla obj = new Sla();
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
