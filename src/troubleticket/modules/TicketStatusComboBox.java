package troubleticket.modules;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import troubleticket.daos.TicketStatusDaoSql;
import troubleticket.models.TicketStatus;

/**
 *  GUI Component Class for TicketStatus
 *  Populates a combobox with ticket status from the data source
 *
 */
public class TicketStatusComboBox implements ComboboxControlI {
	private TicketStatusDaoSql dao = new TicketStatusDaoSql();
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

	public void setList(ArrayList<TicketStatus> objects) {
		ArrayList<String> names = new ArrayList<String>();
		for (TicketStatus object : objects)
			names.add(object.getName());
		observableList = FXCollections.observableArrayList(names);
		comboBox.setItems(observableList);
	}
}
