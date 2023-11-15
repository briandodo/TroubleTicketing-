package troubleticket;

import java.io.IOException;
import org.apache.log4j.Logger;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import troubleticket.modules.*;

/**
 * Main class of Trouble Ticket JavaFX App contains the main stage, session, tabs, and modules so that the app works correctly
 * 
 * @author Israel Gonzalez <igonzals@nmsu.edu>
 */
public class Main extends Application {

	// Session
	private Session session = Session.getInstance();
	// Main Stage
	private Stage stage;
	// Pane and Tabs
	private TabPane mainAppTabPane = new TabPane();
	private Tab ticketManagerModuleTab = new Tab("Tickets Manager");
	private Tab ticketStatusModuleTab = new Tab("Ticket Status");
	private Tab directoryModuleTab = new Tab("Directory");
	private Tab dashboardModuleTab = new Tab("Dashboard");
	private Tab ticketEditionModuleTab = new Tab("Ticket Edition");
	// Modules
	private StaffAuthenticationModule staffAuthenticationModule = new StaffAuthenticationModule();
	private UserAuthenticationModule userAuthenticationModule = new UserAuthenticationModule();
	private TicketManagerModule ticketManagerModule = new TicketManagerModule();
	private TicketStatusModule ticketStatusModule = new TicketStatusModule();
	private TicketEditionModule ticketEditionModule = new TicketEditionModule();
	private DashboardModule dashboardModule = new DashboardModule();
	// Logger
	private Logger log = Logger.getLogger(Main.class.getName());

	/**
	 * Constructor of the class Main. Sets the app on the session.
	 * @throws  Exception
	 */
	public Main() throws Exception {	
		session.setApp(this);
	}

	/**
	 * Executable of main function
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Get the mode of the app (staff or user). 
	 * It can be set in the environments.properties
	 * @return mode of the app
	 */
	public String getInitAs() {
		String initAs = "";
		try {
			initAs = session.getInitAs();
		} catch (IOException e) {
			log.error(e);
		}
		return initAs;
	}

