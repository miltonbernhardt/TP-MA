package app;

import com.jfoenix.controls.JFXComboBox;
import enumeration.EnumTipoDocumento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAltaTitular implements Initializable{

    @FXML private JFXComboBox CBTipoDNI;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CBTipoDNI.setItems(FXCollections.observableArrayList(EnumTipoDocumento.values()));
    }


}
