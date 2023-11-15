package troubleticket.modules;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import troubleticket.daos.UserDaoSql;
import troubleticket.models.User;

/**
 *  GUI Component Class for User
 *  Populates a combobox with users from the data source
 */
public class UserComboBox implements ComboboxControlI {

	private UserDaoSql dao = new UserDaoSql();
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

	public void setList(ArrayList<User> objects) {
		ArrayList<String> names = new ArrayList<String>();
		for (User object : objects)
			names.add(object.getFullname() + " (" + object.getName() + ")");
		observableList = FXCollections.observableArrayList(names);
		comboBox.setItems(observableList);
	}

}
