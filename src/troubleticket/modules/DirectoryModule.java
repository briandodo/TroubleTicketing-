package troubleticket.modules;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import troubleticket.daos.StaffDaoSql;
import troubleticket.daos.UserDaoSql;
import troubleticket.models.User;
import troubleticket.models.Staff;

/**
 *  GUI Module Class for Directory
 *  Shows a list of user and a list of staff members to quickly contact them
 *
 */
public class DirectoryModule implements VBoxModuleI {

	private UserDaoSql userDAO = new UserDaoSql();
	private StaffDaoSql staffDAO = new StaffDaoSql();
	private VBox vBox = null;
	private TableView<User> tableUsers = new TableView<>();
	private TableView<Staff> tableStaff = new TableView<>();
	private int tableWidths[] = { 30, 80, 90, 140, 140, 140 };

	/**
	 * Starts the module
	 */
	public VBox start() {
		vBox = paintDirectory();
		return vBox;
	}

	/**
	 * Paints the GUI interface
	 * @return VBox container with the GUI needed
	 */
	public VBox paintDirectory() {

		VBox vboxUsers = paintUsersTable();
		VBox vboxStaff = paintStaffTable();

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(0, 0, 0, 0));
		hbox.getChildren().addAll(vboxUsers, vboxStaff);

		vBox = new VBox(hbox);
		return vBox;
	}

	/**
	 * Paints users table
	 * @return
	 */
	private VBox paintUsersTable() {

		ArrayList<User> users = userDAO.getAll();
		ObservableList<User> data = FXCollections.observableArrayList(users);

		// start of User table
		Label label = new Label("Users");
		label.setFont(new Font("Arial", 20));

		tableUsers.setEditable(true);

		TableColumn<User, Integer> idColumn = new TableColumn<User, Integer>("ID");
		TableColumn<User, String> nameColumn = new TableColumn<User, String>("Username");
		TableColumn<User, String> fullNameColumn = new TableColumn<User, String>("Full Name");
		TableColumn<User, String> organizationColumn = new TableColumn<User, String>("Organization");
		TableColumn<User, String> emailColumn = new TableColumn<User, String>("Email");
		TableColumn<User, String> createdColumn = new TableColumn<User, String>("Created");
		TableColumn<User, String> updatedColumn = new TableColumn<User, String>("Updated");

		idColumn.setPrefWidth(tableWidths[0]);
		nameColumn.setPrefWidth(tableWidths[1]);
		fullNameColumn.setPrefWidth(tableWidths[2]);
		organizationColumn.setPrefWidth(tableWidths[2]);
		emailColumn.setPrefWidth(tableWidths[3]);
		createdColumn.setPrefWidth(tableWidths[4]);
		updatedColumn.setPrefWidth(tableWidths[5]);

		if (tableUsers.getColumns().size() == 0) {
			tableUsers.getColumns().add(idColumn);
			tableUsers.getColumns().add(nameColumn);
			tableUsers.getColumns().add(fullNameColumn);
			tableUsers.getColumns().add(organizationColumn);
			tableUsers.getColumns().add(emailColumn);
			tableUsers.getColumns().add(createdColumn);
			tableUsers.getColumns().add(updatedColumn);
		}
		
		idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("fullname"));
		organizationColumn.setCellValueFactory(new PropertyValueFactory<User, String>("organization"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		createdColumn.setCellValueFactory(new PropertyValueFactory<User, String>("created"));
		updatedColumn.setCellValueFactory(new PropertyValueFactory<User, String>("updated"));

		tableUsers.setItems(data);

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, tableUsers);

		return vbox;
	}

	/**
	 * Paints staff members table
	 * @return
	 */
	private VBox paintStaffTable() {

		ArrayList<Staff> staffs = staffDAO.getAll();
		ObservableList<Staff> data = FXCollections.observableArrayList(staffs);

		Label label = new Label("Staff Members");
		label.setFont(new Font("Arial", 20));

		tableStaff.setEditable(true);

		TableColumn<Staff, Integer> idColumn = new TableColumn<Staff, Integer>("ID");
		TableColumn<Staff, String> departmentColumn = new TableColumn<Staff, String>("Department");
		TableColumn<Staff, String> roleColumn = new TableColumn<Staff, String>("Role");
		TableColumn<Staff, String> nameColumn = new TableColumn<Staff, String>("Username");
		TableColumn<Staff, String> fullNameColumn = new TableColumn<Staff, String>("Full Name");
		TableColumn<Staff, String> emailColumn = new TableColumn<Staff, String>("Email");
		TableColumn<Staff, String> createdColumn = new TableColumn<Staff, String>("Created");
		TableColumn<Staff, String> updatedColumn = new TableColumn<Staff, String>("Updated");

		idColumn.setPrefWidth(tableWidths[0]);
		departmentColumn.setPrefWidth(tableWidths[1]);
		roleColumn.setPrefWidth(tableWidths[1]);
		nameColumn.setPrefWidth(tableWidths[1]);
		fullNameColumn.setPrefWidth(tableWidths[1]);
		emailColumn.setPrefWidth(tableWidths[3]);
		createdColumn.setPrefWidth(tableWidths[4]);
		updatedColumn.setPrefWidth(tableWidths[5]);

		if (tableStaff.getColumns().size() == 0) {
			tableStaff.getColumns().add(idColumn);
			tableStaff.getColumns().add(departmentColumn);
			tableStaff.getColumns().add(roleColumn);
			tableStaff.getColumns().add(nameColumn);
			tableStaff.getColumns().add(fullNameColumn);
			tableStaff.getColumns().add(emailColumn);
			tableStaff.getColumns().add(createdColumn);
			tableStaff.getColumns().add(updatedColumn);
		}
		
		idColumn.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("id"));
		departmentColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("department"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("role"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("name"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("fullname"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("email"));
		createdColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("created"));
		updatedColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("updated"));

		tableStaff.setItems(data);

		VBox vboxStaff = new VBox();
		vboxStaff.setSpacing(5);
		vboxStaff.setPadding(new Insets(10, 0, 0, 10));
		vboxStaff.getChildren().addAll(label, tableStaff);

		return vboxStaff;
	}

}
