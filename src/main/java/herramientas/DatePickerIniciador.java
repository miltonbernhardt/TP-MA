package herramientas;

import gestor.GestorTitular;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatePickerIniciador extends StringConverter<LocalDate> {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private boolean hasParseError = false;

    public boolean hasParseError() {
        return hasParseError;
    }

    static public void iniciarDatePicker(DatePicker dateNacimiento) {
        LocalDate minDate = LocalDate.of(1940, 1, 1);
        LocalDate maxDate = GestorTitular.get().getFechaMinima();
        dateNacimiento.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});
        dateNacimiento.setConverter(new DatePickerIniciador());
        dateNacimiento.setTooltip(new Tooltip("dd/mm/aaaa"));
    }

    @Override
    public String toString(LocalDate localDate) {
        if (localDate == null)
            return "";
        return DATE_FORMATTER.format(localDate);
    }

    @Override
    public LocalDate fromString(String formattedString) {
        try {
            LocalDate min = LocalDate.of(1940, 1, 1);
            LocalDate max = GestorTitular.get().getFechaMinima();
            LocalDate date = LocalDate.from(DATE_FORMATTER.parse(formattedString));
            if (date.isAfter(max) || date.isBefore(min)) {
                hasParseError = true;
                return null;
            }
            hasParseError = false;
            return date;
        } catch (DateTimeParseException parseExc) {
            hasParseError = true;
            return null;
        }
    }
}
