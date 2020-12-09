package app;

import dto.DTOAltaTitular;
import enumeration.*;
import herramientas.DatePickerIniciador;
import herramientas.AlertPanel;
import herramientas.TextFielIniciador;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import gestor.GestorTitular;

import java.util.Optional;

public class ControllerAltaTitular{
    private static ControllerAltaTitular instance = null;
    private DTOAltaTitular dto;

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
        DatePickerIniciador.iniciarDatePicker(campoFechaNac);
        listenerTextField();

        CBTipoDNI.setItems(FXCollections.observableArrayList(EnumTipoDocumento.values()));
        CBGsang.setItems(FXCollections.observableArrayList(EnumGrupoSanguineo.values()));
        CBRH.setItems(FXCollections.observableArrayList(EnumFactorRH.values()));
        CBSexo.setItems(FXCollections.observableArrayList(EnumSexo.values()));
        Bregistro.setDisable(true);
    }

    public void keyReleasedProperty(){
        boolean isDisable = (campoNombre.getText().trim().isEmpty() || campoApe.getText().trim().isEmpty() || campoDoc.getText().isEmpty() || campoCalle.getText().trim().isEmpty()
                || campoNumCall.getText().trim().isEmpty() || CBTipoDNI.getSelectionModel().isEmpty() || CBGsang.getSelectionModel().isEmpty() || CBRH.getSelectionModel().isEmpty()
                || CBSexo.getSelectionModel().isEmpty() || campoFechaNac.getValue() == null);
        Bregistro.setDisable(isDisable);
    }

    private void listenerTextField(){
        TextFielIniciador.letrasAcento(campoNombre);
        TextFielIniciador.letrasAcento(campoApe);
        TextFielIniciador.letrasAcento(campoCalle);
        TextFielIniciador.letrasNumero(campoDoc);
        TextFielIniciador.soloNumeros(campoNumCall);
    }

    /**
    onRegisterTitular() obtiene los datos ingresados por pantalla y llama a
    GestorTitular.get().registrarTitular(dto) para registrarlo en la DB.
    */
    public void onRegisterTitular(){
            Optional<ButtonType> result = AlertPanel.get(EnumTipoAlerta.CONFIRMACION,
                    "Confirmar alta de Titular",
                    "",
                    "¿Desea confirmar el registro del titular?",
                    null);

            if (result.orElse(null) == ButtonType.OK) {

                if (GestorTitular.get().getEdad(campoFechaNac.getValue()) < 17 || GestorTitular.get().getEdad(campoFechaNac.getValue()) > 65) {
                    AlertPanel.get(EnumTipoAlerta.INFORMACION, "Rango de edad",
                            "",
                            "Su titular debe poser al menos de 17 años y como máximo 65 años", null);
                } else {
                    if (campoDoc.getLength() != 8) {
                        AlertPanel.get(EnumTipoAlerta.INFORMACION, "Documento",
                                "",
                                "El numero de Documento es inválido", null);
                    } else {
                        if (!GestorTitular.get().titularExistente(campoDoc.getText(), CBTipoDNI.getValue())) {
                            dto = new DTOAltaTitular();
                            dto.setNombre(campoNombre.getText().substring(0, 1).toUpperCase() + campoNombre.getText().substring(1).toLowerCase());
                            dto.setApellido(campoApe.getText().substring(0, 1).toUpperCase() + campoApe.getText().substring(1).toLowerCase());
                            dto.setCalle(campoCalle.getText());
                            dto.setDNI(campoDoc.getText());
                            dto.setDonanteOrganos(RBdonante.isSelected()); // 1==sí , 0==no
                            dto.setFactorRH(CBRH.getSelectionModel().getSelectedItem());
                            dto.setTipoDNI(CBTipoDNI.getSelectionModel().getSelectedItem());
                            dto.setSexo(CBSexo.getSelectionModel().getSelectedItem());
                            dto.setGrupoSanguineo(CBGsang.getSelectionModel().getSelectedItem());
                            dto.setNumeroCalle(Integer.parseInt(campoNumCall.getText()));
                            dto.setFechaNacimiento(campoFechaNac.getValue());

                            if (GestorTitular.get().registrarTitular(dto)) {
                                AlertPanel.get(EnumTipoAlerta.INFORMACION,
                                        "Confirmación",
                                        "",
                                        "Se ha dado de alta al titular de forma correcta.",
                                        null);
                            } else {
                                AlertPanel.get(EnumTipoAlerta.ERROR,
                                        "Error",
                                        "",
                                        "No se ha podido dar de alta Titular",
                                        null);
                            }
                            volver();
                        } else {
                            AlertPanel.get(EnumTipoAlerta.ERROR,
                                    "Error",
                                    "",
                                    "El titular ya se encuentra registrado",
                                    null);
                        }
                    }

                }
            }


    }


    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }
}