package test;

import enumeration.EnumClaseLicencia;
import gestor.GestorLicencia;
import gestor.GestorTitular;
import javafx.application.Application;
import javafx.stage.Stage;

public class test1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        EnumClaseLicencia c = EnumClaseLicencia.CLASE_F;
            switch(c)
            {
                case CLASE_A:
                    System.out.println("Clase A");
                    break;
                case CLASE_B:
                    break;
                case CLASE_C:
                    break;
                case CLASE_D:
                    break;
                case CLASE_E:
                    break;
                case CLASE_F:
                    break;
                case CLASE_G:
                    break;
                default :
                    // Declaraciones
            }
        }
    }

