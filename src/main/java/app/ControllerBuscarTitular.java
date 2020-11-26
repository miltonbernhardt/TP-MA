package app;

import dto.DTOBuscarTitular;
import dto.DTOEmitirLicencia;
import enumeration.EnumTipoAlerta;
import enumeration.EnumTipoDocumento;
import gestor.GestorTitular;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

public class ControllerBuscarTitular {
    private static ControllerBuscarTitular instance = null;
    private ControllerEmitirLicencia controllerEmitirLicencia;

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
    @FXML private DatePicker dateNacimiento;

    @FXML private TableView<DTOBuscarTitular> tabla;

    @FXML private TableColumn<DTOBuscarTitular, String> columnaNombre;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaApellido;
    @FXML private TableColumn<DTOBuscarTitular, LocalDate> columnaFechaNacimiento;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaTipoDocumento;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaDocumento;
    @FXML private TableColumn<DTOBuscarTitular, Integer> columnaLicencias;

    private DTOBuscarTitular titularSeleccionado = null;

    @FXML
    private void initialize(){
        dateNacimiento.setValue(LocalDate.now());
        iniciarCombo();
        iniciarTabla();
    }

    private void iniciarCombo(){
        comboTipoDocumento.getItems().clear();
        comboTipoDocumento.setPromptText("Elegir tipo documento");
        comboTipoDocumento.getItems().addAll(EnumTipoDocumento.values());
    }

    private void iniciarTabla() {
        tabla.setPlaceholder(new Label("No hay usuarios que mostrar."));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        columnaTipoDocumento.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));
        columnaDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        //columnaLicencias.setCellValueFactory(new PropertyValueFactory<>("precioComprado"));
    }

    @FXML
    private void buscarTitular(){
        //ToDo validar la correctitud de todo
        HashMap<String, String> argumentos = new HashMap<>();
        argumentos.put("nombre", textNombre.getText());
        argumentos.put("apellido", textNombre.getText());
        argumentos.put("nacimiento", dateNacimiento.getValue().toString());
        if(comboTipoDocumento.getSelectionModel().getSelectedItem() != null) argumentos.put("tipoDocumento", comboTipoDocumento.getSelectionModel().getSelectedItem().getValue());
        else argumentos.put("tipoDocumento", "");
        argumentos.put("documento", textDocumento.getText());

        //ToDo ver de hacer asincronico
        GestorTitular.get().searchTitular(argumentos);
    }

    @FXML
    private void seleccionarTitular(){
        titularSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if(titularSeleccionado != null) {
            Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,
                    "Confirmar selección del titular",
                    "",
                    "¿Desea seleccionar a "+titularSeleccionado.getNombre()+" "+titularSeleccionado.getApellido()+"?",
                    null);

            if (result.get() == ButtonType.OK) {
                controllerEmitirLicencia.seleccionarTitular(titularSeleccionado);
                volver();
            }
            else{
                titularSeleccionado = null;
            }
        }
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

    public void setControllerEmitirLicencia(ControllerEmitirLicencia controllerEmitirLicencia) {
        this.controllerEmitirLicencia = controllerEmitirLicencia;
    }

    //ToDo validarKeyUp
}
