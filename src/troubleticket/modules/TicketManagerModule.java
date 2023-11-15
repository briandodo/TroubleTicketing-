package troubleticket.modules;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import troubleticket.Session;
import troubleticket.daos.TicketDaoSql;
import troubleticket.models.Ticket;

/**
 * GUI Module TicketManagerModule manages the actions to control the whole cycle of life of tickets.
 * It communicates with the rest of modules to see details, edit/create, delete, etc.
 */
public class TicketManagerModule implements VBoxModuleI {

	private TicketDaoSql ticketsDAO = new TicketDaoSql();
	private Session session;
	private VBox vBox = null;
	private TableView<Ticket> ticketsTable = new TableView<>();
	private HBox pager = new HBox();
	private HBox menuBar = new HBox();
	private Button btnSearchTickets = new Button("Search Tickets");
	private Button btnCreateTicket = new Button("New Ticket");
	private Button btnEditTicket = new Button("Edit Ticket");
	private Button btnSeeDetails = new Button("See Details");
	private Button btnDeleteTickets = new Button("Delete Tickets");
	private ComboBox<String> comboSortTypes;
	private ComboBox<String> comboStatus;
	private TextField inputSearch = new TextField();
	private Logger log = Logger.getLogger(TicketManagerModule.class.getName());

	/**
	 * Starts the module
	 */
	public VBox start() {
		try {
			paintMenuBar();
			paintTicketsTable();
			vBox = new VBox(menuBar, ticketsTable, pager);
			vBox.setPadding(new Insets(15));
		} catch (Exception e) {
			log.debug(e);
		}
		return vBox;
	}

