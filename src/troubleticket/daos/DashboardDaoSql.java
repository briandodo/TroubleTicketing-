package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import troubleticket.SqlConnection;

/**
 * Data Object Access Class for Dashboard using SqlConnection
 *
 */
public class DashboardDaoSql implements DashboardDaoI {
	
	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());
	
	private final String noOfOpened = """
			select
			count(ts.name) as noOfOpened
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 1
			and
			t.status_id = ts.id;
			""";
	private final String noOfAssigned = """
			select
			count(ts.name) as noOfAssigned
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 2
			and
			t.status_id = ts.id;
			""";
	private final String noOfResolved = """
			select
			count(ts.name) as noOfResolved
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 3
			and
			t.status_id = ts.id;
			""";
	private final String noOfClosed = """
			select
			count(ts.name) as noOfClosed
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 4
			and
			t.status_id = ts.id;
			""";
	private final String noOfDeleted = """
			select
			count(ts.name) as noOfDeleted
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 5
			and
			t.status_id = ts.id;
			""";

	/**
	 * Gets number of opened tickets
	 */
	public int getNoOfOpened() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfOpened);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfOpened");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

	/**
	 * Gets number of assigned tickets
	 */
	public int getNoOfAssigned() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfAssigned);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfAssigned");
			}
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

	/**
	 * Gets number of resolved tickets
	 */
	public int getNoOfResolved() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfResolved);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfResolved");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

	/**
	 * Gets number of closed tickets
	 */
	public int getNoOfClosed() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfClosed);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfClosed");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

	/**
	 * Gets number of deleted tickets
	 */
	public int getNoOfDeleted() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfDeleted);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfDeleted");
			}
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

}