package troubleticket.daos;

import java.util.ArrayList;
import troubleticket.models.*;

/**
 * Data Object Access Interface for TicketPriority
 */
public interface TicketPriorityDaoI {
	public ArrayList<TicketPriority> getAll();
}
