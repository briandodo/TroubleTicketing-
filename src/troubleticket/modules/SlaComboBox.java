package troubleticket.modules;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import troubleticket.daos.SlaDaoSql;
import troubleticket.models.Sla;

/**
 *  GUI Component Class for Sla
 *  Populates a combobox with slas from the data source
 *
 */
public class SlaComboBox implements ComboboxControlI {
	private SlaDaoSql dao = new SlaDaoSql();
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

	public void setList(ArrayList<Sla> objects) {
		ArrayList<String> names = new ArrayList<String>();
		for (Sla object : objects)
			names.add(object.getName());
		observableList = FXCollections.observableArrayList(names);
		comboBox.setItems(observableList);
	}
}
