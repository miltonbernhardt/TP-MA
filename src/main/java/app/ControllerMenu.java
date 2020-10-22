package app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {

    @FXML private Button BRegTitular;
    @FXML private Button BEmitirLicencia;
    @FXML private ImageView logoini;
    @FXML private ImageView imagenmenu;

    @FXML
    private void darAltaTitular() {
        ControllerAltaTitular.get();
    }

    @FXML
    private void emitirLicencia() {
        ControllerEmitirLicencia.get();
    }

    @FXML
    private void salir() {
        ControllerApp.salir();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void onExitButtonClicked(MouseEvent event){

        Platform.exit();
        System.exit(0);
    }
}
