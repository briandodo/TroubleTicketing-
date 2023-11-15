package troubleticket.modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import troubleticket.Session;
import troubleticket.daos.TicketDaoSql;

/**
 * GUI Module UserAuthenticationModule manages the login process for user when the app is on "user" mode in the enviroment properties
 */
public class UserAuthenticationModule implements AuthenticationModuleI{

	private Session session = Session.getInstance();
	private GridPane form = null;
	private TextField emailTextField = new TextField();
	private TextField numberTextField = new TextField();
	private Text feedbackText = new Text();
	private Button checkStatusBtn = new Button("Check Status of this ticket");
	private Validator validator = new Validator();

	public void start(Stage stage) {
		stage.setTitle("Trouble Ticketing | Check Status of Ticket");
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

		Label emailLabel = new Label("Email:");
		emailTextField.setMaxWidth(200);
		form.add(emailLabel, 0, 1);
		form.add(emailTextField, 1, 1);

		Label numberLabel = new Label("Ticket number:");
		numberTextField.setMaxWidth(200);
		form.add(numberLabel, 0, 2);
		form.add(numberTextField, 1, 2);

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(checkStatusBtn);

		form.add(hbBtn, 1, 4);
		form.add(feedbackText, 1, 5);

		checkStatusBtn.setOnAction(event -> submitForm());
	}

	private void submitForm() {
		
		validator.createCheck().dependsOn("notEmptyEmail", emailTextField.textProperty()).withMethod(c -> {
			if (emailTextField.getText().trim() == "") {
				c.error("Email cannot be empty");
			} else {
				String regex = "^(.+)@(.+)$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(emailTextField.getText().trim());
				if (!matcher.matches())
					c.error("Email is not valid");
			}
		}).decorates(emailTextField).immediate();

		validator.createCheck().dependsOn("notEmptyNumber", numberTextField.textProperty()).withMethod(c -> {
			if (numberTextField.getText().trim() == "") {
				c.error("Number cannot be empty");
			}
		}).decorates(numberTextField).immediate();

		if (validator.validate()) {
			TicketDaoSql ticket = new TicketDaoSql();
			int ticketId = ticket.getIdByEmailAndNumber(emailTextField.getText().trim(),
					numberTextField.getText().trim());
			if (ticketId != 0) {
				session.getApp().paintModules();
				goToStatusModule(ticketId);
			} else
				feedbackText.setText("Invalid ticket number or username");
		}
	}

	private void goToStatusModule(int ticketId) {
		VBox vbox = this.session.getApp().getTicketStatusModule().paintTicket(ticketId);
		this.session.getApp().getTicketStatusModuleTab().setContent(vbox);
		this.session.getApp().getTabPane().getSelectionModel().select(0);
	}
}
