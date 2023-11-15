package troubleticket.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import troubleticket.SqlConnection;
import troubleticket.models.Staff;

/**
 * Data Object Access Class for Staff using SqlConnection
 */
public class StaffDaoSql implements StaffDaoI {

	private Connection connection = SqlConnection.getInstance().getConnection();
	private Logger log = Logger.getLogger(TicketDaoSql.class.getName());

	private final String LOGIN = "select id, name, password from staff where name = ? and password = ? limit 1";
	private final String GET = """
			select
			  st.id,
			  rol.name rol_name,
			  dep.name department_name,
			  st.name,
			  st.fullname,
			  st.email,
			  st.password,
			  st.created,
			  st.updated
			from
			  staff st
			  inner join roles rol on st.role_id = rol.id
			  inner join departments dep on st.department_id = dep.id
			where
			  st.name = ? and st.password = ?
			limit 1";
			""";
	private final String GET_ALL = """
			select
			  st.id,
			  rol.name rol_name,
			  dep.name department_name,
			  st.name,
			  st.fullname,
			  st.email,
			  st.password,
			  st.created,
			  st.updated
			from
			  staff st
			  inner join roles rol on st.role_id = rol.id
			  inner join departments dep on st.department_id = dep.id
			order by st.id asc
			""";
	private final String GET_BY_NAME = "select * from staff where name = ? limit 1";

	/**
	 * Signs in a staff member by contrasting username and password
	 * @param username
	 * @param password
	 */
	public boolean login(String username, String password) {
		boolean logedIn = false;
		Staff staff = new Staff();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(LOGIN);
			stmt.setString(1, username);
			stmt.setString(2, password);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			rs.next();
			staff.setId(rs.getInt("id"));
			staff.setName(rs.getString("name"));
			;
			staff.setPassword(rs.getString("password"));
			if (password.equals(staff.getPassword()))
				logedIn = true;
		} catch (SQLException e) {
			log.debug(e);
		}
		return logedIn;
	}

	/**
	 * Gets one staff object by seaching by staff id
	 * @param staffId
	 * @return Staff object
	 */
	public Staff get(int staffId) {
		Staff staff = new Staff();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET);
			stmt.setInt(1, staffId);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			rs.next();
			staff.setId(rs.getInt("id"));
			staff.setRole(rs.getString("rol_name"));
			staff.setDepartment(rs.getString("department_name"));
			staff.setName(rs.getString("name"));
			staff.setFullname(rs.getString("fullname"));
			staff.setPassword(rs.getString("password"));
			staff.setEmail(rs.getString("email"));
			staff.setCreated(rs.getTimestamp("created"));
			staff.setUpdated(rs.getTimestamp("updated"));
		} catch (SQLException e) {
			log.debug(e);
		}
		return staff;
	}

	/**
	 * Gets all staff members
	 */
	public ArrayList<Staff> getAll() {
		ArrayList<Staff> listForStaff = new ArrayList<Staff>();

		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET_ALL);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				Staff staffForAll = new Staff();
				staffForAll.setId(rs.getInt("id"));
				staffForAll.setRole(rs.getString("rol_name"));
				staffForAll.setDepartment(rs.getString("department_name"));
				staffForAll.setName(rs.getString("name"));
				staffForAll.setFullname(rs.getString("fullname"));
				staffForAll.setEmail(rs.getString("email"));
				staffForAll.setCreated(rs.getTimestamp("created"));
				staffForAll.setUpdated(rs.getTimestamp("updated"));
				listForStaff.add(staffForAll);
			}
		} catch (SQLException e) {
			log.debug(e);
		}
		return listForStaff;
	}

	/**
	 * Gets one staff member searching by name
	 * @param name
	 * @return Staff object
	 */
	public Staff getByName(String name) {
		Staff obj = new Staff();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(GET_BY_NAME);
			stmt.setString(1, name);
		} catch (SQLException e) {
			log.debug(e);
		}
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			rs.next();
			obj.setId(rs.getInt("id"));
			obj.setName(rs.getString("name"));
		} catch (SQLException e) {
			log.debug(e);
		}
		return obj;
	}
}
