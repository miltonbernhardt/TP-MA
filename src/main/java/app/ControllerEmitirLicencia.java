package app;

import dto.DTOEmitirLicencia;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoAlerta;
import exceptions.MenorDeEdadException;
import gestor.GestorLicencia;
import gestor.GestorTitular;
import hibernate.DAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.Titular;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerEmitirLicencia {

    private static ControllerEmitirLicencia instance = null;
    private DTOEmitirLicencia dto = null;

    public static ControllerEmitirLicencia get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerEmitirLicencia) ControllerApp.setRoot("emitirLicencia", "Emitir licencia");
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
    @FXML
    private ComboBox<DTOEmitirLicencia> comboTitulares;


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
        /*
        TODO cambiar al implementar buscar/alta titular
         */
        comboTitulares.getItems().clear();
        try {
            comboTitulares.getItems().addAll(GestorTitular.get().buscarTitulares());

        }catch(Exception e) {e.printStackTrace();}

        if(comboTitulares.getItems().size()==0) {
            comboTitulares.setDisable(true);
        }
        else{
            comboTitulares.setDisable(false);
            comboTitulares.setPromptText("Seleccionar titular");
        }
    }

    @FXML
    private void seleccionarTitular(){

        /*
            TODO cambiar al implementar buscar/alta titular
         */

        dto = comboTitulares.getSelectionModel().getSelectedItem();

        ArrayList<EnumClaseLicencia> listaLicencias = GestorLicencia.get().getClasesLicencias(dto.getIdTitular());

        //Se resetea el estado del comboBox, la descricpción de la clases de licencia, el estado del boton emitir licencia y la selección de la clase de licencaia
        comboLicencias.getSelectionModel().clearSelection();
        comboLicencias.getItems().clear();
        comboLicencias.setPromptText("Seleccionar clase de licencia");
        btnEmitirLicencia.setDisable(true);
        labelDescripcionLicencia.setText("-");
        textNombre.setText("");
        textApellido.setText("");
        textFechaNacimiento.setText("");
        textTipoDocumento.setText("");
        textDocumento.setText("");

        int cantidadClasesLicencia = listaLicencias.size();
        if(cantidadClasesLicencia > 0){
            //En caso de que al titular se le puede emitir una licencia, se procede a setear los campos con sus respectivos datos
            textNombre.setText(dto.getNombre());
            textApellido.setText(dto.getApellido());

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            textFechaNacimiento.setText(dto.getFechaNacimiento().format(formatoFecha));

            textTipoDocumento.setText(dto.getTipoDocumento().getValue());
            textDocumento.setText(dto.getDocumento());

            comboLicencias.setDisable(false);
            textObservaciones.setDisable(false);
            listaLicencias.forEach(listaLicencia -> comboLicencias.getItems().add(listaLicencia));
        }
        else{
            /*
            TODO ver que pasa si es 0, ¿se renueva o se avisa que no se puede emitir licencia?
             */
            PanelAlerta.get(EnumTipoAlerta.ERROR,
                    "Operación no válida",
                    "",
                    "No se le puede dar de alta una licencia a id:"+dto.getIdTitular()+" - '"+dto.getNombre()+" "+dto.getApellido()+"'",
                    null);
        }
    }


    @FXML
    private void listenerCombo(){
        int indexSeleccionad = comboLicencias.getSelectionModel().getSelectedIndex();
        if(indexSeleccionad > -1){
            btnEmitirLicencia.setDisable(false);
            labelDescripcionLicencia.setText("- "+comboLicencias.getItems().get(indexSeleccionad).getDescripcion());
        }
    }

    @FXML
    private void emitirLicencia() throws MenorDeEdadException {
        Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,
                    "Confirmar emisión",
                    "",
                    "¿Desea confirmar la emisión de la licencia?",
                    null);

        if (result.get() == ButtonType.OK){

            dto.setObservaciones(textObservaciones.getText());
            dto.setClaseLicencia(comboLicencias.getItems().get(comboLicencias.getSelectionModel().getSelectedIndex()));

            if(GestorLicencia.get().emitirLicencia(dto)) {
                PanelAlerta.get(EnumTipoAlerta.INFORMACION,
                            "Confirmación",
                            "",
                            "Se emitió la licencia de forma correcta.",
                            null);
            }
            else{
                PanelAlerta.get(EnumTipoAlerta.ERROR,
                            "Error",
                            "",
                            "No se ha podido emitir la licencia.",
                            null);
            }

            volver();
        }
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

    /*
    @FXML
    private void ImprimirLicencia() {
        ControllerImprimirLicencia.get();
    }*/
}
