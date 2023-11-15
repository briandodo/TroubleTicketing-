package troubleticket.modules;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import troubleticket.daos.StaffDaoSql;
import troubleticket.models.Staff;

/**
 *  GUI Component Class for Staff
 *  Populates a combobox with staff members from the data source
 *
 */
public class StaffComboBox implements ComboboxControlI {
	private StaffDaoSql dao = new StaffDaoSql();
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

	public void setList(ArrayList<Staff> objects) {
		ArrayList<String> names = new ArrayList<String>();
		for (Staff object : objects)
			names.add(object.getFullname() + " (" + object.getName() + ")");
		observableList = FXCollections.observableArrayList(names);
		comboBox.setItems(observableList);
	}
}
