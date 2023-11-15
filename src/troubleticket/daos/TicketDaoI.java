package troubleticket.daos;

import java.sql.Timestamp;
import java.util.ArrayList;

import troubleticket.models.Ticket;

/**
 * Data Object Access Interface for Ticket
 */
public interface TicketDaoI {
	public Ticket get(int id);
	public int getTotal(String query);
	public ArrayList<Ticket> getAll(String where, String orderBy,String orderType);
	public boolean insert(String number, int userId, int staffId, int slaId, int statusId, int helptopicId, int priorityId, String subject, String body, Timestamp duedate);
	public boolean update(int id, String number, int userId, int staffId, int slaId, int statusId, int helptopicId, int priorityId, String subject, String body, Timestamp duedate);
	public boolean deleteAll(ArrayList<Integer> ticketIds);
}
