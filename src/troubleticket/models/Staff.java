package troubleticket.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Data Model Class of Staff
 *
 */
public class Staff {

	private int id;
	private String role;
	private String department;
	private String name;
	private String fullname;
	private String password;
	private String email;
	private Timestamp created;
	private Timestamp updated;
	private String pattern = "MM/dd/yy hh:mm aa";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "US"));

	/**
	 * Gets id
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets role
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets role
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets department
	 * @return department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Sets department
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * Gets name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets Fullname
	 * @return fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Sets Fullname
	 * @param fullname
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Gets password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets created
	 * @return created
	 */
	public String getCreated() {
		return simpleDateFormat.format(created);
	}

	/**
	 * Sets created
	 * @param created
	 */
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	/**
	 * Gets updated
	 * @return updated formated as string
	 */
	public String getUpdated() {
		return simpleDateFormat.format(updated);
	}

	/**
	 * Sets updated
	 * @param updated
	 */
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
}
