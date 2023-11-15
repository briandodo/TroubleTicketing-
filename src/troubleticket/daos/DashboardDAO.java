package troubleticket.daos;

public interface DashboardDAO {
	public int getNoOfOpened();

	public int getNoOfAssigned();

	public int getNoOfResolved();

	public int getNoOfClosed();

	public int getNoOfDeleted();
	
	public int getNoOfEmergency();
	
	public int getNoOfHigh();
	
	public int getNoOfLow();
	
	public int getNoOfNormal();
	
	public int getNoOfHour();
	
	public int getNoOfDay();
	
	public int getNoOfWeek();
	
	public int getNoOfThirty();

}
