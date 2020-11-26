package app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {

    @FXML private Button BRegTitular;
    @FXML private Button BEmitirLicencia;

    @FXML private AnchorPane panReq;
    @FXML private AnchorPane panUsuarios;
    @FXML private AnchorPane panValor;
    @FXML private AnchorPane panVigen;
    @FXML private ImageView arrowUser;
    @FXML private ImageView arrowVal;
    @FXML private ImageView arrowReq;
    @FXML private ImageView arrowVig;
    @FXML private ImageView santaFe;
    @FXML private AnchorPane panRecuerda;
    @FXML private ImageView arrowRecuerda;

    public void onRecuerdaButtonClicked(MouseEvent event){
        if (panRecuerda.isVisible()){
            panRecuerda.setVisible(false);
            santaFe.setVisible(true);
            arrowRecuerda.setVisible(false);
            return;

        }
        panRecuerda.setVisible(true);
        arrowRecuerda.setVisible(true);



        santaFe.setVisible(false);
        arrowUser.setVisible(false);
        arrowVal.setVisible(false);
        arrowReq.setVisible(false);
        panUsuarios.setVisible(false);
        panReq.setVisible(false);
        panValor.setVisible(false);
        panVigen.setVisible(false);
        arrowVig.setVisible(false);
        //AcademPanel.setVisible(false);
    }

    public void onVigButtonClicked(MouseEvent event){
        if (panVigen.isVisible()){
            panVigen.setVisible(false);
            santaFe.setVisible(true);
            arrowVig.setVisible(false);
            return;

        }
        panRecuerda.setVisible(false);
        arrowRecuerda.setVisible(false);
        panVigen.setVisible(true);
        arrowVig.setVisible(true);
        santaFe.setVisible(false);
        arrowUser.setVisible(false);
        arrowVal.setVisible(false);
        arrowReq.setVisible(false);
        panUsuarios.setVisible(false);
        panReq.setVisible(false);
        panValor.setVisible(false);
        //AcademPanel.setVisible(false);
    }
    public void onValorButtonClicked(MouseEvent event){
        if (panValor.isVisible()){
            panValor.setVisible(false);
            arrowVal.setVisible(false);
            santaFe.setVisible(true);
            return;

        }
        panValor.setVisible(true);
        arrowVal.setVisible(true);
        panRecuerda.setVisible(false);
        arrowRecuerda.setVisible(false);
        santaFe.setVisible(false);
        arrowUser.setVisible(false);
        panUsuarios.setVisible(false);
        panReq.setVisible(false);
        panVigen.setVisible(false);
        arrowVig.setVisible(false);
        arrowReq.setVisible(false);
        //AcademPanel.setVisible(false);
    }
    public void onReqButtonClicked(MouseEvent event){
        if (panReq.isVisible()){
            panReq.setVisible(false);
            arrowReq.setVisible(false);
            santaFe.setVisible(true);
            return;

        }
        panReq.setVisible(true);
        arrowReq.setVisible(true);
        santaFe.setVisible(false);
        arrowVal.setVisible(false);
        arrowUser.setVisible(false);
        panRecuerda.setVisible(false);
        arrowRecuerda.setVisible(false);
        panUsuarios.setVisible(false);
        panValor.setVisible(false);
        panVigen.setVisible(false);
        arrowVig.setVisible(false);
        //AcademPanel.setVisible(false);
    }

    public void onUserButtonClicked(MouseEvent event){
        if (panUsuarios.isVisible()){
            panUsuarios.setVisible(false);
           arrowUser.setVisible(false);
            santaFe.setVisible(true);
            return;
        }
        panUsuarios.setVisible(true);
        arrowUser.setVisible(true);
        panVigen.setVisible(false);
        santaFe.setVisible(false);
        arrowReq.setVisible(false);
        arrowVal.setVisible(false);
        arrowVig.setVisible(false);
        panReq.setVisible(false);
        panValor.setVisible(false);
        panRecuerda.setVisible(false);
        arrowRecuerda.setVisible(false);


    }



    @FXML
    private void MenuLicencia(){ ControllerMenuLicencia.get();}

    @FXML
    private void MenuTitular(){ ControllerMenuTitular.get();}
    @FXML
    private void MenuUsuario(){ ControllerMenuUsuario.get();}




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
