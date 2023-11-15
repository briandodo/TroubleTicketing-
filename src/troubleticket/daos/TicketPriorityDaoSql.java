package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import troubleticket.SqlConnection;
import troubleticket.models.TicketPriority;

/**
 * Data Object Access Class for TicketPriority using SqlConnection
 *
 */
public class TicketPriorityDaoSql implements TicketPriorityDaoI {

	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());

	private final String GET_ALL = "select * from ticket_priority";
	private final String GET_BY_NAME = "select * from ticket_priority where name = ? limit 1";

	/**
	 * Gets all ticket priorities
	 * @return ArrayList<TicketPriority> list of ticket priorities
	 */
	public ArrayList<TicketPriority> getAll() {
		ArrayList<TicketPriority> answer = new ArrayList<TicketPriority>();
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
				TicketPriority obj = new TicketPriority();
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
	 * Gets a TicketPriority by name
	 * @param name ticket priority name
	 * @return
	 */
	public TicketPriority getByName(String name) {
		TicketPriority obj = new TicketPriority();
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
