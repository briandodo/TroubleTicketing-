package troubleticket.modules;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.synedra.validatorfx.Validator;
import troubleticket.Session;
import troubleticket.daos.ThreadDaoSql;
import troubleticket.daos.TicketDaoSql;
import troubleticket.models.Ticket;
import javafx.scene.control.TextArea;
import troubleticket.models.Thread;
import troubleticket.models.ThreadEntry;

/**
 * GUI Module TicketStatusModule manages the way to add more information (comment threads) to a ticket and also show its information
 */
public class TicketStatusModule implements VBoxModuleI{
	
	private TicketDaoSql ticketsDAO = new TicketDaoSql();
	private ThreadDaoSql threadDAO = new ThreadDaoSql();
	private Ticket ticket = null;
	private VBox vBox = new VBox();
	private VBox vBoxStatus = new VBox();
	private FlowPane ticketSummaryFlowPane = new FlowPane();
	private TabPane formsTabPane = new TabPane();
	private TabPane entriesTabPane = new TabPane();
	private Tab publicEntriesTab = new Tab();
	private Tab privateEntriesTab = new Tab();
	private VBox vboxPublicCommentForm = new VBox();
	private TextField publicTitleTextField = new TextField();
	private TextArea publicCommentTextArea = new TextArea();
	private Button publicCommentSubmitButton = new Button("Submit Public Comment");
	private VBox vboxPrivateCommentForm = new VBox();
	private TextField privateTitleTextField = new TextField();
	private TextArea privateCommentTextArea = new TextArea();
	private Button privateCommentSubmitButton = new Button("Submit Private Comment");
	private String appMode = "";

	/**
	 * Starts the module
	 */
	public VBox start() {
		vBox = new VBox(new Label("Please, indicate a ticket to visualize."));
		vBox.setId("vbox-ticket-status");
		vBox.setPadding(new Insets(20));
		vBox.setSpacing(10);
		return vBox;
	}
	
	/**
	 * Paints the ticket information: summary cards, title, body, dates, forms and public and private comments
	 * @param ticketId
	 * @return VBox container
	 */
	public VBox paintTicket(int ticketId) {
		
		ticket = ticketsDAO.get(ticketId);
		appMode = Session.getInstance().getApp().getInitAs();

		Text createdText = new Text();
		createdText.setText("Created on: " + ticket.getCreatedFormatted());
		createdText.setX(10);
		createdText.setY(10);
		createdText.setFont(Font.font(16));
		createdText.setFill(Color.BLACK);
		
		Text updatedText = new Text();
		updatedText.setText("Updated on: " + ticket.getUpdatedFormatted());
		updatedText.setX(10);
		updatedText.setY(10);
		updatedText.setFont(Font.font(16));
		updatedText.setFill(Color.BLACK);
		
		Text subjectText = new Text();
		subjectText.setText("Subject: " + ticket.getSubject() + "\nDescription: " + ticket.getBody());
		subjectText.setX(10);
		subjectText.setY(10);
		subjectText.setFont(Font.font(16));
		subjectText.setFill(Color.DARKBLUE);
		
		vBoxStatus.setAlignment(Pos.CENTER_LEFT);
		vBoxStatus.getChildren().clear();
		vBoxStatus.getChildren().addAll(createdText, updatedText, subjectText);
		
		//Cards
		StackPane numberCard = ticketCard("Ticket Number", ticket.getNumber() + "", "#426D80");
		StackPane statusCard = ticketCard("Ticket Status", ticket.getStatus(), "#426D80");
		StackPane priorityCard = ticketCard("Ticket Priority", ticket.getPriority(), ticket.getPriorityColor());
		StackPane helptopicCard = ticketCard("Ticket Help Topic", ticket.getHelpTopic(), "#426D80");
		StackPane staffCard = ticketCard("Ticket Assigned To", ticket.getAgentFullname(), "#297994");
		StackPane slaCard = ticketCard("Ticket SLA", ticket.getSla(), "#297994");
		
		//Flow pane to show the box horizontally
		ticketSummaryFlowPane.setHgap(10);
		ticketSummaryFlowPane.getChildren().clear();
		ticketSummaryFlowPane.getChildren().addAll(
				numberCard, 
				statusCard,
				priorityCard,
				helptopicCard,
				staffCard,
				slaCard
		);

		Label commentLabel = new Label("Post New Comment\t\t\t\t\t\t\t\t      Past Comments");
		commentLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		
		//TabPane Comment Forms
		formsTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//Tab Private Comment Form
		Tab privateCommentFormTab = new Tab();
		privateCommentFormTab.setText("Private");
		
		privateTitleTextField = new TextField();
		privateTitleTextField.setPromptText("Enter your subject here");
		privateCommentTextArea = new TextArea();
		privateCommentTextArea.setPromptText("Enter your comment here");
		privateCommentTextArea.setPrefHeight(120);
		privateCommentTextArea.setPrefWidth(450);
        privateCommentSubmitButton.setMinWidth(50);
        privateCommentSubmitButton.setOnAction(action -> submitPrivateForm());
        vboxPrivateCommentForm = new VBox(privateTitleTextField, privateCommentTextArea, privateCommentSubmitButton);
        VBox.setMargin(privateTitleTextField, new Insets(10,0,10,0) );
        privateCommentFormTab.setContent(vboxPrivateCommentForm);
        
		//Tab Public Comment Form
        Tab publicCommentTab = new Tab();
		publicCommentTab.setText("Public");
		
        publicTitleTextField = new TextField();
		publicTitleTextField.setPromptText("Enter your subject here");
		publicCommentTextArea = new TextArea();
		publicCommentTextArea.setPromptText("Enter your comment here");
		publicCommentTextArea.setPrefHeight(120);
		publicCommentTextArea.setPrefWidth(4500);
	    publicCommentSubmitButton.setMinWidth(50);
        publicCommentSubmitButton.setOnAction(action -> submitPublicForm());
        vboxPublicCommentForm = new VBox(publicTitleTextField, publicCommentTextArea, publicCommentSubmitButton);
        VBox.setMargin(publicTitleTextField, new Insets(10,0,10,0) );
        publicCommentTab.setContent(vboxPublicCommentForm);
		
        formsTabPane.getTabs().clear();
        
		if(appMode.equals("staff"))
			formsTabPane.getTabs().add(privateCommentFormTab);
		formsTabPane.getTabs().add(publicCommentTab);

		HBox hBoxNewComment = new HBox();
		
		//New comment tab pane in hBox
		hBoxNewComment.getChildren().clear();
		hBoxNewComment.getChildren().addAll(formsTabPane);
		hBoxNewComment.setMaxWidth(500);

		//Label for comment thread
		Label labelThread = new Label("Comment Thread");
		labelThread.setFont(Font.font(null, FontWeight.BOLD, 20));
		labelThread.setTextFill(Color.DARKSEAGREEN);
		
		//Paint entries (comments)
		VBox commentThread = paintThread(ticketId);

		HBox hBoxComment;
		if(commentThread != null)
			hBoxComment = new HBox(hBoxNewComment, commentThread);
		else
			hBoxComment = new HBox(hBoxNewComment);
		hBoxComment.setPadding(new Insets(15, 12, 15, 12));
		hBoxComment.setSpacing(50);

		vBox.setId("vbox-ticket-status");
		vBox.getChildren().clear();
		vBox.getChildren().add(ticketSummaryFlowPane);
		vBox.getChildren().add(vBoxStatus);
		vBox.getChildren().add(commentLabel);
		vBox.getChildren().add(hBoxComment);
		vBox.setPadding(new Insets(20));
		vBox.setSpacing(10);
		
		return vBox;
	}
	
