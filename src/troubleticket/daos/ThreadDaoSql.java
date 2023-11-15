package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import troubleticket.SqlConnection;
import troubleticket.models.Thread;
import troubleticket.models.ThreadEntry;

/**
 * Data Object Access Class for Thread using SqlConnection
 */
public class ThreadDaoSql {

	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());
	
	private final String GET_BY_TICKET_ID = """
			select
			  ti.id ticket_id,
			  th.id thread_id,
			  th.type thread_type,
			  en.title entry_title,
			  en.body entry_body,
			  en.created entry_created,
			  en.updated entry_updated,
			  en.type entry_type
			from tickets ti
			  inner join threads th on ti.id = th.ticket_id and ti.id = ?
			  inner join thread_entries en on th.id = en.thread_id
			  order by thread_id, entry_updated DESC
			""";
	private final String GET_THREAD_ID = """
			select
			  th.id 
			from tickets ti
			  inner join threads th on ti.id = th.ticket_id
			where
			  ti.id = ? and th.type = ?
			limit 1;
			""";
	private final String INSERT_THREAD = """
			INSERT INTO threads
			(`ticket_id`,
			`type`,
			`lastresponse`,
			`created`,
			`updated`)
			VALUES
			(?,?,now(), now(), now());
			""";
	private final String UPDATE_THREAD = """
			UPDATE threads
			SET
			`ticket_id` = ?,
			`type` = ?,
			`lastresponse` = now(),
			`updated` = now()
			WHERE `id` = ?;
			""";
	private final String INSERT_ENTRY = """
			INSERT INTO thread_entries
			(`thread_id`,
			`title`,
			`body`,
			`type`,
			`created`,
			`updated`)
			VALUES
			(?,?,?,?,now(), now());
			""";
	/**
	 * Gets a Thread searching by ticket id
	 * @param ticketId
	 * @return Thread object
	 */
	public Thread getByTicketId(int ticketId) {
		Thread thread = new Thread();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET_BY_TICKET_ID);
			stmt.setInt(1, ticketId);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			ArrayList<ThreadEntry> entries = new ArrayList<ThreadEntry>();
			while (rs.next()) {
				thread.setId(rs.getInt("thread_id"));
				thread.setType(rs.getString("thread_type"));
				ThreadEntry entry = new ThreadEntry();
				entry.setTitle(rs.getString("entry_title"));
				entry.setBody(rs.getString("entry_body"));
				entry.setLastupdate(rs.getTimestamp("entry_updated"));
				entry.setType(rs.getString("entry_type"));
				entries.add(entry);
			}
			thread.setEntries(entries);
		} catch (SQLException e) {
			log.debug(e);
		}
		return thread;
	}
	
	/**
	 * Inserts a Thread Entry
	 * @param ticketId
	 * @param threadId
	 * @param threadType
	 * @param entryTitle
	 * @param entryBody
	 * @return success if insertion was correct
	 */
	public boolean insertEntry(Integer ticketId, Integer threadId, String threadType, String entryTitle, String entryBody) {
		if(ticketId <= 0)
			return false;
		boolean inserted = false;
		PreparedStatement stmt = null;
		try {
			if(threadId <= 0) {
				stmt = connection.prepareStatement(INSERT_THREAD);
				stmt.setInt(1, ticketId);
				stmt.setString(2, threadType);
			} else {
				stmt = connection.prepareStatement(UPDATE_THREAD);
				stmt.setInt(1, ticketId);
				stmt.setString(2, threadType);
				stmt.setInt(3, threadId);
			}
			try {
				int row = stmt.executeUpdate();
				if (row > 0)
					inserted = true;
				threadId = getThreadIdByTicketIdAndType(ticketId, threadType);
				if(threadId <= 0) {
					inserted = false;
				} else {
					PreparedStatement stmt2 = connection.prepareStatement(INSERT_ENTRY);
					stmt2.setInt(1, threadId);
					stmt2.setString(2, entryTitle);
					stmt2.setString(3, entryBody);
					stmt2.setString(4, threadType);
					
					int row2 = stmt2.executeUpdate();
					if (row2 > 0)
						inserted = true;
					else
						inserted = false;
				}
			} catch (SQLException e) {
				log.debug(e);
			}
		} catch (SQLException e) {
			log.debug(e);
		}
		return inserted;
	}
	
	/**
	 * Gets a Thread ID seaching by ticket id and thread type
	 * @param ticketId
	 * @param threadType
	 * @return thread id
	 */
	public int getThreadIdByTicketIdAndType(int ticketId, String threadType) {
		int threadId = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET_THREAD_ID);
			stmt.setInt(1, ticketId);
			stmt.setString(2, threadType);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			rs.next();
			threadId = rs.getInt("id");
		} catch (SQLException e) {
			log.debug(e);
		}
		return threadId;
	}
}
