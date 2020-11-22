package app;

import dto.DTOBuscarTitular;
import dto.DTOEmitirLicencia;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class ControllerBuscarTitular {
    private static ControllerBuscarTitular instance = null;
    private DTOEmitirLicencia dto = null;

    public static ControllerBuscarTitular get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerBuscarTitular) ControllerApp.setRoot("buscarTitular", "Buscar usuario");
        }
        return instance;
    }

    @FXML private TableView<DTOBuscarTitular> tabla;

    @FXML private TableColumn<DTOBuscarTitular, String> columnaNombre;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaApellido;
    @FXML private TableColumn<DTOBuscarTitular, LocalDate> columnaFechaNac;
    @FXML private TableColumn<DTOBuscarTitular, String> columnatipoDocumento;
    @FXML private TableColumn<DTOBuscarTitular, String> columnaDocumento;
    @FXML private TableColumn<DTOBuscarTitular, Integer> columnaLicencias;

    @FXML
    private void buscarCliente(){

    }

    @FXML
    private void seleccionarTitular(){

    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }
}
