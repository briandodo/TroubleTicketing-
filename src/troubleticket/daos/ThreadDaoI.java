package troubleticket.daos;

/**
 * Data Object Access Interface for Thread
 */
public interface ThreadDaoI {
	public Thread getByTicketId(int ticketId);
}
