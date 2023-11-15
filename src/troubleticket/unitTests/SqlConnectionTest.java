package troubleticket.unitTests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.*;

import troubleticket.SqlConnection;
/**
 * Class to encapsulate unit tests for SqlConnection class
 *
 */
public class SqlConnectionTest {

	@Test
	public void connectionNoException() {
		SqlConnection.getInstance();
		assertDoesNotThrow(() -> SqlConnection.getInstance().getConnection());
	}

	@Test
	public void connectionIsNotNull() {
		SqlConnection.getInstance();
		assertNotEquals(null, SqlConnection.getInstance().getConnection());
	}
}
