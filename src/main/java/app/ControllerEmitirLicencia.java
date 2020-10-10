package app;

import dto.DTOEmitirLicencia;
import enumeration.EnumClaseLicencia;
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
        //TODO Se comenta, falta revisar para que funcione correctamente -> no me dejaba ejecutar el app, coli comenta
        // dto = GestorTitular.get().buscarTitular();

        textNombre.setText(dto.getNombre());
        textApellido.setText(dto.getApellido());

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        textFechaNacimiento.setText(dto.getFechaNacimiento().format(formatoFecha));

        textTipoDocumento.setText(dto.getTipoDocumento().getValue());
        textDocumento.setText(dto.getDocumento());

        cargarClasesLicencia(dto.getIdTitular());
    }

    private void cargarClasesLicencia(Integer idTitular){
        //TODO cambiar cuando esté listo el método
        //ArrayList<EnumClaseLicencia> listaLicencias = GestorLicencia.get().getClasesLicencias(idTitular);
        ArrayList<EnumClaseLicencia> listaLicencias = new ArrayList<EnumClaseLicencia>();
        listaLicencias.add(EnumClaseLicencia.CLASE_E);
        listaLicencias.add(EnumClaseLicencia.CLASE_B);

        Integer cantidadClasesLicencia = listaLicencias.size();
        if(cantidadClasesLicencia > 0){
            comboLicencias.setDisable(false);
            textObservaciones.setDisable(false);
            for(int i=0; i<cantidadClasesLicencia; i++){
                comboLicencias.getItems().add(listaLicencias.get(i));
            }
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
        GestorLicencia.get().emitirLicencia(dto.getIdTitular(), comboLicencias.getItems().get(comboLicencias.getSelectionModel().getSelectedIndex()));
    }

}
