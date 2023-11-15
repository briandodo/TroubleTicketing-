package troubleticket.models;

import java.util.ArrayList;

/**
 * Data Model Class of Thread
 *
 */
public class Thread {

	private int id;
	private String type;
	private ArrayList<ThreadEntry> entries = new ArrayList<ThreadEntry>();

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

	/**
	 * Get entries
	 * @return entries
	 */
	public ArrayList<ThreadEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets entries
	 * @param entries
	 */
	public void setEntries(ArrayList<ThreadEntry> entries) {
		this.entries = entries;
	}

}
