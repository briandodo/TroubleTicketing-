package troubleticket.modules;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GUI Component Class for a DateTimePicker. 
 * Configurable datetime format where both date and time can be changed
 * via the text field and the date can additionally be changed via the 
 * JavaFX default date picker.
 *
 */
public class DateTimePicker extends DatePicker {
	
	public static final String DefaultFormat = "yyyy-MM-dd HH:mm";
	private DateTimeFormatter formatter;
	private ObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.now());
	
	/**
	 * Sets the format for the date
	 */
	private ObjectProperty<String> format = new SimpleObjectProperty<String>() {
		public void set(String newValue) {
			super.set(newValue);
			formatter = DateTimeFormatter.ofPattern(newValue);
		}
	};

	/**
	 * Sets format for the editor box
	 */
	public void alignColumnCountWithFormat() {
		getEditor().setPrefColumnCount(getFormat().length());
	}

	/**
	 * Constructor
	 */
	public DateTimePicker() {
		getStyleClass().add("datetime-picker");
		setFormat(DefaultFormat);
		setConverter(new InternalConverter());
        alignColumnCountWithFormat();

		// Syncronize changes to the underlying date value back to the dateTimeValue
		valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				dateTimeValue.set(null);
			} else {
				if (dateTimeValue.get() == null) {
					dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
				} else {
					LocalTime time = dateTimeValue.get().toLocalTime();
					dateTimeValue.set(LocalDateTime.of(newValue, time));
				}
			}
		});

		// Syncronize changes to dateTimeValue back to the underlying date value
		dateTimeValue.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDate dateValue = newValue.toLocalDate();
                boolean forceUpdate = dateValue.equals(valueProperty().get());
                // Make sure the display is updated even when the date itself wasn't changed
                setValue(dateValue);
                if (forceUpdate) setConverter(new InternalConverter());
            } else {
                setValue(null);
            }

        });

		// Persist changes onblur
		getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue)
				simulateEnterPressed();
		});

	}

	/**
	 * Catches the enter key pressed
	 */
	private void simulateEnterPressed() {
		getEditor().commitValue();
	}

	/**
	 * Gets date time
	 * @return date time
	 */
	public LocalDateTime getDateTimeValue() {
		return dateTimeValue.get();
	}

	/**
	 * Sets date time
	 * @param dateTimeValue
	 */
	public void setDateTimeValue(LocalDateTime dateTimeValue) {
		this.dateTimeValue.set(dateTimeValue);
	}

	/**
	 * Gets date time value
	 */
	public ObjectProperty<LocalDateTime> dateTimeValueProperty() {
		return dateTimeValue;
	}

	/**
	 * Gets the string format for date time
	 * @return format
	 */
	public String getFormat() {
		return format.get();
	}

	/**
	 * Gets format property
	 * @return format
	 */
	public ObjectProperty<String> formatProperty() {
		return format;
	}

	/**
	 * Sets format
	 * @param format
	 */
	public void setFormat(String format) {
		this.format.set(format);
		alignColumnCountWithFormat();
	}

	/**
	 * Internal class to convert Local Dates to internal date
	 *
	 */
	class InternalConverter extends StringConverter<LocalDate> {
		public String toString(LocalDate object) {
			LocalDateTime value = getDateTimeValue();
			return (value != null) ? value.format(formatter) : "";
		}

		public LocalDate fromString(String value) {
			if (value == null || value.isEmpty()) {
				dateTimeValue.set(null);
				return null;
			}

			dateTimeValue.set(LocalDateTime.parse(value, formatter));
			return dateTimeValue.get().toLocalDate();
		}
	}
}
