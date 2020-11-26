package app;

import javafx.fxml.FXML;

public class ControllerMenuTitular {

    private static ControllerMenuTitular instance = null;


    public static ControllerMenuTitular get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerMenuTitular) ControllerApp.setRoot("menuTitular", "Menu Titular");
        }
        return instance;
    }

    @FXML
    private void darAltaTitular() {
        ControllerAltaTitular.get();
    }

    @FXML
    private void buscarTitular() {
        ControllerBuscarTitular.get();
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

}
