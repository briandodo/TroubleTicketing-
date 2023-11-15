package troubleticket.modules;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import troubleticket.Session;
import troubleticket.daos.StaffDaoSql;

/**
 * GUI Module StaffAuthenticationModule manages the login process for staff members when the app is on "staff" mode in the enviroment properties
 */
public class StaffAuthenticationModule implements AuthenticationModuleI {

	private Session session = Session.getInstance();
	private StaffDaoSql staffDAO = new StaffDaoSql();
	private GridPane form = null;
	private TextField usernameTextField = new TextField();
	private PasswordField passwordPasswordField = new PasswordField();
	private Text feedbackText = new Text();
	private Button signInBtn = new Button("Sign in");
	private Validator validator = new Validator();

	public void start(Stage stage) {
		stage.setTitle("Trouble Ticketing | Staff Login");
		paintForm();
		Scene scene = new Scene(form, 450, 300);
		stage.setScene(scene);
		stage.show();
	}

	private void paintForm() {

		if (form == null) {
			form = new GridPane();
			form.setAlignment(Pos.CENTER);
			form.setHgap(7);
			form.setVgap(7);
			form.setPadding(new Insets(70, 70, 70, 70));
		}

		Text formTitle = new Text("Welcome to Trouble Ticket");
		formTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		form.add(formTitle, 0, 0, 2, 1);

		Label usernameLabel = new Label("Username:");
		usernameTextField.setMaxWidth(200);
		form.add(usernameLabel, 0, 1);
		form.add(usernameTextField, 1, 1);

		Label passwordLabel = new Label("Password:");
		passwordPasswordField.setMaxWidth(200);
		form.add(passwordLabel, 0, 3);
		form.add(passwordPasswordField, 1, 3);

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(signInBtn);

		form.add(hbBtn, 1, 4);
		form.add(feedbackText, 1, 5);

		signInBtn.setOnAction(event -> submitForm());
		usernameTextField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				submitForm();
		});
		passwordPasswordField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				submitForm();
		});
	}

	private void submitForm() {
		
		String username = usernameTextField.getText().toString().trim();
		String password = passwordPasswordField.getText().toString().trim();
		
		validator.createCheck().dependsOn("notEmptyUsernamer", usernameTextField.textProperty()).withMethod(c -> {
			if (usernameTextField.getText().trim() == "") {
				c.error("Username cannot be empty");
			}
		}).decorates(usernameTextField).immediate();

		validator.createCheck().dependsOn("notEmptyPassword", passwordPasswordField.textProperty()).withMethod(c -> {
			if (passwordPasswordField.getText().trim() == "") {
				c.error("Password cannot be empty");
			}
		}).decorates(passwordPasswordField).immediate();
		
		if (validator.validate()) {
			
			boolean login = staffDAO.login(username, password);
			
			signInBtn.setDisable(true);
			feedbackText.setText("");
			
			if (login) {
				session.getApp().paintModules();
			} else {
				signInBtn.setDisable(false);
				feedbackText.setFill(Color.RED);
				feedbackText.setText("Incorrect username or password." 
									+ "\nPlease, try again.");
			}
		}
	}
}
