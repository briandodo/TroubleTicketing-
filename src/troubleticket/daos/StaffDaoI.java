package troubleticket.daos;

import java.util.ArrayList;

import troubleticket.models.Staff;

/**
 * Data Object Access Interface for Staff
 */
public interface StaffDaoI {
	public boolean login(String username, String password);
	public ArrayList<Staff> getAll();
}