	/**
	 * Paints a card on ticket summary
	 * @param title
	 * @param body
	 * @param bgColor
	 * @return
	 */
	private StackPane ticketCard(String title, String body, String bgColor) {
		
		Label label = new Label (title);
		label.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
		label.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
		label.setTextFill(Color.LAVENDER);
		
		Text text = new Text();
		text.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
		text.setText(body);
		text.setX(20);
		text.setY(50);
		text.setFont(Font.font("Tahoma", FontWeight.BOLD, 21));     
		text.setFill(Color.WHITE);
		text.applyCss();

		Rectangle rectangle = new Rectangle();
		rectangle.setX(100);
		rectangle.setY(100);
		double width = (text.getLayoutBounds().getWidth() > 160.0) ? text.getLayoutBounds().getWidth() + 20 : 160.0;
		rectangle.setWidth(width);
		rectangle.setHeight(100);
		rectangle.setFill((bgColor == null) ? Color.BLACK : Color.web(bgColor,1.0));
		rectangle.setArcWidth(9);
		rectangle.setArcHeight(9); 
		
		StackPane stackPane = new StackPane();
		StackPane.setAlignment(label, Pos.TOP_CENTER);
		StackPane.setMargin(label, new Insets(7));
		StackPane.setAlignment(text, Pos.CENTER);
		stackPane.getChildren().addAll(rectangle, label, text);
		
		return stackPane;
	}
	
