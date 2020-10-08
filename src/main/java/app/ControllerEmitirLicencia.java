package app;

import dto.DTOEmitirLicencia;
import gestor.GestorLicencia;
import gestor.GestorTitular;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ControllerEmitirLicencia {

    private static ControllerEmitirLicencia instance = null;

    public ControllerEmitirLicencia() { }

    public static ControllerEmitirLicencia get() {
        if (instance == null){
            //ControllerApp.setViewAnterior();
            //instance = (ControllerEmitirLicencia) ControllerApp.setRoot("CU02View01", "Emitir licencia");
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
    private TextField textFechaNacimiento;
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
    private Button btnEmitirLicencia;

    /*
        Label
     */
    @FXML
    private Label labelDescripcionLicencia;

    @FXML
    public void buscarTitular(){
        DTOEmitirLicencia dto = GestorTitular.get().buscarTitular();

        textNombre.setText(dto.getNombre());
        textApellido.setText(dto.getApellido());

        String pattern = "dd/MM/yyyy";
        DateFormat formatoFecha = new SimpleDateFormat(pattern);
        textFechaNacimiento.setText(formatoFecha.format(dto.getFechaNacimiento()));

        textTipoDocumento.setText(dto.getTipoDocumento().getValue());
        textDocumento.setText(dto.getDocumento());

        //TODO seguir logica
    }

    private void cargarClasesLicencia(Integer idTitular){
        GestorLicencia.get().getClasesLicencias(idTitular);
    }

}
