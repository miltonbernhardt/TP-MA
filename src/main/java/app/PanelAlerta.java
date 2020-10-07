package app;

import java.util.Optional;

import enumeration.EnumTipoAlerta;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class PanelAlerta {

    /**
     * Muestra un panel que muestra error/información/confirmación/excepción de acuerdo a tipoAlerta.
     * tituloVentana: titulo de la ventana del panel.
     * contenidoTitulo: titulo dentro del panel. Suele ser "Error!", "Confirmación.", etc.
     * contenidoMensaje: descripción de la información que brinda el panel.
     * excepcion: este campo se completa con la excepción obtenida de un "catch", si no es null.
     * @param tipoAlerta
     * @param tituloVentana
     * @param contenidoTitulo
     * @param contenidoMensaje
     * @param excepcion
     * @return
     */

    public static Optional<ButtonType> get(EnumTipoAlerta tipoAlerta, String tituloVentana, String contenidoTitulo, String contenidoMensaje, Exception excepcion){
        Alert alert = null;
        switch (tipoAlerta){
            case ERROR:
                alert = new Alert(AlertType.ERROR);
                break;

            case EXCEPCION:
                alert = new Alert(AlertType.ERROR);
                tituloVentana = "Excepción!";
                contenidoTitulo = null;
                break;

            case INFORMACION:
                alert = new Alert(AlertType.INFORMATION);
                break;

            case CONFIRMACION:
                alert = new Alert(AlertType.CONFIRMATION);
                break;
        }

        alert.setTitle(tituloVentana);
        alert.setHeaderText(contenidoTitulo);
        alert.setContentText(contenidoMensaje);

        ControllerApp.setStyle(alert.getDialogPane());
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });

        if(tipoAlerta.equals(EnumTipoAlerta.EXCEPCION)){
            String exceptionText = excepcion.toString();

            Label label = new Label("La excepción fue:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            alert.getDialogPane().setExpandableContent(expContent);
        }

        return alert.showAndWait();

    }
}