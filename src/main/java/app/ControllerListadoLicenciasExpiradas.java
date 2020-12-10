package app;


import dto.DTOLicenciaExpirada;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;
import gestor.GestorLicencia;
import herramientas.TextFielIniciador;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerListadoLicenciasExpiradas implements Initializable {
    private static ControllerListadoLicenciasExpiradas instance = null;

    public static ControllerListadoLicenciasExpiradas get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerListadoLicenciasExpiradas) ControllerApp.setRoot("listadoLicenciasExpiradas", "Listado licencias expiradas");
        }
        return instance;
    }

    @FXML private ComboBox<EnumTipoDocumento> CBTipoDNI;
    @FXML private TextField campoNombre;
    @FXML private TextField campoApe;
    @FXML private TextField campoDoc;
    @FXML private DatePicker campoFechaInicial;
    @FXML private DatePicker campoFechaFinal;
    @FXML private ComboBox<EnumClaseLicencia> CBClaseLicencia;
    @FXML private TextField campoNroLicencia;
    @FXML private CheckBox filtrarPorRangoFecha;
    @FXML private CheckBox ordenarDesc;

    @FXML private TableView<DTOLicenciaExpirada> tabla;

    @FXML private TableColumn<DTOLicenciaExpirada, String> columnaNombre;
    @FXML private TableColumn<DTOLicenciaExpirada, String> columnaApellido;
    @FXML private TableColumn<DTOLicenciaExpirada, LocalDate> columnaFechaVencimiento;
    @FXML private TableColumn<DTOLicenciaExpirada, String> columnaTipoDocumento;
    @FXML private TableColumn<DTOLicenciaExpirada, String> columnaDocumento;
    @FXML private TableColumn<DTOLicenciaExpirada, String> columnaNroLicencia;
    @FXML private TableColumn<DTOLicenciaExpirada, String> columnaClaseLicencia;

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

    @FXML
    public void onCheckRangoClick(){
        if (filtrarPorRangoFecha.isSelected()){
           campoFechaFinal.setDisable(false);
            campoFechaFinal.setValue(campoFechaInicial.getValue().plusDays(1));
        }
        else {
            campoFechaFinal.setDisable(true);
            campoFechaFinal.getEditor().setText("");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        campoFechaFinal.getEditor().setDisable(true);
        campoFechaInicial.getEditor().setDisable(true);
        campoFechaInicial.setValue(LocalDate.now());

        CBClaseLicencia.setPromptText("Clase");
        CBClaseLicencia.getItems().setAll(EnumClaseLicencia.values());

        CBTipoDNI.setPromptText("Tipo Doc.");
        CBTipoDNI.getItems().setAll(EnumTipoDocumento.values());

        iniciarTabla();
        iniciarCampos();
    }

    private void cargarTabla(List<DTOLicenciaExpirada> lista) {
        tabla.getItems().clear();
        for(DTOLicenciaExpirada dto:lista){
            tabla.getItems().add(dto);
        }
    }

    /** Añade los listener correpondientes a los campos mediante la clase TextFielIniciador, el cuál indica que valores
     del campo pueden aceptarse en dichos campos. */
    private void iniciarCampos(){
        TextFielIniciador.letrasAcento(campoNombre);
        TextFielIniciador.letrasAcento(campoNombre);
        TextFielIniciador.letrasNumero(campoDoc);
        TextFielIniciador.soloNumeros(campoNroLicencia);
    }

    public void buscarLicenciasExpiradas(){

        DTOLicenciaExpirada DTOLE = new DTOLicenciaExpirada();

        DTOLE.setRangofechas(filtrarPorRangoFecha.isSelected());
        DTOLE.setApellido(campoApe.getText());
        DTOLE.setNombre(campoNombre.getText());
        if(CBClaseLicencia.getSelectionModel().getSelectedItem() != null) DTOLE.setClaseLicencia(CBClaseLicencia.getValue());
        else DTOLE.setClaseLicencia(null);
        if (CBTipoDNI.getSelectionModel().getSelectedItem() != null) DTOLE.setTipoDNI(CBTipoDNI.getValue());
        else DTOLE.setTipoDNI(null);
        DTOLE.setDNI(campoDoc.getText());
        if (campoNroLicencia.getText().equals("")) DTOLE.setNroLicencia(0);
        else DTOLE.setNroLicencia(Integer.valueOf(campoNroLicencia.getText()));
        DTOLE.setFechaInicial(campoFechaInicial.getValue());
        DTOLE.setFechaFinal(campoFechaFinal.getValue());
        DTOLE.setOrdenamientoDescendente(ordenarDesc.isSelected());

        List<DTOLicenciaExpirada> resultado = GestorLicencia.obtenerListadoLicenciasExpiradas(DTOLE);

        this.cargarTabla(resultado);

    }

    private void iniciarTabla() {
        tabla.setPlaceholder(new Label("No hay licencias que mostrar."));

        columnaNroLicencia.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaFechaVencimiento.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        columnaTipoDocumento.setCellValueFactory(new PropertyValueFactory<>("tipoDNI"));
        columnaDocumento.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        columnaClaseLicencia.setCellValueFactory(new PropertyValueFactory<>("claseLicencia"));

    }

}
