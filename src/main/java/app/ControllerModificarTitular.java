package app;

import dto.DTOGestionTitular;
import dto.DTOModificarTitular;
import enumeration.*;
import gestor.GestorTitular;
import herramientas.AlertPanel;
import herramientas.TextFielIniciador;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

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
    @FXML private ComboBox<EnumSexo> comboBoxSexo;
    @FXML private RadioButton radioButtonDonante;
    @FXML private TextField campoDireccionNumero;

    public void initialize() {
        TextFielIniciador.letrasAcento(campoNombre);
        TextFielIniciador.letrasAcento(campoApellido);
        TextFielIniciador.letrasAcento(campoDireccion);
        TextFielIniciador.soloNumeros(campoDireccionNumero);
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

    public void modificarTitular() {
        if(dto != null){
            if(campoNombre.getText().isEmpty() || campoApellido.getText().isEmpty() || campoDireccion.getText().isEmpty() || campoDireccionNumero.getText().isEmpty()){
                AlertPanel.get(EnumTipoAlerta.ERROR,"Faltan Campos","","No pueden existir campos vacios del titular, vuelva a completar los campos faltantes.",null);
            } else{
                dto.setNombre(campoNombre.getText());
                dto.setApellido(campoApellido.getText());
                dto.setCalle(campoDireccion.getText());
                dto.setNumeroCalle(Integer.parseInt(campoDireccionNumero.getText()));
                dto.setSexo(comboBoxSexo.getSelectionModel().getSelectedItem());
                dto.setDonante(radioButtonDonante.isSelected());
                //todo crear metodo para realizar un update en la base de datos con el dto, mostrar una ventana consultando si esta seguro de la modificacion
                // y si acepta que, muestre otra ventana diciendo si se realizo correctamente o no y que vuelva para atras.
                Optional<ButtonType> result = AlertPanel.get(EnumTipoAlerta.CONFIRMACION,
                        "Confirmar selección del titular",
                        "",
                        "¿Está seguro de actualizar esta información?",
                        null);
                if (result.orElse(null) == ButtonType.OK) {
                    try {
                        GestorTitular.get().modificarTitular(dto);
                        AlertPanel.get(EnumTipoAlerta.INFORMACION, "Modificación", "", "Se pudo realizar la modificacion correctamente", null);
                        volver();
                    } catch (Exception e){
                        AlertPanel.get(EnumTipoAlerta.EXCEPCION, "Error", "", "No se pudo realizar la modificacion correctamente", e);
                    }
                }
            }
        }else {
            AlertPanel.get(EnumTipoAlerta.ERROR,"Seleccione un titular","","Debe seleccionar un titular para modificar, en caso de querer dar de alta un nuevo titular, vuelva a la pantalla anterior.",null);
        }
    }
}
