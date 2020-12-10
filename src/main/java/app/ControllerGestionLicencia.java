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
    private Boolean renovarObservaciones;
    private Boolean renovarTipoLicencia;
    private String observaciones;

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
    private Label labelEstadoLicencia1;
    @FXML
    private Label labelEstadoLicencia2;

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

    /**
     * Segun lo seleccionado puede existir la opcion de crear una nueva licencia del tipo, renovarla cambiando las
     * observaciones de la licencia, o renovar desde una licencia con menos privilegios a una mayor.
     */
    @FXML
    private void listenerCombo(){
        int indexSeleccionad = comboLicencias.getSelectionModel().getSelectedIndex();
        if(indexSeleccionad > -1){
            btnGenerarLicencia.setDisable(false);
            labelDescripcionLicencia.setText("- "+comboLicencias.getItems().get(indexSeleccionad).getDescripcion());
            renovarObservaciones = false;
            renovarTipoLicencia = false;

            EnumClaseLicencia selected = comboLicencias.getItems().get(indexSeleccionad);
            if(selected.equals(EnumClaseLicencia.CLASE_A) || selected.equals(EnumClaseLicencia.CLASE_B) ||
               selected.equals(EnumClaseLicencia.CLASE_F) || selected.equals(EnumClaseLicencia.CLASE_G)) {
                for (DTOLicenciasVigentes licencia : listaLicenciasVigentes) {
                    if (selected.equals(licencia.getClaseLicencia())) {
                        labelEstadoLicencia1.setText("Posee una licencia vigente " + selected);
                        labelEstadoLicencia2.setText("Puede cambiar las observaciones.");
                        renovarObservaciones = true;
                        observaciones = licencia.getObservaciones();
                        textObservaciones.setText(observaciones);
                        break;
                    }
                }
            }
            else if(selected.equals(EnumClaseLicencia.CLASE_C)){
                for (DTOLicenciasVigentes licencia : listaLicenciasVigentes) {
                    if (selected.equals(licencia.getClaseLicencia())) {
                        labelEstadoLicencia1.setText("Posee una licencia vigente " + selected);
                        labelEstadoLicencia2.setText("Puede cambiar las observaciones.");
                        renovarObservaciones = true;
                        observaciones = licencia.getObservaciones();
                        textObservaciones.setText(observaciones);
                        break;
                    }
                    else if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_B)){
                        //PUEDE CREAR UN TIPO C, REVOCANDO LA B
                        labelEstadoLicencia1.setText("Posee una licencia vigente " + EnumClaseLicencia.CLASE_B);
                        labelEstadoLicencia2.setText("Puede renovarla como una licencia " + selected);
                        renovarTipoLicencia = true;
                        break;
                    }
                }
            }
            else if(selected.equals(EnumClaseLicencia.CLASE_D) || selected.equals(EnumClaseLicencia.CLASE_E)){
                for (DTOLicenciasVigentes licencia : listaLicenciasVigentes) {
                    if (selected.equals(licencia.getClaseLicencia())) {
                        labelEstadoLicencia1.setText("Posee una licencia vigente " + selected);
                        labelEstadoLicencia2.setText("Puede cambiar las observaciones.");
                        renovarObservaciones = true;
                        observaciones = licencia.getObservaciones();
                        textObservaciones.setText(observaciones);
                        break;
                    }
                    else if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_B)){
                        //PUEDE CREAR UN TIPO C, REVOCANDO LA B
                        labelEstadoLicencia1.setText("Posee una licencia vigente " + EnumClaseLicencia.CLASE_B);
                        labelEstadoLicencia2.setText("Puede renovarla como una licencia " + selected);
                        renovarTipoLicencia = true;
                        break;
                    }
                    else if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_C)){
                        //PUEDE CREAR UN TIPO C, REVOCANDO LA B
                        labelEstadoLicencia1.setText("Posee una licencia vigente " + EnumClaseLicencia.CLASE_C);
                        labelEstadoLicencia2.setText("Puede renovarla como una licencia " + selected);
                        renovarTipoLicencia = true;
                        break;
                    }
                }
            }
            //si no tiene licencias vigentes en relacion a la seleccionada puede crear una nueva
            if(!renovarObservaciones && !renovarTipoLicencia){
                labelEstadoLicencia1.setText("No posee licencia vigente " + selected);
                labelEstadoLicencia2.setText("Puede emitir una nueva licencia " + selected);
                textObservaciones.setText("");
                observaciones = "";
            }
        }
    }

    @FXML
    private void gestionarLicencia() {

        String tituloVentana, contenidoMensaje, mensajeExito, mensajeNoExito;
        tituloVentana = "Confirmar emisión";
        contenidoMensaje = "¿Desea confirmar la acción?";
        mensajeExito = "Se realizó la operación de forma correcta.";
        mensajeNoExito = "No se ha podido realizar la operación.";
        Optional<ButtonType> result = AlertPanel.get(EnumTipoAlerta.CONFIRMACION,tituloVentana,
                "",contenidoMensaje,
                null);
        if (result.orElse(null) == ButtonType.OK) {
            //crea una nueva sin renovar
            if (!renovarTipoLicencia && !renovarObservaciones) {

                dto.setObservaciones(textObservaciones.getText());
                dto.setClaseLicencia(comboLicencias.getItems()
                        .get(comboLicencias.getSelectionModel()
                                .getSelectedIndex()));
                if (GestorLicencia.get().generarLicencia(dto))
                    AlertPanel.get(EnumTipoAlerta.INFORMACION,
                            "Confirmación",
                            "", mensajeExito,
                            null);
                else
                    AlertPanel.get(EnumTipoAlerta.ERROR,
                            "Error",
                            "", mensajeNoExito,
                            null);
                volver();
            }
            else if (renovarObservaciones) {
                if (textObservaciones.getText().equals(observaciones)) {
                    AlertPanel.get(EnumTipoAlerta.ERROR,
                            "Error",
                            "",
                            "Debe ingresar cambios en las observaciones.",
                            null);
                }
                else {
                    //Llamar al metodo renovarObservacionesLicencia
                    EnumClaseLicencia selected = comboLicencias.getItems()
                                                               .get(comboLicencias
                                                               .getSelectionModel()
                                                               .getSelectedIndex());
                    //Recorro para buscar la licencia a actualizar observaciones
                    for (DTOLicenciasVigentes licencia : listaLicenciasVigentes) {
                        if (selected.equals(licencia.getClaseLicencia())) {
                            licencia.setObservaciones(textObservaciones.getText());
                            if (GestorLicencia.get().renovarObservaciones(licencia))
                                AlertPanel.get(EnumTipoAlerta.INFORMACION,
                                        "Confirmación",
                                        "", mensajeExito,
                                        null);
                            else
                                AlertPanel.get(EnumTipoAlerta.ERROR,
                                        "Error",
                                        "", mensajeNoExito,
                                        null);
                            break;
                        }
                    }
                    volver();
                }
            }
            else if (renovarTipoLicencia) {
                //llamar al metodo renovarTipoLicencia
                dto.setObservaciones(textObservaciones.getText());
                dto.setClaseLicencia(comboLicencias.getItems()
                        .get(comboLicencias.getSelectionModel()
                                .getSelectedIndex()));
                if (GestorLicencia.get().renovarTipoLicencia(dto,listaLicenciasVigentes))
                    AlertPanel.get(EnumTipoAlerta.INFORMACION,
                            "Confirmación",
                            "", mensajeExito,
                            null);
                else
                    AlertPanel.get(EnumTipoAlerta.ERROR,
                            "Error",
                            "", mensajeNoExito,
                            null);
                volver();
            }
        }
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instanciaGestionLicencia = null;
    }
}