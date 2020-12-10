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
    public static LocalDate minDate = LocalDate.of(1930, 1, 1);
    public static LocalDate maxDate = GestorTitular.get().getFechaMinima();

    /** Setea el formato de los objetos DatePicker y les establece un rango de fechas válidas.
        Si el valor de maximo es true se setea como valor por default el valor de fecha máximo permitido
        por el sistema para registrarse (18 años hacia atrás el dia actual).
        Si no se setea el valor minimo permitido, que se tomo la fecha de 1/1/1930. */
    static public void iniciarDatePicker(DatePicker datePicker, boolean maximo) {
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
        if(maximo) datePicker.setValue(maxDate);
        else datePicker.setValue(minDate);
    }

    @Override
    /** Convierte un LocalDate a un String con el formato indicado. */
    public String toString(LocalDate localDate) {
        if (localDate == null)
            return "";
        return DATE_FORMATTER.format(localDate);
    }

    @Override
    /** Convierte un String a un dato tipo LocalDate si posee el formato indicado.
        Si está en el formato adecuado verifica que esté dentro del rango de fechas válido. */
    public LocalDate fromString(String formattedString) {
        try {
            LocalDate date = LocalDate.from(DATE_FORMATTER.parse(formattedString));
            if (date.isAfter(maxDate) || date.isBefore(minDate)) {
                return null;
            }
            return date;
        } catch (DateTimeParseException parseExc) {
            return null;
        }
    }
}
