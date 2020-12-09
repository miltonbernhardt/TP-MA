package app;

import dto.DTOGestionTitular;
import dto.DTOEmitirLicencia;
import dto.DTOLicenciasVigentes;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoAlerta;
import gestor.GestorLicencia;
import herramientas.AlertPanel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Licencia;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerGestionLicencia {

    private static ControllerGestionLicencia instanciaGestionLicencia = null;
    private DTOEmitirLicencia dto = null;
    private List<DTOLicenciasVigentes> listaLicenciasVigentes;

    public static ControllerGestionLicencia get() {
        if (instanciaGestionLicencia == null){
            ControllerApp.setViewAnterior();
            instanciaGestionLicencia = (ControllerGestionLicencia) ControllerApp.setRoot("gestionarLicencia", "Gestionar licencia");
        }
        return instanciaGestionLicencia;
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
    private Label labelEstadoLicencia;

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
        listaLicenciasVigentes = GestorLicencia.get().getLicenciasVigentes(dtoGestionTitular.getIdTitular());

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

        ArrayList<EnumClaseLicencia> listaLicencias = GestorLicencia.get().getClasesLicencias(dto.getIdTitular());
        int cantidadClasesLicencia = listaLicencias.size();
            if (cantidadClasesLicencia > 0) {
                textNombre.setText(dto.getNombre());
                textApellido.setText(dto.getApellido());
                DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                textFechaNacimiento.setText(dto.getFechaNacimiento().format(formatoFecha));
                textTipoDocumento.setText(dto.getTipoDocumento().getValue());
                textDocumento.setText(dto.getDocumento());
                comboLicencias.setDisable(false);
                textObservaciones.setDisable(false);
                listaLicencias.forEach(listaLicencia -> comboLicencias.getItems().add(listaLicencia));
            } else {
                AlertPanel.get(EnumTipoAlerta.ERROR, "Operación no válida", "", "No se le puede dar de alta una licencia '" + dto.getNombre() + " " + dto.getApellido() + "'", null);
            }
    }

    @FXML
    private void listenerCombo(){
        int indexSeleccionad = comboLicencias.getSelectionModel().getSelectedIndex();
        if(indexSeleccionad > -1){
            btnGenerarLicencia.setDisable(false);
            labelDescripcionLicencia.setText("- "+comboLicencias.getItems().get(indexSeleccionad).getDescripcion());
            //ToDo fijarse estado renovar/emitir
            Boolean vigente = false;

            EnumClaseLicencia selected = comboLicencias.getItems().get(indexSeleccionad);
            switch (selected) {
                case CLASE_A:
                    for (DTOLicenciasVigentes licencia : listaLicenciasVigentes) {
                        if (selected.equals(licencia.getClaseLicencia())) {
                            labelEstadoLicencia.setText("Posee una licencia vigente " + selected + " - Puede cambiar las observaciones.");
                            textObservaciones.setText(licencia.getObservaciones());
                            vigente = true;
                            break;
                        }

                    }
            }
        }
    }

    @FXML
    private void generarLicencia() {
        String tituloVentana, contenidoMensaje, mensajeExito, mensajeNoExito;

        tituloVentana = "Confirmar emisión";
        contenidoMensaje = "¿Desea confirmar la emisión de la licencia?";
        mensajeExito = "Se emitió la licencia de forma correcta.";
        mensajeNoExito = "No se ha podido emitir la licencia.";

        Optional<ButtonType> result = AlertPanel.get(EnumTipoAlerta.CONFIRMACION,tituloVentana,"",contenidoMensaje,null);
        if (result.orElse(null) == ButtonType.OK) {
            dto.setObservaciones(textObservaciones.getText());
            dto.setClaseLicencia(comboLicencias.getItems().get(comboLicencias.getSelectionModel().getSelectedIndex()));

            if (GestorLicencia.get().generarLicencia(dto))
                AlertPanel.get(EnumTipoAlerta.INFORMACION, "Confirmación", "", mensajeExito, null);
            else
                AlertPanel.get(EnumTipoAlerta.ERROR, "Error", "", mensajeNoExito, null);
            volver();
        }
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instanciaGestionLicencia = null;
    }
}