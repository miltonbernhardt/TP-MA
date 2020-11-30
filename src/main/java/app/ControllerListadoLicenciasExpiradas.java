package app;


import dto.DTOLicenciaExpirada;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;
import gestor.GestorLicencia;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.net.URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerListadoLicenciasExpiradas implements Initializable {
    private static ControllerListadoLicenciasExpiradas instance = null;
    //private DTOAltaTitular dto = new DTOAltaTitular();

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

        campoDoc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[0-9A-Za-z]*")) {
                    String s = campoDoc.getText().substring(0,campoDoc.getText().length()-1);
                    campoDoc.setText(s);
                }
            }
        });
        campoApe.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("[^0-9]*")) {
                    String s = campoApe.getText().substring(0,campoApe.getText().length()-1);
                    campoApe.setText(s);
                }
            }
        });

        campoNombre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("[^0-9]*")) {
                    String s = campoNombre.getText().substring(0,campoNombre.getText().length()-1);
                    campoNombre.setText(s);
                }
            }
        });
        campoNroLicencia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    String s = campoNroLicencia.getText().substring(0,campoNroLicencia.getText().length()-1);
                    campoNroLicencia.setText(s);
                }
            }
        });

        CBClaseLicencia.setPromptText("Clase");
        CBClaseLicencia.getItems().setAll(EnumClaseLicencia.values());

        CBTipoDNI.setPromptText("Tipo Doc.");
        CBTipoDNI.getItems().setAll(EnumTipoDocumento.values());


    }

    public void buscarLicencias(){

        DTOLicenciaExpirada DTOLE = new DTOLicenciaExpirada();
        DTOLE.setRangofechas(filtrarPorRangoFecha.isSelected());
        DTOLE.setApellido(campoApe.getText());
        DTOLE.setNombre(campoNombre.getText());
        DTOLE.setClaseLicencia(CBClaseLicencia.getValue().getValue());
        DTOLE.setTipoDNI(CBTipoDNI.getValue().getValue());
        DTOLE.setDNI(campoDoc.getText());
        DTOLE.setNroLicencia(campoNroLicencia.getText());
        DTOLE.setFechaInicial(campoFechaInicial.getValue().toString());
        DTOLE.setFechaFinal(campoFechaFinal.getValue().toString());
        DTOLE.setOrdenamientoDescendente(ordenarDesc.isSelected());

        ArrayList<DTOLicenciaExpirada> resultado = GestorLicencia.obtenerListadoLicenciasExpiradas(DTOLE);



    }
}
