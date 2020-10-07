package app;

public class ControllerEmitirLicencia {

    private static ControllerEmitirLicencia instance = null;

    public ControllerEmitirLicencia() { }

    public static ControllerEmitirLicencia get() {
        if (instance == null){
            //ControllerApp.setViewAnterior();
            instance = (ControllerEmitirLicencia) ControllerApp.setRoot("CU02View01", "Buscar productos");
        }
        return instance;
    }

    /*
        Atributos y funciones que se uSen en la vista, colocarle previamente un "@FXML".
    */

}
