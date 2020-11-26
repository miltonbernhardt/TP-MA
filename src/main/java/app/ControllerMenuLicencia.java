package app;

import dto.DTOAltaTitular;
import javafx.fxml.FXML;

public class ControllerMenuLicencia {

    private static ControllerMenuLicencia instance = null;


    public static ControllerMenuLicencia get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerMenuLicencia) ControllerApp.setRoot("menuLicencia", "Menu Licencia");
        }
        return instance;
    }
    @FXML
    private void emitirLicencia() {
        ControllerEmitirLicencia.get();
    }

    @FXML
    private void listadoLicenciasExpitradas() {
        ControllerListadoLicenciasExpiradas.get();
    }



    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }
}
