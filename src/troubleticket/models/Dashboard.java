package troubleticket.models;

/**
 * Data Model Class of Dashboard
 */
public class Dashboard {
	private int noOfOpened;
	private int noOfAssigned;
	private int noOfResolved;
	private int noOfClosed;
	private int noOfDeleted;

	public int getNoOfOpened() {
		return noOfOpened;
	}

	public void setNoOfOpened(int noOfOpened) {
		this.noOfOpened = noOfOpened;
	}

	public int getNoOfAssigned() {
		return noOfAssigned;
	}

	public void setNoOfAssigned(int noOfAssigned) {
		this.noOfAssigned = noOfAssigned;
	}

	public int getNoOfResolved() {
		return noOfResolved;
	}

	public void setNoOfResolvedd(int noOfResolved) {
		this.noOfResolved = noOfResolved;
	}

	public int getNoOfClosed() {
		return noOfClosed;
	}

	public void setNoOfClosed(int noOfClosed) {
		this.noOfClosed = noOfClosed;
	}

	public int getNoOfDeleted() {
		return noOfDeleted;
	}

	public void setNoOfDeleted(int noOfDeleted) {
		this.noOfDeleted = noOfDeleted;
	}

}
