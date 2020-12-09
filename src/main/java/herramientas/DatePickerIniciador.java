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

    /** Setea el formato de los objetos DatePicker y les establece un rango de fechas válidas */
    static public void iniciarDatePicker(DatePicker datePicker) {
        LocalDate minDate = LocalDate.of(1930, 1, 1);
        LocalDate maxDate = GestorTitular.get().getFechaMinima();
        datePicker.setPromptText( "dia/mes/año" );
        datePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item.isAfter(maxDate) || item.isBefore(minDate)){
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }});
        datePicker.setConverter(new DatePickerIniciador());
        datePicker.setTooltip(new Tooltip("dd/mm/aaaa"));
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
            LocalDate min = LocalDate.of(1930, 1, 1);
            LocalDate max = GestorTitular.get().getFechaMinima();
            LocalDate date = LocalDate.from(DATE_FORMATTER.parse(formattedString));
            if (date.isAfter(max) || date.isBefore(min)) {
                return null;
            }
            return date;
        } catch (DateTimeParseException parseExc) {
            return null;
        }
    }
}
