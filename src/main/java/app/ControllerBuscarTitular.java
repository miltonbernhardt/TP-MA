package app;

import dto.DTOGestionTitular;
import enumeration.EnumTipoAlerta;
import enumeration.EnumTipoDocumento;
import gestor.GestorTitular;
import herramientas.DatePickerIniciador;
import herramientas.TextFielIniciador;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ControllerBuscarTitular {
    private static ControllerBuscarTitular instance = null;
    private ControllerGestionLicencia controllerGestionLicencia = null;
    private ControllerImprimirLicencia controllerImprimirLicencia = null;
    private ControllerModificarTitular controllerModificarTitular = null;

    public static ControllerBuscarTitular get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerBuscarTitular) ControllerApp.setRoot("buscarTitular", "Buscar titular");
        }
        return instance;
    }

    @FXML private TextField textNombre;
    @FXML private TextField textApellido;
    @FXML private TextField textDocumento;
    @FXML private ComboBox<EnumTipoDocumento> comboTipoDocumento;
    @FXML private DatePicker dateNacimientoInicial;
    @FXML private DatePicker dateNacimientoFinal;

    @FXML private TableView<DTOGestionTitular> tabla;

    @FXML private TableColumn<DTOGestionTitular, String> columnaNombre;
    @FXML private TableColumn<DTOGestionTitular, String> columnaApellido;
    @FXML private TableColumn<DTOGestionTitular, String> columnaFechaNacimiento;
    @FXML private TableColumn<DTOGestionTitular, String> columnaTipoDocumento;
    @FXML private TableColumn<DTOGestionTitular, String> columnaDocumento;

    private DTOGestionTitular titularSeleccionado = null;

    @FXML
    private void initialize(){
        iniciarCombo();
        iniciarTabla();
        DatePickerIniciador.iniciarDatePicker(dateNacimientoInicial);
        DatePickerIniciador.iniciarDatePicker(dateNacimientoFinal);
        listenerTextField();
    }

    private void listenerTextField(){
        TextFielIniciador.letrasAcento(textNombre);
        TextFielIniciador.letrasAcento(textApellido);
        TextFielIniciador.letrasNumero(textDocumento);
    }

    private void iniciarCombo(){
        comboTipoDocumento.getItems().clear();
        comboTipoDocumento.setPromptText("Elegir tipo documento");
        comboTipoDocumento.getItems().addAll(EnumTipoDocumento.values());
    }

    private void iniciarTabla() {
        tabla.setPlaceholder(new Label("No hay usuarios que mostrar."));
        tabla.setTooltip(new Tooltip("Doble click para seleccionar un titular"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        columnaTipoDocumento.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));
        columnaDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));

        tabla.setRowFactory( tv -> {
            TableRow<DTOGestionTitular> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                titularSeleccionado = tabla.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2 && (! fila.isEmpty()) && titularSeleccionado != null ) {
                    titularSeleccionado = fila.getItem();
                    selectionTitular(titularSeleccionado);
                }
            });
            return fila ;
        });
    }

    private void cargarTabla(List<DTOGestionTitular> lista) {
        tabla.getItems().clear();
        for(DTOGestionTitular dto:lista){
            tabla.getItems().add(dto);
        }
    }

    @FXML
    private void buscarTitular(){
        DTOGestionTitular argumentos = new DTOGestionTitular();
        argumentos.setNombre(textNombre.getText());
        argumentos.setApellido(textApellido.getText());
        argumentos.setFechaNacimientoInicial(dateNacimientoInicial.getValue());
        argumentos.setFechaNacimientoFinal(dateNacimientoFinal.getValue());
        if(comboTipoDocumento.getSelectionModel().getSelectedItem() != null)  argumentos.setTipoDocumento(comboTipoDocumento.getSelectionModel().getSelectedItem());
        else argumentos.setTipoDocumento(null);
        argumentos.setDocumento(textDocumento.getText());

        //ToDo ver de hacer asincronico
        cargarTabla(GestorTitular.get().searchTitular(argumentos));
    }

    private void selectionTitular(DTOGestionTitular dtoTitular){
        if(dtoTitular != null) {
            Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,
                    "Confirmar selección del titular",
                    "",
                    "¿Desea seleccionar a "+dtoTitular.getNombre()+" "+dtoTitular.getApellido()+"?",
                    null);

            if (result.orElse(null) == ButtonType.OK) {
                if(controllerGestionLicencia != null) controllerGestionLicencia.seleccionarTitular(dtoTitular);
                else if(controllerImprimirLicencia != null) controllerImprimirLicencia.seleccionarTitular(dtoTitular);
                volver();
            }
        }
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

    public void setControllerGestionLicencia(ControllerGestionLicencia controllerGestionLicencia) {
        this.controllerGestionLicencia = controllerGestionLicencia;
    }

    public void setControllerImprimirLicencia(ControllerImprimirLicencia controllerImprimirLicencia) {
        this.controllerImprimirLicencia = controllerImprimirLicencia;
    }
}