package app;

import dto.DTOGestionTitular;
import dto.DTOModificarTitular;
import enumeration.*;
import gestor.GestorTitular;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerModificarTitular{

    private static ControllerModificarTitular instance = null;
    private DTOModificarTitular dto = null;
    public static ControllerModificarTitular get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerModificarTitular) ControllerApp.setRoot("modificarTitular", "Modificar Datos Titular");
        }
        return instance;
    }

    @FXML private TextField campoNombre;
    @FXML private TextField campoApellido;
    @FXML private TextField campoDireccion;
    @FXML private ComboBox comboBoxSexo;
    @FXML private RadioButton radioButtonDonante;
    @FXML private TextField campoDireccionNumero;
    @FXML private Button Modificar;

    public void initialize() {
        campoNombre.addEventFilter(KeyEvent.ANY, handlerletters);
        campoApellido.addEventFilter(KeyEvent.ANY, handlerletters);
        campoDireccion.addEventFilter(KeyEvent.ANY, handlerletters);
        campoDireccionNumero.addEventFilter(KeyEvent.ANY, handlerNumbers);
    }


    @FXML
    public void buscarTitular(){
        ControllerBuscarTitular.get().setControllerModificarTitular(this);
    }


    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

    public void seleccionarTitular(DTOGestionTitular dtoTitular) {
        this.dto = new DTOModificarTitular();
        dto.setId(dtoTitular.getIdTitular());
        dto.setNombre(dtoTitular.getNombre());
        dto.setApellido(dtoTitular.getApellido());
        dto.setCalle(dtoTitular.getCalle());
        dto.setNumeroCalle(dtoTitular.getNroCalle());
        dto.setDonante(dtoTitular.getDonante());
        dto.setSexo(dtoTitular.getSexo());

        System.out.println(dto.getId());
        campoNombre.setText(dto.getNombre());
        campoApellido.setText(dto.getApellido());
        campoDireccion.setText(dto.getCalle());
        campoDireccionNumero.setText(Integer.toString(dto.getNumeroCalle()));
        comboBoxSexo.getItems().clear();

        comboBoxSexo.setPromptText("Sexo..");
        comboBoxSexo.getItems().addAll(EnumSexo.values());
        comboBoxSexo.setValue(dto.getSexo());
        radioButtonDonante.setSelected(dto.getDonante());
    }

    public void modificarTitular() throws Exception {
        if(dto != null){
            if(campoNombre.getText().isEmpty() || campoApellido.getText().isEmpty() || campoDireccion.getText().isEmpty() || campoDireccionNumero.getText().isEmpty()){
                PanelAlerta.get(EnumTipoAlerta.ERROR,"Faltan Campos","","No pueden existir campos vacios del titular, vuelva a completar los campos faltantes.",null);
            } else{
                dto.setNombre(campoNombre.getText());
                dto.setApellido(campoApellido.getText());
                dto.setCalle(campoDireccion.getText());
                dto.setNumeroCalle(Integer.parseInt(campoDireccionNumero.getText()));
                dto.setSexo((EnumSexo) comboBoxSexo.getSelectionModel().getSelectedItem());
                dto.setDonante(radioButtonDonante.isSelected());
                //todo crear metodo para realizar un update en la base de datos con el dto, mostrar una ventana consultando si esta seguro de la modificacion
                // y si acepta que, muestre otra ventana diciendo si se realizo correctamente o no y que vuelva para atras.
                Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,
                        "Confirmar selección del titular",
                        "",
                        "¿Está seguro de actualizar esta información?",
                        null);
                if (result.orElse(null) == ButtonType.OK) {
                    try {
                        GestorTitular.ModificarTitular(dto);
                        PanelAlerta.get(EnumTipoAlerta.INFORMACION, "Modificación", "", "Se pudo realizar la modificacion correctamente", null);
                        volver();
                    } catch (Exception e){
                        PanelAlerta.get(EnumTipoAlerta.EXCEPCION, "Error", "", "No see pudo realizar la modificacion correctamente", e);
                    }
                }
            }
        }else {
            PanelAlerta.get(EnumTipoAlerta.ERROR,"Seleccione un titular","","Debe seleccionar un titular para modificar, en caso de querer dar de alta un nuevo titular, vuelva a la pantalla anterior.",null);
        }
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
