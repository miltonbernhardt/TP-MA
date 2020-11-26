package app;

import dto.DTOAltaTitular;
import enumeration.EnumTipoDocumento;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ControllerListadoLicenciasExpiradas {
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
    @FXML private ComboBox<EnumTipoDocumento> CBClaseLicencia;
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
           campoFechaFinal.getEditor().setDisable(false);
        }
        else campoFechaFinal.getEditor().setDisable(true);
    }




}
