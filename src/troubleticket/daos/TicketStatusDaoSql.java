package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import troubleticket.SqlConnection;
import troubleticket.models.TicketStatus;

/**
 * Data Object Access Class for TicketStatus using SqlConnection
 */
public class TicketStatusDaoSql implements TicketStatusDaoI {

	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());

	private final String GET_ALL = "select * from ticket_status";
	private final String GET_BY_NAME = "select * from ticket_status where name = ? limit 1";

	/**
	 * Gets all TicketStaus
	 */
	public ArrayList<TicketStatus> getAll() {
		ArrayList<TicketStatus> answer = new ArrayList<TicketStatus>();
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
				TicketStatus obj = new TicketStatus();
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
	 * Gets one ticket status searching by name
	 * @param name
	 * @return TicketStatus object
	 */
	public TicketStatus getByName(String name) {
		TicketStatus obj = new TicketStatus();
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
