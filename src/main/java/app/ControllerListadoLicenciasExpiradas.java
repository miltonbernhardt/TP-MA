package app;


import dto.DTOLicenciaExpirada;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;
import gestor.GestorLicencia;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        campoNombre.addEventFilter(KeyEvent.ANY, handlerletters);
        campoApe.addEventFilter(KeyEvent.ANY, handlerletters);
        campoNroLicencia.addEventFilter(KeyEvent.ANY, handlerNumbers);
        campoDoc.addEventFilter(KeyEvent.ANY, handlerNumbers);

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
        if(CBClaseLicencia.getValue() != null) DTOLE.setClaseLicencia(CBClaseLicencia.getValue().getValue());
        else DTOLE.setClaseLicencia("");
        if (CBTipoDNI.getValue() != null) DTOLE.setTipoDNI(CBTipoDNI.getValue().getValue());
        else DTOLE.setTipoDNI("");

        DTOLE.setDNI(campoDoc.getText());
        DTOLE.setNroLicencia(campoNroLicencia.getText());
        DTOLE.setFechaInicial(campoFechaInicial.getValue().toString());
        DTOLE.setFechaFinal(campoFechaFinal.getValue().toString());
        //DTOLE.setOrdenamientoDescendente(ordenarDesc.isSelected());

        List<DTOLicenciaExpirada> resultado = GestorLicencia.obtenerListadoLicenciasExpiradas(DTOLE);



    }
    //verificar campos solo letras, consume las entradas no validas
    EventHandler<KeyEvent> handlerletters = new EventHandler<KeyEvent>() {
        private boolean willConsume =false;
        @Override
        public void handle(KeyEvent event){
            Object temp0= event.getSource();
            //una vez que se consume se debe volver a poner en falso, sino seguira consumiendo hasta que
            //se ingrese un caracter no valido
            if (willConsume){
                event.consume();
                willConsume = false;
            }
            String temp = event.getCode().toString();
            if (!event.getCode().toString().matches("[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]")&&(event.getCode()!= KeyCode.SPACE)
                    && ( event.getCode() != KeyCode.SHIFT)) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED){
                    willConsume = true;
                }  else if (event.getEventType() == KeyEvent.KEY_RELEASED)  {
                    willConsume = false;
                }

            }
        }
    };

    //verificar campos solo numeros
    EventHandler<KeyEvent> handlerNumbers = new EventHandler<KeyEvent>() {
        private boolean willConsume = false;
        private int maxLength = 10;

        @Override
        public void handle(KeyEvent event) {
            TextField temp = (TextField) event.getSource();
            if (willConsume) {
                event.consume();

            }
            if (!event.getText().matches("[0-9]") && event.getCode() != KeyCode.BACK_SPACE) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    willConsume = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                    willConsume = false;
                }
            }
            if (temp.getText().length() > maxLength - 1) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    willConsume = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                    willConsume = false;
                }
            }
        }
    };


}
