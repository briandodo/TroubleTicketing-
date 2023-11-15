package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

import troubleticket.SqlConnection;
import troubleticket.models.Ticket;
import troubleticket.models.TicketStatus;

/**
 * Data Object Access Class for Ticket using SqlConnection
 */
public class TicketDaoSql implements TicketDaoI {

	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());

	private final String GET = """
			select
			  ti.id,
			  ti.number,
			  ti.subject,
			  ti.user_id,
			  concat(usr.fullname, ' (', usr.name, ')') username,
			  st.name status,
			  concat(stf.fullname, ' (', stf.name, ')') agent_fullname,
			  top.name helptopic,
			  pri.name priority,
			  pri.color priority_color,
			  dep.name department_name,
			  sla.name sla,
			  ti.duedate,
			  ti.updated,
			  ti.created,
			  ti.closed,
			  ti.body,
			  th.id thread_id
			from tickets ti
			  left join users usr on usr.id = ti.user_id
			  left join ticket_status st on st.id = ti.status_id
			  left join staff stf on stf.id = ti.staff_id
			  left join helptopic top on top.id = ti.helptopic_id
			  left join ticket_priority pri on pri.id = ti.priority_id
			  left join slas sla on sla.id = ti.sla_id
			  left join departments dep on dep.id = stf.department_id and stf.id = ti.staff_id
			  left join threads th on ti.id = th.ticket_id
			where ti.id = ?
			""";
	private final String GET_TOTAL = """
			SELECT count(*) total FROM tickets ti
			where 1
			  and ti.number like ? or ti.subject like ?
			""";
	private final String GET_ID_BY_EMAIL_AND_NUMBER = """
			select
			  ti.id
			from
			  tickets ti
			  inner join users usr on usr.id = ti.user_id
			where
			  usr.email = ?	and ti.number = ?
			limit 1
			""";
	private final String DELETE_ALL = "delete from tickets where id in (%s)";
	private final String UPDATE_BY_STATUS = "update tickets set status_id = ? where id in (%s)";
	private final String UPDATE_CLOSED_DATE = "update tickets set closed = now() where id in (%s)";
	private final String INSERT = """
			INSERT INTO tickets
			(`number`,
			`user_id`,
			`staff_id`,
			`sla_id`,
			`status_id`,
			`helptopic_id`,
			`priority_id`,
			`subject`,
			`body`,
			`duedate`,
			`closed`,
			`created`,
			`updated`)
			VALUES
			(?,?,?,?,?,?,?,?,?,?,null, now(), now());
			""";
	private final String UPDATE = """
			UPDATE tickets
			SET
			`number` = ?,
			`user_id` = ?,
			`staff_id` = ?,
			`sla_id` = ?,
			`status_id` = ?,
			`helptopic_id` = ?,
			`priority_id` = ?,
			`subject` = ?,
			`body` = ?,
			`duedate` = ?,
			`updated` = now()
			WHERE `id` = ?;
			""";

	/**
	 * Gets a ticket searching by id
	 * @param id
	 */
	public Ticket get(int id) {
		Ticket ticket = new Ticket();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET);
			stmt.setLong(1, id);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			rs.next();
			ticket.setId(rs.getInt("id"));
			ticket.setNumber(rs.getLong("number"));
			ticket.setSubject(rs.getString("subject"));
			ticket.setBody(rs.getString("body"));
			ticket.setUsername(rs.getString("username"));
			ticket.setHelpTopic(rs.getString("helptopic"));
			ticket.setPriority(rs.getString("priority"));
			ticket.setPriorityColor(rs.getString("priority_color"));
			ticket.setStatus(rs.getString("status"));
			ticket.setThreadId(rs.getInt("thread_id"));
			ticket.setSla(rs.getString("sla"));
			ticket.setAgentFullname(rs.getString("agent_fullname"));
			ticket.setDepartmentName(rs.getString("department_name"));
			ticket.setDuedate(rs.getTimestamp("duedate"));
			ticket.setCreated(rs.getTimestamp("created"));
			ticket.setUpdated(rs.getTimestamp("updated"));
			ticket.setClosed(rs.getTimestamp("closed"));
			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return ticket;
	}

	/**
	 * Gets total of tickets filtering by a query
	 * @param query filter for where statement
	 */
	public int getTotal(String query) {
		PreparedStatement stmt = null;
		int total = 0;
		try {
			stmt = connection.prepareStatement(GET_TOTAL);
			stmt.setString(1, (query != null) ? "%" + query + "%" : "%%");
			stmt.setString(2, (query != null) ? "%" + query + "%" : "%%");
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			rs.next();
			total = rs.getInt("total");
		} catch (SQLException e) {
			log.debug(e);
		}
		return total;
	}

	/**
	 * Gets a list of Tickets filtering by a query, and sorting the list by orderBy and orderType
	 * @param query query
	 * @param orderBy
	 * @param orderType ASC or DESC
	 * @return List of Tickets
	 */
	public ArrayList<Ticket> getAll(String query, String orderBy, String orderType) {
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		PreparedStatement stmt = getSelectQuery(
				query, 
				orderBy, 
				orderType);
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getInt("id"));
				ticket.setNumber(rs.getLong("number"));
				ticket.setSubject(rs.getString("subject"));
				ticket.setUsername(rs.getString("username"));
				ticket.setHelpTopic(rs.getString("helptopic"));
				ticket.setPriority(rs.getString("priority"));
				ticket.setPriorityColor(rs.getString("priority_color"));
				ticket.setStatus(rs.getString("status"));
				ticket.setSla(rs.getString("sla"));
				ticket.setAgentFullname(rs.getString("agent_fullname"));
				ticket.setDepartmentName(rs.getString("department_name"));
				ticket.setDuedate(rs.getTimestamp("duedate"));
				ticket.setCreated(rs.getTimestamp("created"));
				ticket.setUpdated(rs.getTimestamp("updated"));
				ticket.setClosed(rs.getTimestamp("closed"));
				list.add(ticket);
			}
		} catch (SQLException e) {
			log.debug(e);
		}
		return list;
	}

	/**
	 * Makes a select query filtering and sorting
	 * @param query
	 * @param orderBy
	 * @param orderType ASC or DESC
	 * @return Statement
	 */
	private PreparedStatement getSelectQuery(String query, String orderBy, String orderType) {
		String selectQuery = """
				select
				  ti.id,
				  ti.number,
				  ti.subject,
				  ti.user_id,
				  concat(usr.fullname, ' (', usr.name, ')') username,
				  st.name status,
				  concat(stf.fullname, ' (', stf.name, ')') agent_fullname,
				  top.name helptopic,
				  pri.id priority_id,
				  pri.name priority,
				  pri.color priority_color,
				  dep.name department_name,
				  sla.name sla,
				  ti.duedate,
				  ti.created,
				  ti.updated,
				  ti.closed
				from tickets ti
				  left join users usr on usr.id = ti.user_id
				  left join ticket_status st on st.id = ti.status_id
				  left join staff stf on stf.id = ti.staff_id
				  left join helptopic top on top.id = ti.helptopic_id
				  left join ticket_priority pri on pri.id = ti.priority_id
				  left join slas sla on sla.id = ti.sla_id
				  left join departments dep on dep.id = stf.department_id and stf.id = ti.staff_id
				where 1
				  and ti.number like ? or ti.subject like ?
				%s
				""";
		PreparedStatement stmt = null;
		try {
			final int WHERE_1_INDEX = 1;
			final int WHERE_2_INDEX = 2;
			if (orderBy == null) {
				selectQuery = String.format(selectQuery, "");
			} else {
				String orderSubQuery = "";
				switch (orderBy) {
				case "Priority + Most reacently updated":
					orderSubQuery = "order by priority_id desc, updated desc";
					break;
				case "Priority + Most reacently created":
					orderSubQuery = "order by priority_id desc, created desc";
					break;
				case "Due Date":
					orderSubQuery = "order by duedate desc";
					break;
				case "Create Date":
					orderSubQuery = "order by created desc";
					break;
				case "Update Date":
					orderSubQuery = "order by updated desc";
					break;
				case "Number":
					orderSubQuery = "order by number asc";
					break;
				default:
					orderSubQuery = "";
					break;
				}
				selectQuery = String.format(selectQuery, orderSubQuery);
			}
			stmt = connection.prepareStatement(selectQuery);
			stmt.setString(WHERE_1_INDEX, (query != null) ? "%" + query + "%" : "%%");
			stmt.setString(WHERE_2_INDEX, (query != null) ? "%" + query + "%" : "%%");
		} catch (SQLException e) {
			log.debug(e);
		}
		return stmt;
	}
	
	/**
	 * Inserts a Ticket
	 */
	public boolean insert(String number, int userId, int staffId, int slaId, int statusId, int helptopicId, int priorityId, String subject, String body, Timestamp duedate) {
		boolean inserted = false;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(INSERT);
			stmt.setString(1, number);
			stmt.setInt(2, userId);
			stmt.setInt(3, staffId);
			stmt.setInt(4, slaId);
			stmt.setInt(5, statusId);
			stmt.setInt(6, helptopicId);
			stmt.setInt(7, priorityId);
			stmt.setString(8, subject);
			stmt.setString(9, body);
			stmt.setTimestamp(10, duedate);
		} catch (SQLException e) {
			log.debug(e);
		} 
		try {
			int row = stmt.executeUpdate();
			if (row > 0)
				inserted = true;
		} catch (SQLException e) {
			log.debug(e);
		}
		return inserted;
	}

	/**
	 * Updates a ticket
	 */
	public boolean update(int id, String number, int userId, int staffId, int slaId, int statusId, int helptopicId, int priorityId, String subject, String body, Timestamp duedate) {
		boolean updated = false;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(UPDATE);
			stmt.setString(1, number);
			stmt.setInt(2, userId);
			stmt.setInt(3, staffId);
			stmt.setInt(4, slaId);
			stmt.setInt(5, statusId);
			stmt.setInt(6, helptopicId);
			stmt.setInt(7, priorityId);
			stmt.setString(8, subject);
			stmt.setString(9, body);
			stmt.setTimestamp(10, duedate);
			stmt.setInt(11, id);
		} catch (SQLException e) {
			log.debug(e);
		} 
		try {
			int row = stmt.executeUpdate();
			if (row > 0)
				updated = true;
		} catch (SQLException e) {
			log.debug(e);
		}
		return updated;
	}

	/**
	 * Deletes a list of tickets by a list of ticket ids
	 */
	public boolean deleteAll(ArrayList<Integer> ids) {
		PreparedStatement stmt = null;
		try {
			String sql = String.format(DELETE_ALL, ids.stream().map(String::valueOf).collect(Collectors.joining(", ")));
			stmt = connection.prepareStatement(sql);
			stmt.execute();
			return true;
		} catch (Exception e) {
			log.debug(e);
			return false;
		}
	}

	/**
	 * Updates the ticket status of a list of tickets
	 */
	public boolean updateByStatus(ArrayList<Integer> ticketIds, String statusName) {
		
		if(ticketIds == null || ticketIds.size() == 0 || statusName == null)
			return false;
		
		TicketStatusDaoSql statusDAO = new TicketStatusDaoSql();
		TicketStatus ticketStatus = statusDAO.getByName(statusName);
		PreparedStatement stmt = null;
		
		try {
			String sql = String.format(UPDATE_BY_STATUS,
					ticketIds.stream().map(String::valueOf).collect(Collectors.joining(", ")));
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, ticketStatus.getId());
			stmt.execute();
			
			//If status name is closed, we update its closed date
			if(statusName.equals("Closed")) {
				sql = String.format(UPDATE_CLOSED_DATE,
						ticketIds.stream().map(String::valueOf).collect(Collectors.joining(", ")));
				stmt = connection.prepareStatement(sql);
				stmt.execute();
			}
			return true;
		} catch (Exception e) {
			log.debug(e);
			return false;
		}
	}

	/**
	 * Gets a ticket id given a email of a user and a ticket number
	 */
	public int getIdByEmailAndNumber(String userEmail, String ticketNumber) {
		int idAnswer = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET_ID_BY_EMAIL_AND_NUMBER);
			stmt.setString(1, userEmail);
			stmt.setString(2, ticketNumber);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			rs.next();
			idAnswer = rs.getInt("id");
		} catch (SQLException e) {
			log.debug(e);
		}
		return idAnswer;
	}
}
