package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import troubleticket.connections.AuroraConnection;

public class DashboardDAOAurora implements DashboardDAO {
	private Connection connection = AuroraConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDAOAurora.class.getName());
	private final String noOfOpened = """
			select
			count(ts.name) as noOfOpened
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 1
			and
			t.status_id = ts.id;
			""";
	private final String noOfAssigned = """
			select
			count(ts.name) as noOfAssigned
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 2
			and
			t.status_id = ts.id;
			""";
	private final String noOfResolved = """
			select
			count(ts.name) as noOfResolved
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 3
			and
			t.status_id = ts.id;
			""";
	private final String noOfClosed = """
			select
			count(ts.name) as noOfClosed
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 4
			and
			t.status_id = ts.id;
			""";
	private final String noOfDeleted = """
			select
			count(ts.name) as noOfDeleted
			from
			tickets as t,
			ticket_status as ts
			where
			status_id = 5
			and
			t.status_id = ts.id;
			""";
	private final String noOfEmergency = """
			select
			t.id 
			from 
			tickets as t, 
			ticket_priority as tp 
			where 
			t.priority_id = tp.id 
			and 
			tp.name = 'Emergency'  
			group by tp.name;
			""";
	private final String noOfHigh = """
			select
			t.id 
			from 
			tickets as t, 
			ticket_priority as tp 
			where 
			t.priority_id = tp.id 
			and 
			tp.name = 'High'  
			group by tp.name;
			""";
	private final String noOfLow = """
			select
			t.id 
			from 
			tickets as t, 
			ticket_priority as tp 
			where 
			t.priority_id = tp.id 
			and 
			tp.name = 'Low'  
			group by tp.name;
			""";
	
	private final String noOfNormal = """
			select
			t.id 
			from 
			tickets as t, 
			ticket_priority as tp 
			where 
			t.priority_id = tp.id 
			and 
			tp.name = 'Normal'  
			group by tp.name;
			""";
	
	private final String noOfHour = """
			select 
			t.id 
			from 
			tickets as t, 
			slas as s 
			where 
			t.sla_id = s.id 
			and 
			s.name = 'Within 1 hour' 
			group by s.name;
			""";
	
	private final String noOfDay = """
			select 
			t.id 
			from 
			tickets as t, 
			slas as s 
			where 
			t.sla_id = s.id 
			and 
			s.name = 'Within 1 day' 
			group by s.name;
			""";
	
	private final String noOfWeek = """
			select 
			t.id 
			from 
			tickets as t, 
			slas as s 
			where 
			t.sla_id = s.id 
			and 
			s.name = 'Within 1 week' 
			group by s.name;
			""";
	
	private final String noOfThirty = """
			select 
			t.id 
			from 
			tickets as t, 
			slas as s 
			where 
			t.sla_id = s.id 
			and 
			s.name = 'Within 30 days' 
			group by s.name;
			""";
	
	

	public int getNoOfOpened() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfOpened);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfOpened");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

	public int getNoOfAssigned() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfAssigned);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfAssigned");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

	public int getNoOfResolved() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfResolved);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfResolved");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

	public int getNoOfClosed() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfClosed);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfClosed");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}

	public int getNoOfDeleted() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfDeleted);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("noOfDeleted");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;

	}
	
	public int getNoOfEmergency() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfEmergency);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("t.id");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;
	}
	
	public int getNoOfHigh() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfHigh);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("t.id");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;
		
	}
	
	public int getNoOfLow() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfLow);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("t.id");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;
		
	}
	
	public int getNoOfNormal() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfNormal);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("t.id");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;
		
	}
	
	public int getNoOfHour() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfHour);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("t.id");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;
		
	}
	
	public int getNoOfDay() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfDay);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("t.id");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;
		
	}
	
	public int getNoOfWeek() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfWeek);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("t.id");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;
		
	}
	
	public int getNoOfThirty() {
		int num = 0;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(noOfThirty);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("t.id");
			}

			// add the rest
		} catch (SQLException e) {
			log.debug(e);
		}
		return num;
		
	}

}