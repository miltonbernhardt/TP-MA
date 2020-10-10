package app;

import dto.DTOEmitirLicencia;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoAlerta;
import gestor.GestorLicencia;
import gestor.GestorTitular;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


public class ControllerEmitirLicencia {

    private static ControllerEmitirLicencia instance = null;
    private DTOEmitirLicencia dto = null;

    public ControllerEmitirLicencia() { }

    public static ControllerEmitirLicencia get() {
        if (instance == null){
            //ControllerApp.setViewAnterior();
            //instance = (ControllerEmitirLicencia) ControllerApp.setRoot("", "VISTA ANTERIOR");
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
    private ComboBox<EnumClaseLicencia> comboLicencias;

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
        dto = GestorTitular.get().buscarTitular(101);

        textNombre.setText(dto.getNombre());
        textApellido.setText(dto.getApellido());

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        textFechaNacimiento.setText(dto.getFechaNacimiento().format(formatoFecha));

        textTipoDocumento.setText(dto.getTipoDocumento().getValue());
        textDocumento.setText(dto.getDocumento());

        cargarClasesLicencia(dto.getIdTitular());
    }

    private void cargarClasesLicencia(Integer idTitular){
        //TODO cambiar cuando esté listo EmitirTitular
        ArrayList<EnumClaseLicencia> listaLicencias = GestorLicencia.get().getClasesLicencias(idTitular);
        /*ArrayList<EnumClaseLicencia> listaLicencias = new ArrayList<>();
        listaLicencias.add(EnumClaseLicencia.CLASE_E);
        listaLicencias.add(EnumClaseLicencia.CLASE_B);*/

        int cantidadClasesLicencia = listaLicencias.size();
        if(cantidadClasesLicencia > 0){
            comboLicencias.setDisable(false);
            textObservaciones.setDisable(false);
            listaLicencias.forEach(listaLicencia -> comboLicencias.getItems().add(listaLicencia));
        }
    }

    @FXML
    private void listenerCombo(){
        Integer indexSeleccionad = comboLicencias.getSelectionModel().getSelectedIndex();
        if(indexSeleccionad > -1){
            btnEmitirLicencia.setDisable(false);
            labelDescripcionLicencia.setText(comboLicencias.getItems().get(indexSeleccionad).getDescripcion());
        }
    }

    @FXML
    private void emitirLicencia(){
        Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,"Confirmar emisión", null, "¿Desea confirmar la emisión de la licencia?",null);
        if (result.get() == ButtonType.OK){

            EnumClaseLicencia claseLicenciaElegida = comboLicencias.getItems().get(comboLicencias.getSelectionModel().getSelectedIndex());

            if(GestorLicencia.get().emitirLicencia(dto.getIdTitular(), claseLicenciaElegida, textObservaciones.getText())) {
                PanelAlerta.get(EnumTipoAlerta.INFORMACION,"Confirmación", null, "Se emitió la licencia de forma correcta.", null);
                //TODO implementar el volver();
            }
            else{
                PanelAlerta.get(EnumTipoAlerta.ERROR, "Error", null, "No se ha podido emitir la licencia.", null);
            }
        }
    }

}
