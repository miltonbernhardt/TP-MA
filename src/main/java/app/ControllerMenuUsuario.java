package app;

import javafx.fxml.FXML;

public class ControllerMenuUsuario {

    private static ControllerMenuUsuario instance = null;

    public static ControllerMenuUsuario get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerMenuUsuario) ControllerApp.setRoot("menuUsuario", "Menú Usuario");
        }
        return instance;
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }
}
