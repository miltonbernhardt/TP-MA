package app;

import dto.DTOBuscarTitular;
import enumeration.EnumTipoAlerta;
import enumeration.EnumTipoCampo;
import enumeration.EnumTipoDocumento;
import gestor.GestorTitular;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ControllerBuscarTitular {
    private static ControllerBuscarTitular instance = null;
    private ControllerEmitirLicencia controllerEmitirLicencia = null;

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

    @FXML private TableView<DTOBuscarTitular> tabla;

    @FXML private TableColumn<DTOBuscarTitular, String> columnaNombre;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaApellido;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaFechaNacimiento;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaTipoDocumento;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaDocumento;

    private DTOBuscarTitular titularSeleccionado = null;

    @FXML
    private void initialize(){
        iniciarCombo();
        iniciarTabla();
        iniciarDatePicker(dateNacimientoInicial);
        iniciarDatePicker(dateNacimientoFinal);
        listenerTextField();
    }

    private void listenerTextField(){
        textNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(EnumTipoCampo.SOLO_LETRAS.getValue()).matcher(newValue).matches())
                textNombre.setText(oldValue);
        });

        textApellido.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(EnumTipoCampo.SOLO_LETRAS.getValue()).matcher(newValue).matches())
                textApellido.setText(oldValue);
        });

        textDocumento.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(EnumTipoCampo.SOLO_LETRAS.getValue()).matcher(newValue).matches())
                textDocumento.setText(oldValue);
        });

        //ToDo validar las fechas en los day picker
    }

    private void iniciarDatePicker(DatePicker dateNacimientoInicial) {
        //ToDo poner fechas limites
        dateNacimientoInicial.setConverter(new StringConverter<LocalDate>()
        {
            private final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
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
            TableRow<DTOBuscarTitular> fila = new TableRow<>();
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

    private void cargarTabla(List<DTOBuscarTitular> lista) {
        tabla.getItems().clear();
        for(DTOBuscarTitular dto:lista){
            tabla.getItems().add(dto);
        }
    }

    @FXML
    private void buscarTitular(){
        //ToDo validar la correctitud de todo
        DTOBuscarTitular argumentos = new DTOBuscarTitular();
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

    private void selectionTitular(DTOBuscarTitular dtoTitular){
        if(dtoTitular != null) {
            Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,
                    "Confirmar selección del titular",
                    "",
                    "¿Desea seleccionar a "+dtoTitular.getNombre()+" "+dtoTitular.getApellido()+"?",
                    null);

            if (result.get() == ButtonType.OK) {
                if(controllerEmitirLicencia != null){
                    controllerEmitirLicencia.seleccionarTitular(dtoTitular);
                }
                volver();
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

}