	/**
	 * Sets a reference to the singleton Session object
	 * @param session
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Paints menu bar
	 */
	private void paintMenuBar() {

		inputSearch.setPromptText("Search by Number or Text");
		inputSearch.setPrefWidth(230);
		inputSearch.setOnMouseClicked(event -> inputSearch.selectAll());
		inputSearch.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				searchTickets();
		});

		btnCreateTicket.getStyleClass().add("btns-crud-tickets");
		btnEditTicket.getStyleClass().add("btns-crud-tickets");
		btnSeeDetails.getStyleClass().add("btns-crud-tickets");
		btnDeleteTickets.getStyleClass().add("btns-crud-tickets");

		Label label2_1 = new Label("Sort by: ");
		String sortingOptions[] = { 
				"Priority + Most reacently updated", 
				"Priority + Most reacently created",
				"Priority + Due Date", 
				"Due Date", 
				"Create Date", 
				"Update Date", 
				"Number" 
				};
		comboSortTypes = new ComboBox<String>(FXCollections.observableArrayList(sortingOptions));

		Label label3_1 = new Label("Change Status to: ");
		String bulkStatuses[] = { 
				"Opened", 
				"Assigned", 
				"Resolved", 
				"Closed" 
				};
		comboStatus = new ComboBox<String>(FXCollections.observableArrayList(bulkStatuses));

		menuBar.setAlignment(Pos.CENTER_LEFT);
		menuBar.getChildren().addAll(
				inputSearch, 
				btnSearchTickets, 
				new Label("|"), 
				btnCreateTicket, 
				btnEditTicket,
				btnSeeDetails, 
				btnDeleteTickets, 
				new Label("|"), 
				label2_1, 
				comboSortTypes, 
				label3_1, 
				comboStatus
			);
		menuBar.getStyleClass().add("tickets-manager-hbox1");

		// Actions for menu bar items
		btnSearchTickets.setOnAction(event -> searchTickets());
		btnCreateTicket.setOnAction(event -> createTicket());
		btnEditTicket.setOnAction(event -> editTicket());
		btnSeeDetails.setOnAction(event -> seeDetails());
		btnDeleteTickets.setOnAction(event -> deleteTickets());
		comboSortTypes.setOnAction(event -> sortTickets());
		comboStatus.setOnAction(event -> changeStatus());
	}

	/**
	 * Paints main tickets table
	 */
	public void paintTicketsTable() {

		String query = getQuery();
		String orderBy = (comboSortTypes.getValue() == null) ? null : comboSortTypes.getValue().toString();
		ArrayList<Ticket> tickets = ticketsDAO.getAll(query, orderBy, null);
		ObservableList<Ticket> data = FXCollections.observableArrayList(tickets);

		ticketsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ticketsTable.setId("tickets-table");

		int table1Widths[] = { 35, 60, 200, 80, 80, 80, 80, 80, 80, 120, 120, 120, 120, 120 };
		String table1ColLabels[] = { 
				"ID", 
				"Number", 
				"Subject", 
				"From User", 
				"Help Topic", 
				"Priority",
				"Status", 
				"SLA", 
				"Assigned Agent", 
				"Department",
				"Due Date",
				"Created at",
				"Updated at",
				"Closed at"
				};

		TableColumn<Ticket, Integer> idTableColumn = new TableColumn<Ticket, Integer>(table1ColLabels[0]);
		TableColumn<Ticket, String> numberTableColumn = new TableColumn<Ticket, String>(table1ColLabels[1]);
		TableColumn<Ticket, String> subjectTableColumn = new TableColumn<Ticket, String>(table1ColLabels[2]);
		TableColumn<Ticket, String> userTableColumn = new TableColumn<Ticket, String>(table1ColLabels[3]);
		TableColumn<Ticket, String> helptopicTableColumn = new TableColumn<Ticket, String>(table1ColLabels[4]);
		TableColumn<Ticket, String> priorityTableColumn = new TableColumn<Ticket, String>(table1ColLabels[5]);
		TableColumn<Ticket, String> statusTableColumn = new TableColumn<Ticket, String>(table1ColLabels[6]);
		TableColumn<Ticket, String> slaTableColumn = new TableColumn<Ticket, String>(table1ColLabels[7]);
		TableColumn<Ticket, String> staffTableColumn = new TableColumn<Ticket, String>(table1ColLabels[8]);
		TableColumn<Ticket, String> departmentTableColumn = new TableColumn<Ticket, String>(table1ColLabels[9]);
		TableColumn<Ticket, String> duedateTableColumn = new TableColumn<Ticket, String>(table1ColLabels[10]);
		TableColumn<Ticket, String> createdTableColumn = new TableColumn<Ticket, String>(table1ColLabels[11]);
		TableColumn<Ticket, String> updatedTableColumn = new TableColumn<Ticket, String>(table1ColLabels[12]);
		TableColumn<Ticket, String> closedTableColumn = new TableColumn<Ticket, String>(table1ColLabels[13]);

		idTableColumn.setPrefWidth(table1Widths[0]);
		numberTableColumn.setPrefWidth(table1Widths[1]);
		subjectTableColumn.setPrefWidth(table1Widths[2]);
		userTableColumn.setPrefWidth(table1Widths[3]);
		helptopicTableColumn.setPrefWidth(table1Widths[4]);
		priorityTableColumn.setPrefWidth(table1Widths[5]);
		statusTableColumn.setPrefWidth(table1Widths[6]);
		slaTableColumn.setPrefWidth(table1Widths[7]);
		staffTableColumn.setPrefWidth(table1Widths[8]);
		departmentTableColumn.setPrefWidth(table1Widths[9]);
		duedateTableColumn.setPrefWidth(table1Widths[10]);
		createdTableColumn.setPrefWidth(table1Widths[11]);
		updatedTableColumn.setPrefWidth(table1Widths[12]);
		closedTableColumn.setPrefWidth(table1Widths[13]);

		idTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("id"));
		numberTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("number"));
		subjectTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("subject"));
		userTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("username"));
		helptopicTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("helpTopic"));
		priorityTableColumn.setCellFactory(new Callback<TableColumn<Ticket, String>, TableCell<Ticket, String>>() {
			@Override
			public TableCell<Ticket, String> call(TableColumn<Ticket, String> param) {
				return new TableCell<Ticket, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						if (!empty) {
							int currentIndex = indexProperty().getValue() < 0 ? 0 : indexProperty().getValue();
							String sPriority = param.getTableView().getItems().get(currentIndex).getPriority();
							String sPriorityColor = param.getTableView().getItems().get(currentIndex)
									.getPriorityColor();
							setStyle(
									"-fx-background-color: " + ((sPriorityColor != null) ? sPriorityColor : "#DDFFDD"));
							setText((sPriority != null) ? sPriority : "LOW");
						}
					}
				};
			}
		});
		statusTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("status"));
		slaTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("sla"));
		staffTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("agentFullname"));
		departmentTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("departmentName"));
		duedateTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("duedateFormatted"));
		createdTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("createdFormatted"));
		updatedTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("updatedFormatted"));
		closedTableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("closedFormatted"));

		if (ticketsTable.getColumns().size() == 0) {
			ticketsTable.getColumns().add(idTableColumn); 
			ticketsTable.getColumns().add(numberTableColumn); 
			ticketsTable.getColumns().add(subjectTableColumn); 
			ticketsTable.getColumns().add(userTableColumn); 
			ticketsTable.getColumns().add(helptopicTableColumn);
			ticketsTable.getColumns().add(priorityTableColumn); 
			ticketsTable.getColumns().add(statusTableColumn); 
			ticketsTable.getColumns().add(slaTableColumn); 
			ticketsTable.getColumns().add(staffTableColumn); 
			ticketsTable.getColumns().add(departmentTableColumn);
			ticketsTable.getColumns().add(duedateTableColumn);
			ticketsTable.getColumns().add(createdTableColumn);
			ticketsTable.getColumns().add(updatedTableColumn);
			ticketsTable.getColumns().add(closedTableColumn);
		}
		ticketsTable.getItems().clear();
		ticketsTable.setItems(data);

		ticketsTable.setRowFactory(tv -> {
			TableRow<Ticket> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					seeDetails();
				}
			});
			return row;
		});
		ticketsTable.setFixedCellSize(26);
		ticketsTable.prefHeightProperty().bind(ticketsTable.fixedCellSizeProperty().multiply(Bindings.size(ticketsTable.getItems()).add(1.05)));
		//ticketsTable.minHeightProperty().bind(ticketsTable.prefHeightProperty());
		//ticketsTable.maxHeightProperty().bind(ticketsTable.prefHeightProperty());
	}

	/**
	 * Dialog to warn when tickets from tickets table have not been chosen before
	 * @param singular
	 */
	private void paintNoTicketSelected(boolean singular) {
		String title = (singular) ? "No Ticket Selected" : "No Tickets Selected";
		String body = (singular) ? "Ooops, you have not selected a ticket! Please, select one."
				: "Ooops, you have not selected tickets! Please, select at least one.";
		paintInfoMessage(AlertType.ERROR, title, body);
	}

	/**
	 * Creates a Alert Dialog
	 * @param alertType
	 * @param title
	 * @param body
	 */
	private void paintInfoMessage(AlertType alertType, String title, String body) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(body);
		alert.show();
	}

	/**
	 * Goes to search unto the database tickets using what search textfield and sort combobox has
	 */
	public void searchTickets() {
		paintTicketsTable();
	}

	/**
	 * Redirects to the TicketEditionModule in order to fill a form to create a new ticket
	 */
	public void createTicket() {
		VBox vbox = this.session.getApp().getTicketEditionModule().start();
		this.session.getApp().getTicketEditionModuleTab().setContent(vbox);
		this.session.getApp().addTab(this.session.getApp().getTicketEditionModuleTab());
		this.session.getApp().getTabPane().getSelectionModel().select(4);
	}

	/**
	 * Redirects to the TicketEditionModule in order to fill a form to edit a ticket
	 */
	public void editTicket() {
		Ticket ticket = (Ticket) ticketsTable.getSelectionModel().getSelectedItem();
		if (ticket == null)
			paintNoTicketSelected(true);
		else {
			createTicket();
			this.session.getApp().getTicketEditionModule().paintTicketInForm(ticket.getId());
		}
	}

	/**
	 * Redirects to the TicketStatusModule in order to see the detail of ticket (ticket, comments)
	 */
	public void seeDetails() {
		Ticket ticket = (Ticket) ticketsTable.getSelectionModel().getSelectedItem();
		if (ticket == null)
			paintNoTicketSelected(true);
		else {
			VBox vbox = this.session.getApp().getTicketStatusModule().paintTicket(ticket.getId());
			this.session.getApp().getTicketStatusModuleTab().setContent(vbox);
			this.session.getApp().getTabPane().getSelectionModel().select(1);
		}
	}

	/**
	 * Goes to the database to execute a bulk deletion of selected ids in the tickets table
	 */
	public void deleteTickets() {
		ObservableList<Ticket> tickets = ticketsTable.getSelectionModel().getSelectedItems();
		if (tickets.size() <= 0)
			paintNoTicketSelected(false);
		else {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			for (int i = 0; i < tickets.size(); i++) {
				Ticket t = (Ticket) tickets.get(i);
				ids.add((int) t.getId());
			}
			if (ticketsDAO.deleteAll(ids)) {
				paintInfoMessage(AlertType.INFORMATION, "Deletion Results",
						"Ticket(s) " + ids.toString() + " deleted sucessfully.");
				paintTicketsTable();
			} else {
				paintInfoMessage(AlertType.ERROR, "Deletion Results",
						"There has been an error trying to delete ticket(s) " + ids.toString());
			}
		}
	}

	/**
	 * Gets the search value
	 * @return inputSearch search query
	 */
	public String getQuery() {
		return (
				inputSearch.getText() == null || 
				inputSearch.getText().trim() == ""
				) 
				? null : inputSearch.getText().trim();
	}

	/**
	 * Refresh the table to see tickets sorted in the chosen manner in combobox of types of sorts
	 */
	public void sortTickets() {
		paintTicketsTable();
	}

	/**
	 * Goes to the database to change the status of selected tickets
	 */
	public void changeStatus() {
		ObservableList<Ticket> tickets = ticketsTable.getSelectionModel().getSelectedItems();
		if (tickets.size() <= 0)
			paintNoTicketSelected(false);
		else {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			for (int i = 0; i < tickets.size(); i++) {
				Ticket t = (Ticket) tickets.get(i);
				ids.add((int) t.getId());
			}
			String statusName = comboStatus.getValue().toString();
			if (ticketsDAO.updateByStatus(ids, statusName)) {
				paintInfoMessage(AlertType.INFORMATION, "Updating status",
						"Status for ticket(s) " + ids.toString() + " updated sucessfully.");
				paintTicketsTable();
			}
		}
	}
}
