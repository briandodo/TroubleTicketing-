package troubleticket.modules;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import troubleticket.daos.TicketPriorityDaoSql;
import troubleticket.models.TicketPriority;

/**
 *  GUI Component Class for TicketPriority
 *  Populates a combobox with ticket priorities from the data source
 *
 */
public class TicketPriorityComboBox implements ComboboxControlI {

	private TicketPriorityDaoSql dao = new TicketPriorityDaoSql();
	private ComboBox<String> comboBox = new ComboBox<>();
	private ObservableList<String> observableList;

	public ComboBox<String> getComboBox() {
		return comboBox;
	}

	public void setComboBox(ComboBox<String> comboBox) {
		this.comboBox = comboBox;
	}

	public void setValue(String value) {
		comboBox.setValue(value);
	}

	public void setAll() {
		setList(dao.getAll());
	}

	public void setList(ArrayList<TicketPriority> objects) {
		ArrayList<String> names = new ArrayList<String>();
		for (TicketPriority object : objects)
			names.add(object.getName());
		observableList = FXCollections.observableArrayList(names);
		comboBox.setItems(observableList);
	}
}
