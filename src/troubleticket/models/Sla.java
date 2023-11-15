package troubleticket.models;

/**
 * Data Model Class of Sla
 *
 */
public class Sla {
	private int id;
	private String name;

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
	 * Gets name
	 * @return
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
}
