package app;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class ControllerEmitirLicencia {

    private static ControllerEmitirLicencia instance = null;

    public ControllerEmitirLicencia() { }

    public static ControllerEmitirLicencia get() {
        if (instance == null){
            //ControllerApp.setViewAnterior();
            instance = (ControllerEmitirLicencia) ControllerApp.setRoot("CU02View01", "Buscar productos");
        }
        return instance;
    }

    /*
        TextFields
     */
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellido;
    @FXML
    private TextField textEdad;
    @FXML
    private TextField textTipoDocumento;
    @FXML
    private TextField textDocumento;

    /*
        TextArea
     */
    @FXML
    private TextArea textObservaciones;

    /*
        ComboBox
     */
    @FXML
    private ComboBox comboLicencias;

    /*
        Button
     */
    @FXML
    private Button btnBuscarTitular;
    private Button btnEmitirLicencia;
    private Button btnVolver;

    /*
        Label
     */
    private Label labelDescripcionLicencia;

}
