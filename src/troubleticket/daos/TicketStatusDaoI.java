package troubleticket.daos;

import java.util.ArrayList;
import troubleticket.models.TicketStatus;

/**
 * Data Object Access Interface for TicketStatus
 */
public interface TicketStatusDaoI {
	public ArrayList<TicketStatus> getAll();
}
