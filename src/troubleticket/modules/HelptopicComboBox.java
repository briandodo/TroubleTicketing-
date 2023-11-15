package troubleticket.modules;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import troubleticket.daos.HelptopicDaoSql;
import troubleticket.models.Helptopic;

/**
 *  GUI Component Class for Helptopic
 *  Populates a combobox with helptopics from the data source
 *
 */
public class HelptopicComboBox implements ComboboxControlI{
	private HelptopicDaoSql dao = new HelptopicDaoSql();
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

	public void setList(ArrayList<Helptopic> objects) {
		ArrayList<String> names = new ArrayList<String>();
		for (Helptopic object : objects)
			names.add(object.getName());
		observableList = FXCollections.observableArrayList(names);
		comboBox.setItems(observableList);
	}
}
