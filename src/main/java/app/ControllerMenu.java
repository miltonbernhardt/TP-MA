package app;

import javafx.fxml.FXML;

public class ControllerMenu {

    @FXML
    private void darAltaTitular() {
        //ControllerAltaTitular.get();
    }

    @FXML
    private void emitirLicencia() {
        ControllerEmitirLicencia.get();
    }

    @FXML
    private void salir() {
        ControllerApp.salir();
    }
}
