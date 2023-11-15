package troubleticket.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Data Model Class of Ticket
 *
 */
public class Ticket {

	private int id;
	private long number;
	private String subject;
	private String body;
	private String username;
	private String helpTopic;
	private String priority;
	private String priorityColor;
	private String status;
	private String sla;
	private String agentFullname;
	private Integer threadId;
	private String departmentName;
	private Timestamp duedate;
	private String duedateFormatted;
	private Timestamp created;
	private String createdFormatted;
	private Timestamp updated;
	private String updatedFormatted;
	private Timestamp closed;
	private String closedFormatted;

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
	 * @param ticketId
	 */
	public void setId(int ticketId) {
		this.id = ticketId;
	}

	/**
	 * Gets number
	 * @return number
	 */
	public long getNumber() {
		return number;
	}

	/**
	 * Sets number
	 * @param number
	 */
	public void setNumber(long number) {
		this.number = number;
	}

	/**
	 * Gets subject
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets subject
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets help topic
	 * @return helptopic
	 */
	public String getHelpTopic() {
		return helpTopic;
	}

	/**
	 * Sets helptopic
	 * @param helpTopic
	 */
	public void setHelpTopic(String helpTopic) {
		this.helpTopic = helpTopic;
	}

	/**
	 * Gets priority
	 * @return priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets priority
	 * @param priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Gets priority color
	 * @return priority color
	 */
	public String getPriorityColor() {
		return priorityColor;
	}

	/**
	 * Sets priority color
	 * @param priorityColor
	 */
	public void setPriorityColor(String priorityColor) {
		this.priorityColor = priorityColor;
	}

	/**
	 * Gets staff (agent) full name
	 * @return agent fullname
	 */
	public String getAgentFullname() {
		return agentFullname;
	}

	/**
	 * Sets agent full name
	 * @param agentFullname
	 */
	public void setAgentFullname(String agentFullname) {
		this.agentFullname = agentFullname;
	}

	/**
	 * Gets department name
	 * @return department name
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * Sets department name
	 * @param departmentName
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * Gets status
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets status
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets sla
	 * @return
	 */
	public String getSla() {
		return this.sla;
	}

	/**
	 * Sets sla
	 * @param sla
	 */
	public void setSla(String sla) {
		this.sla = sla;
	}

	/**
	 * Sets duedate
	 * @param duedate
	 */
	public void setDuedate(Timestamp duedate) {
		this.duedate = duedate;
		this.duedateFormatted = (duedate == null) ? "" : simpleDateFormat.format(duedate);
	}
	
	/**
	 * Gets duedate
	 * @return duedate
	 */
	public Timestamp getDuedate() {
		return duedate;
	}

	/**
	 * Gets duedate
	 * @return duedate formatted with locale format
	 */
	public String getDuedateFormatted() {
		return duedateFormatted;
	}
	
	/**
	 * Sets created
	 * @param created
	 */
	public void setCreated(Timestamp created) {
		this.created = created;
		this.createdFormatted = (created == null) ? "" : simpleDateFormat.format(created);
	}
	
	/**
	 * Gets created
	 * @return created
	 */
	public Timestamp getCreated() {
		return created;
	}
	
	/**
	 * Gets created
	 * @return created with locale format
	 */
	public String getCreatedFormatted() {
		return createdFormatted;
	}
	
	/**
	 * Sets updated
	 * @param updated
	 */
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
		this.updatedFormatted = (updated == null) ? "" : simpleDateFormat.format(updated);
	}
	
	/**
	 * Gets updated
	 * @return updated
	 */
	public Timestamp getUpdated() {
		return updated;
	}
	
	/**
	 * Gets updated
	 * @return updated with locale format
	 */
	public String getUpdatedFormatted() {
		return updatedFormatted;
	}
	
	/**
	 * Sets closed
	 * @param closed
	 */
	public void setClosed(Timestamp closed) {
		this.closed = closed;
		this.closedFormatted = (closed == null) ? "" : simpleDateFormat.format(closed);
	}

	/**
	 * Gets closed
	 * @return closed
	 */
	public Timestamp getClosed() {
		return closed;
	}
	
	/**
	 * Gets closed
	 * @return closed formatted with locale format
	 */
	public String getClosedFormatted() {
		return closedFormatted;
	}

	/**
	 * Gets body
	 * @return body
	 */
	public String getBody() {
		return (body == null) ? "" : body;
	}

	/**
	 * Sets body
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	/**
	 * Gets thread id
	 * @return threadId
	 */
	public Integer getThreadId() {
		return threadId;
	}

	/**
	 * Sets thread id
	 * @param threadId
	 */
	public void setThreadId(Integer threadId) {
		this.threadId = threadId;
	}
}
