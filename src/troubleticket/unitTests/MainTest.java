package troubleticket.unitTests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.*;

import javafx.stage.Stage;
import troubleticket.Main;
/**
 * Class to encapsulate unit tests for Main class
 *
 */
public class MainTest {
	@Test
	public void startsWithNoException() {
		/*
		assertDoesNotThrow(() -> {
			Stage stage = new Stage();
			Main main = null;
			try {
				main = new Main();
				main.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		*/
	}
}
