package app;

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
        ControllerGestionLicencia.get(true);
    }

    @FXML
    private void renovarLicencia() {
        ControllerGestionLicencia.get(false);
    }

    @FXML
    private void imprimirLicencia() {
        ControllerImprimirLicencia.get();
    }

    @FXML
    private void buscarLicenciasExpiradas() {
        ControllerListadoLicenciasExpiradas.get();
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }
}
