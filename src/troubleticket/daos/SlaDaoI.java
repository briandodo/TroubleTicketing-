package troubleticket.daos;

import java.util.ArrayList;
import troubleticket.models.*;

/**
 * Data Object Access Interface for Sla
 */
public interface SlaDaoI {
	public ArrayList<Sla> getAll();
}
