package troubleticket.daos;

import java.util.ArrayList;

import troubleticket.models.User;

/**
 * Data Object Access Interface for User
 */
public interface UserDaoI {
	public ArrayList<User> getAll();

}
