package app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dto.DTOAltaTitular;
import enumeration.EnumFactorRH;
import enumeration.EnumGrupoSanguineo;
import enumeration.EnumSexo;
import enumeration.EnumTipoDocumento;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import gestor.GestorTitular;
//import DTO.DTOAltaTitular;
import sun.awt.SubRegionShowable;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerAltaTitular implements Initializable{
    private static ControllerAltaTitular instance = null;
    private DTOAltaTitular dto = new DTOAltaTitular();


    public static ControllerAltaTitular get() {

        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerAltaTitular) ControllerApp.setRoot("altaTitular", "Alta titular");
        }
        return instance;
    }
    @FXML private ComboBox<EnumTipoDocumento> CBTipoDNI;
    @FXML private TextField campoNombre;
    @FXML private TextField campoApe;
    @FXML private TextField campoDoc;
    @FXML private TextField campoCalle;
    @FXML private TextField campoNumCall;
    @FXML private ComboBox<EnumGrupoSanguineo> CBGsang;
    @FXML private ComboBox<EnumFactorRH> CBRH;
    @FXML private ComboBox<EnumSexo> CBSexo;
    @FXML private DatePicker campoFechaNac;
    @FXML private RadioButton RBdonante;
    @FXML private Button Bregistro;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        campoNombre.addEventFilter(KeyEvent.ANY, handlerletters);
        campoApe.addEventFilter(KeyEvent.ANY, handlerletters);
        campoCalle.addEventFilter(KeyEvent.ANY, handlerletters);
        campoDoc.addEventFilter(KeyEvent.ANY, handlerNumbers);
        campoNumCall.addEventFilter(KeyEvent.ANY, handlerNumbers);
        CBTipoDNI.setItems(FXCollections.observableArrayList(EnumTipoDocumento.values()));
        CBGsang.setItems(FXCollections.observableArrayList(EnumGrupoSanguineo.values()));
        CBRH.setItems(FXCollections.observableArrayList(EnumFactorRH.values()));
        CBSexo.setItems(FXCollections.observableArrayList(EnumSexo.values()));
        campoFechaNac.addEventHandler(KeyEvent.ANY,handdate);
        Bregistro.setDisable(true);




    }

    public void keyReleasedProperty(){

        Boolean isDisable = (campoNombre.getText().trim().isEmpty() || campoApe.getText().trim().isEmpty() || campoDoc.getText().isEmpty() || campoCalle.getText().trim().isEmpty()
                || campoNumCall.getText().trim().isEmpty() || CBTipoDNI.getSelectionModel().isEmpty() || CBGsang.getSelectionModel().isEmpty() || CBRH.getSelectionModel().isEmpty()
                || CBSexo.getSelectionModel().isEmpty() || campoFechaNac.getValue() == null);
        Bregistro.setDisable(isDisable);
    }

    EventHandler<KeyEvent> handdate = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent t) {
            LocalDate date = campoFechaNac.getValue();
            System.err.println("Selected date: " + date);
        }
    };


    //verificar campos solo letras
    EventHandler<KeyEvent> handlerletters = new EventHandler<KeyEvent>() {
        private boolean willConsume =false;
        @Override
        public void handle(KeyEvent event){
            Object tempO= event.getSource();
            if (willConsume){
                event.consume();

            }
            String temp = event.getCode().toString();
            if (!event.getCode().toString().matches("[a-zA-Z]") && event.getCode() != KeyCode.BACK_SPACE
                    && event.getCode() != KeyCode.SHIFT) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED){
                    willConsume = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED)  {
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



    public void onRegisterTitular(MouseEvent event){


       dto.setNombre(campoNombre.getText());
       dto.setApellido(campoApe.getText());
       dto.setCalle(campoCalle.getText());
       dto.setDNI(campoDoc.getText());
       dto.setDonanteOrganos(RBdonante.isSelected());
       dto.setFactorRH(CBRH.getSelectionModel().getSelectedItem());
       dto.setTipoDNI(CBTipoDNI.getSelectionModel().getSelectedItem());
       dto.setSexo(CBSexo.getSelectionModel().getSelectedItem());
       dto.setGrupoSanguineo(CBGsang.getSelectionModel().getSelectedItem());
       dto.setNumeroCalle(Integer.parseInt(campoNumCall.getText()));
       dto.setFechaNacimiento(campoFechaNac.getValue());


        GestorTitular.get().registrarTitular(dto);

        System.out.println("dto: "+ dto.getApellido());
    }


    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

    public void onExitButtonClicked(MouseEvent event){

        Platform.exit();
        System.exit(0);
    }

}