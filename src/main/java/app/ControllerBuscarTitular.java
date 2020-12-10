package app;

import dto.DTOGestionTitular;
import enumeration.EnumTipoAlerta;
import enumeration.EnumTipoDocumento;
import gestor.GestorTitular;
import herramientas.DatePickerIniciador;
import herramientas.AlertPanel;
import herramientas.TextFielIniciador;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class ControllerBuscarTitular {
    private static ControllerBuscarTitular instance = null;
    private ControllerGestionLicencia controllerGestionLicencia = null;
    private ControllerImprimirLicencia controllerImprimirLicencia = null;
    private ControllerModificarTitular controllerModificarTitular = null;

    /** Constructor singleton, que establece mediante el método setRoot del ControllerApp la Scene de buscarTitular en el
       Stage principal. Previamente guarda la vista anterior en caso de querer volver a la Scene anterior. */
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
    /** Inicializa la Scene. */
    private void initialize(){
        iniciarCombo();
        iniciarTabla();
        DatePickerIniciador.iniciarDatePicker(dateNacimientoInicial, false);
        DatePickerIniciador.iniciarDatePicker(dateNacimientoFinal, true);
        listenerTextField();
    }

    /** Añade los listener correpondientes a los campos mediante la clase TextFielIniciador, el cuál indica que valores
       del campo pueden aceptarse en dichos campos. */
    private void listenerTextField(){
        TextFielIniciador.letrasAcento(textNombre);
        TextFielIniciador.letrasAcento(textApellido);
        TextFielIniciador.letrasNumero(textDocumento);
    }

    /** Inicializa el ComboBox correspondiente a los tipos de documentos*/
    private void iniciarCombo(){
        comboTipoDocumento.getItems().clear();
        comboTipoDocumento.setPromptText("Tipo documento");
        comboTipoDocumento.getItems().addAll(EnumTipoDocumento.values());
    }

    /** Inicializa la tabla. Indica que datos contiene cada columna según los atributos que posea el DTOBuscarTitular.
       Cada fila de la tabla representa un DTOBuscarTitular.
       También se establece el comportamiento de esta tabla al hacer doble click, que viene a ser que selecciona la fila
       en la cuál se hizo doble click y retorna dicho DTOBuscarTitular. */
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



    @FXML
    /** Método que se llama al clickear en el botón "Buscar titular". Pasa como argumentos al método searchTitular() del
      GestorTitular los distintos campos que se dan como parámetros de busqueda de titular. Luego dicho metodo le
      retornará una lista de DTOBuscarTitular que simulan la lista de Titulars que cumplieron los parámetros de
      búsqueda y con esta lista se carga la tabla para mostrarlos mediante el método cargarTabla. */
    private void buscarTitular(){
        DTOGestionTitular argumentos = new DTOGestionTitular();
        argumentos.setNombre(textNombre.getText());
        argumentos.setApellido(textApellido.getText());
        argumentos.setFechaNacimientoInicial(dateNacimientoInicial.getValue());
        argumentos.setFechaNacimientoFinal(dateNacimientoFinal.getValue());
        if(comboTipoDocumento.getSelectionModel().getSelectedItem() != null)
            argumentos.setTipoDocumento(comboTipoDocumento.getSelectionModel().getSelectedItem());
        else
            argumentos.setTipoDocumento(null);
        argumentos.setDocumento(textDocumento.getText());

        cargarTabla(GestorTitular.get().searchTitular(argumentos));
    }

    /** Método que permite cargar la tabla según la lista luego de llamar al método "buscarTitular" en este controler. */
    private void cargarTabla(List<DTOGestionTitular> lista) {
        tabla.getItems().clear();
        for(DTOGestionTitular dto:lista){
            tabla.getItems().add(dto);
        }
    }

    /** Método que al hacer doble click sobre una filla de la tabla muestra un panel de confirmación pregunta si se desea
       seleccionar dicho titular. Si la respuesta es afirmativa se retorna el titular al controller que haya solicitado
       sus servicios y luego se vuelve a la pantalla anterior. */
    private void selectionTitular(DTOGestionTitular dtoTitular){
        if(dtoTitular != null) {
            Optional<ButtonType> result = AlertPanel.get(EnumTipoAlerta.CONFIRMACION,
                    "Confirmar selección del titular",
                    "",
                    "¿Desea seleccionar a "+dtoTitular.getNombre()+" "+dtoTitular.getApellido()+"?",
                    null);

            if (result.orElse(null) == ButtonType.OK) {
                if(controllerGestionLicencia != null) controllerGestionLicencia.seleccionarTitular(dtoTitular);
                else if(controllerImprimirLicencia != null) controllerImprimirLicencia.titularBuscado(dtoTitular);
                else if(controllerModificarTitular != null) controllerModificarTitular.seleccionarTitular(dtoTitular);
                volver();
            }
        }
    }

    @FXML
    /** Vuelve a la Scene anterior si la hay, y setea su instancia en null (por el patrón singleton implementado previamente). */
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }


    /** Los 3 métodos subsiguientes permiten setearle a este controllador las instancias de otros controladores que
       necesiten los servicios que éste brinda (que le retorne un titular buscado)  */

    public void setControllerGestionLicencia(ControllerGestionLicencia controllerGestionLicencia) {
        this.controllerGestionLicencia = controllerGestionLicencia;
    }

    public void setControllerImprimirLicencia(ControllerImprimirLicencia controllerImprimirLicencia) {
        this.controllerImprimirLicencia = controllerImprimirLicencia;
    }

    public void setControllerModificarTitular(ControllerModificarTitular controllerModificarTitular) {
        this.controllerModificarTitular = controllerModificarTitular;
    }
}