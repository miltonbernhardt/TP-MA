package app;

import dto.DTOGestionTitular;
import dto.DTOEmitirLicencia;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoAlerta;
import exceptions.MenorDeEdadException;
import gestor.GestorLicencia;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class ControllerGestionLicencia {

    private static ControllerGestionLicencia instanceEmitir = null;
    private static ControllerGestionLicencia instanceRenovar = null;
    private DTOEmitirLicencia dto = null;
    private static boolean emitirLicencia;

    public static ControllerGestionLicencia get(boolean emitir) {
        emitirLicencia = emitir;
        if(emitir){
            if (instanceEmitir == null){
                ControllerApp.setViewAnterior();
                instanceEmitir = (ControllerGestionLicencia) ControllerApp.setRoot("gestionarLicencia", "Emitir licencia");
            }
            return instanceEmitir;
        }
        else{
            if (instanceRenovar == null){
                ControllerApp.setViewAnterior();
                instanceRenovar = (ControllerGestionLicencia) ControllerApp.setRoot("gestionarLicencia", "Renovar licencia");
            }
            return instanceRenovar;
        }
    }

    /* TextFields */
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

    /* TextArea */
    @FXML
    private TextArea textObservaciones;

    /* ComboBox */
    @FXML
    private ComboBox<EnumClaseLicencia> comboLicencias;

    /* Button */
    @FXML
    private Button btnGenerarLicencia;

    /* Label */
    @FXML
    private Label labelDescripcionLicencia;

    @FXML
    private void initialize(){
        if(!emitirLicencia) btnGenerarLicencia.setText("Renovar licencia");
    }

    @FXML
    private void buscarTitular(){
        ControllerBuscarTitular.get().setControllerGestionLicencia(this);
    }

    public void seleccionarTitular(DTOGestionTitular dtoGestionTitular){
        this.dto = new DTOEmitirLicencia();
        dto.setIdTitular(dtoGestionTitular.getIdTitular());
        dto.setFechaNacimiento(dtoGestionTitular.getFechaNacimiento());
        dto.setNombre(dtoGestionTitular.getNombre());
        dto.setApellido(dtoGestionTitular.getApellido());
        dto.setTipoDocumento(dtoGestionTitular.getTipoDocumento());
        dto.setDocumento(dtoGestionTitular.getDocumento());

        //Se resetea el estado del comboBox, la descripción de la clases de licencia, el estado del boton emitir licencia y la selección de la clase de licencia
        comboLicencias.getSelectionModel().clearSelection();
        comboLicencias.getItems().clear();
        comboLicencias.setPromptText("Seleccionar clase de licencia");
        btnGenerarLicencia.setDisable(true);
        labelDescripcionLicencia.setText("-");
        textNombre.setText("");
        textApellido.setText("");
        textFechaNacimiento.setText("");
        textTipoDocumento.setText("");
        textDocumento.setText("");

        //ToDo implementar backend de las licencias de renovarLicencia
        ArrayList<EnumClaseLicencia> listaLicencias = GestorLicencia.get().getClasesLicencias(dto.getIdTitular());
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
            PanelAlerta.get(EnumTipoAlerta.ERROR,"Operación no válida","","No se le puede dar de alta una licencia '"+dto.getNombre()+" "+dto.getApellido()+"'",null);
        }
    }

    @FXML
    private void listenerCombo(){
        int indexSeleccionad = comboLicencias.getSelectionModel().getSelectedIndex();
        if(indexSeleccionad > -1){
            btnGenerarLicencia.setDisable(false);
            labelDescripcionLicencia.setText("- "+comboLicencias.getItems().get(indexSeleccionad).getDescripcion());
        }
    }

    @FXML
    private void generarLicencia() throws MenorDeEdadException {
        String tituloVentana, contenidoMensaje, mensajeExito, mensajeNoExito;

        if(emitirLicencia){
            tituloVentana = "Confirmar emisión";
            contenidoMensaje = "¿Desea confirmar la emisión de la licencia?";
            mensajeExito = "Se emitió la licencia de forma correcta.";
            mensajeNoExito = "No se ha podido emitir la licencia.";
        }
        else{
            tituloVentana = "Confirmar renovación";
            contenidoMensaje = "¿Desea confirmar la renovación de la licencia?";
            mensajeExito = "Se renovó la licencia de forma correcta.";
            mensajeNoExito = "No se ha podido renovar la licencia.";
        }

        Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,tituloVentana,"",contenidoMensaje,null);
        if (result.orElse(null) == ButtonType.OK) {
            dto.setObservaciones(textObservaciones.getText());
            dto.setClaseLicencia(comboLicencias.getItems().get(comboLicencias.getSelectionModel().getSelectedIndex()));

            //ToDo cuando se haga la logica de renovar licencia, fijarse si usar funciones distintas para la generacion de la licencia.
            if(emitirLicencia){
                if (GestorLicencia.get().generarLicencia(dto))
                    PanelAlerta.get(EnumTipoAlerta.INFORMACION, "Confirmación", "", mensajeExito, null);
                else
                    PanelAlerta.get(EnumTipoAlerta.ERROR, "Error", "", mensajeNoExito, null);
            }
            volver();
        }
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        if(emitirLicencia) instanceEmitir = null;
        else instanceRenovar = null;
    }
}