	/**
	 * Starts the application
	 */
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		if (getInitAs().equals("staff"))
			staffAuthenticationModule.start(this.stage);
		else if (getInitAs().equals("user"))
			userAuthenticationModule.start(this.stage);
		else
			System.out.println("INIT_AS needs to be specified in Environment properties");
	}

	/**
	 * Stops the application
	 */
	@Override
	public void stop() {
		SqlConnection.getInstance().closeConnection();
	}

	/**
	 * Paints the main Scene with the modules according to the app mode 
	 */
	public void paintModules() {

		try {
			// First Module by default
			if (getInitAs().equals("staff")) {
				ticketManagerModule.setSession(session);
				VBox vboxTickets = ticketManagerModule.start();
				ticketManagerModuleTab.setContent(vboxTickets);
				ticketManagerModuleTab.setClosable(false);
				mainAppTabPane.getTabs().add(ticketManagerModuleTab);
			}
			// Tab initialization for TicketStaus Module
			ticketStatusModuleTab.setClosable(false);
			mainAppTabPane.getTabs().add(ticketStatusModuleTab);
			ticketStatusModuleTab.setOnSelectionChanged((EventHandler<Event>) t -> {
				if (ticketStatusModuleTab.isSelected() && ticketStatusModuleTab.getContent() == null) {
					ticketStatusModuleTab.setContent(ticketStatusModule.start());
				}
			});
			// Tab initialization for Directory Module
			if (getInitAs().equals("staff")) {
				directoryModuleTab.setClosable(false);
				mainAppTabPane.getTabs().add(directoryModuleTab);
				directoryModuleTab.setOnSelectionChanged((EventHandler<Event>) t -> {
					if (directoryModuleTab.isSelected() && directoryModuleTab.getContent() == null) {
						DirectoryModule directoryModule = new DirectoryModule();
						directoryModuleTab.setContent(directoryModule.start());
					}
				});
			}
			// Tab initialization for Dashboard Module
			if (getInitAs().equals("staff")) {
				dashboardModuleTab.setClosable(false);
				mainAppTabPane.getTabs().add(dashboardModuleTab);
				dashboardModuleTab.setOnSelectionChanged((EventHandler<Event>) t -> {
					if (dashboardModuleTab.isSelected() && dashboardModuleTab.getContent() == null) {
						DashboardModule dashboardModule = new DashboardModule();
						dashboardModuleTab.setContent(dashboardModule.start());
					} else {
						dashboardModule.paint();
					}
				});
			}
			// Tab initialization for Ticket Edition module
			if (getInitAs().equals("staff")) {
				ticketEditionModuleTab.setClosable(true);
				mainAppTabPane.getTabs().add(ticketEditionModuleTab);
				ticketEditionModuleTab.setOnSelectionChanged((EventHandler<Event>) t -> {
					if (ticketEditionModuleTab.isSelected() && ticketEditionModuleTab.getContent() == null) {
						TicketEditionModule ticketEdition = new TicketEditionModule();
						ticketEditionModuleTab.setContent(ticketEdition.start());
					}
				});
			}
			Scene scene = new Scene(mainAppTabPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("TroubleTicket Ticketing System");
			stage.setMaximized(true);

			stage.show();

		} catch (Exception e) {
			log.debug(e);
		}
	}

	/**
	 * Obtains the main TabPane
	 * @return main TabPane of the app
	 */
	public TabPane getTabPane() {
		return mainAppTabPane;
	}

	/**
	 * Adds a tab containing a module
	 * @param tab
	 */
	public void addTab(Tab tab) {
		if (tab == null)
			return;
		boolean exists = false;
		for (Tab t : this.mainAppTabPane.getTabs())
			if (t.equals(tab))
				exists = true;
		if (!exists)
			this.mainAppTabPane.getTabs().add(tab);
	}

	/**
	 * Sets the main TabPane
	 * @param main TabPane of the app
	 */
	public void setMainTabPane(TabPane tabPane) {
		this.mainAppTabPane = tabPane;
	}
	
	/**
	 * Sets main app tab pane
	 * @param mainAppTabPane
	 */
	public void setMainAppTabPane(TabPane mainAppTabPane) {
		this.mainAppTabPane = mainAppTabPane;
	}

	/**
	 * Gets Ticket Manager Module tab
	 * @return tab of the module
	 */
	public Tab getTicketManagerModuleTab() {
		return ticketManagerModuleTab;
	}

	/**
	 * Sets Ticket Manager Module tab
	 * @param ticketManagerModuleTab
	 */
	public void setTicketManagerModuleTab(Tab ticketManagerModuleTab) {
		this.ticketManagerModuleTab = ticketManagerModuleTab;
	}

	/**
	 * Gets Ticket Status Module tab
	 * @return tab of the module
	 */
	public Tab getTicketStatusModuleTab() {
		return ticketStatusModuleTab;
	}

	/**
	 * Sets Ticket Status Module tab
	 * @param ticketStatusModuleTab
	 */
	public void setTicketStatusModuleTab(Tab ticketStatusModuleTab) {
		this.ticketStatusModuleTab = ticketStatusModuleTab;
	}

	/**
	 * Gets Directory Module tab
	 * @return tab of the module
	 */
	public Tab getDirectoryModuleTab() {
		return directoryModuleTab;
	}

	/**
	 * Sets Directory Module tab
	 * @param directoryModuleTab
	 */
	public void setDirectoryModuleTab(Tab directoryModuleTab) {
		this.directoryModuleTab = directoryModuleTab;
	}

	/**
	 * Gets Dashboard Module tab
	 * @return tab of the module
	 */
	public Tab getDashboardModuleTab() {
		return dashboardModuleTab;
	}

	/**
	 * Sets Dashboard Module tab
	 * @param dashboardModuleTab
	 */
	public void setDashboardModuleTab(Tab dashboardModuleTab) {
		this.dashboardModuleTab = dashboardModuleTab;
	}

	/**
	 * Gets Ticket Edition Module tab
	 * @return tab of the module
	 */
	public Tab getTicketEditionModuleTab() {
		return ticketEditionModuleTab;
	}

	/**
	 * Sets Ticket Edition Module tab
	 * @param ticketEditionModuleTab
	 */
	public void setTicketEditionModuleTab(Tab ticketEditionModuleTab) {
		this.ticketEditionModuleTab = ticketEditionModuleTab;
	}

	/**
	 * Gets Staff Authentication Module
	 * @return staff authentication module
	 */
	public StaffAuthenticationModule getStaffAuthenticationModule() {
		return staffAuthenticationModule;
	}

	/**
	 * Sets Staff Authentication Module
	 * @param staffAuthenticationModule
	 */
	public void setStaffAuthenticationModule(StaffAuthenticationModule staffAuthenticationModule) {
		this.staffAuthenticationModule = staffAuthenticationModule;
	}

	/**
	 * Gets Ticket Manager Module
	 * @return ticketManagerModule
	 */
	public TicketManagerModule getTicketManagerModule() {
		return ticketManagerModule;
	}

	/**
	 * Sets Ticket Manager Module
	 * @param ticketManagerModule
	 */
	public void setTicketManagerModule(TicketManagerModule ticketManagerModule) {
		this.ticketManagerModule = ticketManagerModule;
	}

	/**
	 * Gets Ticket Status Module
	 * @return ticketStatusModule
	 */
	public TicketStatusModule getTicketStatusModule() {
		return ticketStatusModule;
	}

	/**
	 * Sets Ticket Status Module
	 * @param ticketStatusModule
	 */
	public void setTicketStatusModule(TicketStatusModule ticketStatusModule) {
		this.ticketStatusModule = ticketStatusModule;
	}

	/**
	 * Gets Ticket Edition Module
	 * @return ticketEditionModule
	 */
	public TicketEditionModule getTicketEditionModule() {
		return ticketEditionModule;
	}

	/**
	 * Sets Ticket Edition Module
	 * @param ticketEditionModule
	 */
	public void setTicketEditionModule(TicketEditionModule ticketEditionModule) {
		this.ticketEditionModule = ticketEditionModule;
	}
}
