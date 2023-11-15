package troubleticket.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Data Model Class of ThreadEntry
 *
 */
public class ThreadEntry {

	private int id;
	private String title;
	private String body;
	private Timestamp updated;
	private String type;

	String pattern = "MM/dd/yyyy hh:mm aa";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "US"));

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
	 * Gets title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets body
	 * @return body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets body
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Gets updated
	 * @return updated formated with locale format
	 */
	public String getLastupdate() {
		return simpleDateFormat.format(updated);
	}

	/**
	 * Sets update
	 * @param updated
	 */
	public void setLastupdate(Timestamp updated) {
		this.updated = updated;
	}

	/**
	 * Gets type
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
