package troubleticket.daos;
/**
 * Data Object Access Interface for Dashboard
 *
 */
public interface DashboardDaoI {
	public int getNoOfOpened();
	public int getNoOfAssigned();
	public int getNoOfResolved();
	public int getNoOfClosed();
	public int getNoOfDeleted();
}