	/**
	 * Paints the thread entries (comments) in two separeted tabs
	 * @param ticketId
	 * @return VBox container of tabs of thread entry comments
	 */
	private VBox paintThread(int ticketId) {
		
		Thread thread = threadDAO.getByTicketId(ticketId);
		
		VBox threadsVBox = new VBox();		 
		VBox threadIVBox = new VBox();
		VBox threadPVBox = new VBox();
		
		threadIVBox.setPrefWidth(600);
		threadPVBox.setPrefWidth(600);
		
		threadIVBox.setId("vbox-comments");
		threadPVBox.setId("vbox-comments");
		
		threadIVBox.getChildren().clear();
		threadPVBox.getChildren().clear();
		
		
		entriesTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//Tab of "I"nternal or Private comments
		
		if(appMode.equals("staff")) {
			
			privateEntriesTab.setText("Private Comments");
			ScrollPane scrollIVBox = new ScrollPane();
			scrollIVBox.setContent(threadIVBox);
			privateEntriesTab.setContent(scrollIVBox);
			
			for(ThreadEntry entry : thread.getEntries()) {
				if (entry.getType().equals("I")) {
					Label label = new Label();
					label.setWrapText(true);
					label.setText(
						"Title: " + entry.getTitle() +
						"\nLast Updated: " + entry.getLastupdate() +
						"\nDetails:\n" + entry.getBody() +
						"\n\n"
					);
					threadIVBox.getChildren().add(label);
				}
			}
			entriesTabPane.getTabs().addAll(privateEntriesTab);
		}
		
		//Tab of "P"ublic comments
		publicEntriesTab.setText("Public Comments");
		ScrollPane scrollPVBox = new ScrollPane();
		scrollPVBox.setContent(threadPVBox);
		publicEntriesTab.setContent(scrollPVBox);
		
		for(ThreadEntry entry : thread.getEntries()) {
			if(entry.getType().equals("P")) {
				Label label = new Label();
				label.setWrapText(true);	
				label.setText(
					"Title: " + entry.getTitle() +
					"\nLast Updated: " + entry.getLastupdate() +
					"\nDetails:\n" + entry.getBody() +
					"\n\n"
				);
				threadPVBox.getChildren().add(label);
			}	
		}
		entriesTabPane.getTabs().add(publicEntriesTab);
		
		threadsVBox = new VBox(entriesTabPane);
 
		return threadsVBox;
	}
	
	/**
	 * Goes to the database to create a new Public comment entry
	 */
	private void submitPublicForm() {
		
		Validator validator = new Validator();
		
		validator.createCheck().dependsOn("notEmptyPublicTitle", publicTitleTextField.textProperty()).withMethod(c -> {
			if (publicTitleTextField.getText().trim() == "") {
				c.error("Public Comment Title cannot be empty");
			}
		}).decorates(publicTitleTextField).immediate();
		
		validator.createCheck().dependsOn("notEmptyPublicComment", publicCommentTextArea.textProperty()).withMethod(c -> {
			if (publicCommentTextArea.getText() == null || publicCommentTextArea.getText() == "") {
				c.error("Public Comment Body cannot be empty");
			}
		}).decorates(publicCommentTextArea).immediate();
		
		if (validator.validate()) {
			processNewComment("P", publicTitleTextField, publicCommentTextArea);
		}
	}

	/**
	 * Goes to the database to create a new Private comment entry
	 */
	private void submitPrivateForm() {
		
		Validator validator = new Validator();
		
		validator.createCheck().dependsOn("notEmptyPrivateTitle", privateTitleTextField.textProperty()).withMethod(c -> {
			if (privateTitleTextField.getText().trim() == "") {
				c.error("Private Comment Title cannot be empty");
			}
		}).decorates(privateTitleTextField).immediate();
		
		validator.createCheck().dependsOn("notEmptyPrivateComment", privateCommentTextArea.textProperty()).withMethod(c -> {
			if (privateCommentTextArea.getText() == null || privateCommentTextArea.getText() == "") {
				c.error("Private Comment Body cannot be empty");
			}
		}).decorates(privateCommentTextArea).immediate();
		
		if (validator.validate()) {
			processNewComment("I", privateTitleTextField, privateCommentTextArea);
		}
	}
	
	/**
	 * Process the creation of a validated comment entry
	 */
	private void processNewComment(String threadType, TextField textfield, TextArea textarea) {
		Integer ticketId = ticket.getId();
		Integer ticketThreadId = ticket.getThreadId();
		String entryTitle = textfield.getText().trim();
		String entryBody = textarea.getText().trim();
		boolean answerInsert;
		try {
			answerInsert = threadDAO.insertEntry(ticketId, ticketThreadId, threadType, entryTitle, entryBody);
			String feedback = "";
			if(answerInsert) {
				feedback = "Comment created successfully";
			} else {
				feedback = "Comment could not be updated";
			}
			paintInfoMessage(AlertType.INFORMATION, "Database answer", feedback);
			paintTicket(ticketId);
			SingleSelectionModel<Tab> selectionModel = entriesTabPane.getSelectionModel();
			if(threadType.equals("P"))
				selectionModel.select(publicEntriesTab);
			else
				selectionModel.select(privateEntriesTab);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Paints a dialog with a message
	 * @param alertType
	 * @param title
	 * @param body
	 */
	private void paintInfoMessage(AlertType alertType, String title, String body) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(body);
		alert.showAndWait();
	}
	
}