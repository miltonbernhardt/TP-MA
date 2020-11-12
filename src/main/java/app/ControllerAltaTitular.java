package app;

import dto.DTOAltaTitular;
import enumeration.*;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import gestor.GestorTitular;

import java.util.Optional;

public class ControllerAltaTitular{

    private static ControllerAltaTitular instance = null;
    private DTOAltaTitular dto = new DTOAltaTitular();

    public static ControllerAltaTitular get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerAltaTitular) ControllerApp.setRoot("altaTitular", "Registro titular");
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

    public void initialize() {
        campoNombre.addEventFilter(KeyEvent.ANY, handlerletters);
        campoApe.addEventFilter(KeyEvent.ANY, handlerletters);
        campoCalle.addEventFilter(KeyEvent.ANY, handlerletters);
        campoDoc.addEventFilter(KeyEvent.ANY, handlerNumbers);
        campoNumCall.addEventFilter(KeyEvent.ANY, handlerNumbers);
        CBTipoDNI.setItems(FXCollections.observableArrayList(EnumTipoDocumento.values()));
        CBGsang.setItems(FXCollections.observableArrayList(EnumGrupoSanguineo.values()));
        CBRH.setItems(FXCollections.observableArrayList(EnumFactorRH.values()));
        CBSexo.setItems(FXCollections.observableArrayList(EnumSexo.values()));
        //campoFechaNac.addEventHandler(KeyEvent.ANY,handdate);
        Bregistro.setDisable(true);
    }

    public void keyReleasedProperty(){
        Boolean isDisable = (campoNombre.getText().trim().isEmpty() || campoApe.getText().trim().isEmpty() || campoDoc.getText().isEmpty() || campoCalle.getText().trim().isEmpty()
                || campoNumCall.getText().trim().isEmpty() || CBTipoDNI.getSelectionModel().isEmpty() || CBGsang.getSelectionModel().isEmpty() || CBRH.getSelectionModel().isEmpty()
                || CBSexo.getSelectionModel().isEmpty() || campoFechaNac.getValue() == null);
        Bregistro.setDisable(isDisable);
    }

    /*
    EventHandler<KeyEvent> handdate = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent t) {
            LocalDate date = campoFechaNac.getValue();
            System.err.println("Selected date: " + date);
        }
    };
     */

    //verificar campos solo letras
    EventHandler<KeyEvent> handlerletters = new EventHandler<KeyEvent>() {
        private boolean willConsume =false;
        @Override
        public void handle(KeyEvent event){
            Object tempO= event.getSource();
            if (willConsume){
                event.consume();

            }
            //TODO hay un problema cuando introducis un caracter no permitido, y despues queres introducir uno valido, no te deja
            //si apreto un numero, por ejemplo, me deja volver a escribir
            //TODO esto es una sugerencia de algo similar que hice, capaz es más facil que ante un evento de keyUp se obtenga el string de los campos, y a ese string validarle los caracteres
            //  y si los caracteres no son válidos sacarlos de la cadena
            String temp = event.getCode().toString();
            if (!event.getCode().toString().matches("[a-zA-Z]") /*&& ( event.getCode() != KeyCode.BACK_SPACE )*/
                    && ( event.getCode() != KeyCode.SHIFT )) {
                //TODO agregar que acepte acentos y apostrofes
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

    public void onRegisterTitular(){

        Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,
                "Confirmar alta de Titular",
                "",
                "¿Desea confirmar el registro del titular?",
                null);

        if (result.get() == ButtonType.OK) {

            //TODO validar que los datos esten correctos (que los numeros esten de la longitud deseada, lo mismo con lo otro)

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

            if (GestorTitular.get().registrarTitular(dto)){
                PanelAlerta.get(EnumTipoAlerta.INFORMACION,
                        "Confirmación",
                        "",
                        "Se ha dado de alta al titular de forma correcta.",
                        null);
            }
            else {
                PanelAlerta.get(EnumTipoAlerta.ERROR,
                        "Error",
                        "",
                        "No se ha podido dar de alta Titular. El mismo ya esta registrado en la base de datos.",  //Todo agregar /n
                        null);
            }

            volver();
        }

    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }
}