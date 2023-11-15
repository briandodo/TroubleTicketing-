package troubleticket.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Data Model Class of User
 *
 */
public class User {

	private int id;
	private String organization;
	private String name;
	private String fullname;
	private String email;
	private Timestamp created;
	private Timestamp updated;
	private String pattern = "MM/dd/yy hh:mm aa";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "US"));

	/**
	 * Gets id
	 * @return
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
	 * Gets organization
	 * @return organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Sets organization
	 * @param organization
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
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
	 * Gets fullname
	 * @return fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Sets fullname
	 * @param fullname
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
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
	 * @return
	 */
	public String getCreated() {
		return simpleDateFormat.format(created);
	}

	/**
	 * Gets created
	 * @param created
	 */
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	/**
	 * Gets updated
	 * @return updated formatted with locale format
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
