package app;

import enumeration.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import java.io.IOException;
import hibernate.HibernateUtil;

public class ControllerApp extends Application {
    private static Scene scene;
    private static Stage stage;
    private static FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage)  {
        HibernateUtil.apagarLog(true);
        HibernateUtil.getSessionFactory();
        scene = new Scene(loadFXML("emitirLicencia"));
        primaryStage.setTitle("Menú");
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;

        /*
        //----------------------------------------- para probar los enums --------------------------------------

        System.out.println(EnumTipoDocumento.DNI);
        System.out.println(EnumSexo.values());
        System.out.println(EnumTipoAlerta.values());
        System.out.println(EnumTipoDocumento.PASAPORTE.equals(EnumTipoDocumento.getEnum("Pasaporte")));
        */


        //----------------------------------------- para probar la base de datos --------------------------------------
/*
        Titular t1 = new Titular(EnumTipoDocumento.DNI, "40000000", "López", "Juan", LocalDate.of(2000, Month.MARCH, 21),
                EnumGrupoSanguineo.GRUPO_0, EnumFactorRH.FACTOR_POSITIVO, true, EnumSexo.MASCULINO);

        Titular t2 = new Titular(EnumTipoDocumento.DNI, "30000000", "Martinez", "Giulana", LocalDate.of(1989, Month.AUGUST, 12),
                EnumGrupoSanguineo.GRUPO_AB, EnumFactorRH.FACTOR_NEGATIVO, false, EnumSexo.FEMENINO);
        Licencia l1 = new Licencia(t2, EnumClaseLicencia.CLASE_A,  LocalDate.of(2020, Month.FEBRUARY, 01),  LocalDate.of(2024, Month.AUGUST, 12));
        t2.getLicencias().add(l1);


        Titular t3 = new Titular(EnumTipoDocumento.DNI, "33333333", "Lucas", "Luciana", LocalDate.of(1995, Month.SEPTEMBER, 11),
                EnumGrupoSanguineo.GRUPO_B, EnumFactorRH.FACTOR_POSITIVO, true, EnumSexo.FEMENINO);
        Licencia l2 = new Licencia(t3, EnumClaseLicencia.CLASE_B,  LocalDate.of(2019, Month.MAY, 12),  LocalDate.of(2023, Month.SEPTEMBER, 11));
        t3.getLicencias().add(l2);

        Titular t4 = new Titular(EnumTipoDocumento.DNI, "37812389", "Eros", "Franco", LocalDate.of(1996, Month.DECEMBER, 03),
                EnumGrupoSanguineo.GRUPO_A, EnumFactorRH.FACTOR_POSITIVO, true, EnumSexo.MASCULINO);
        Licencia l3 = new Licencia(t4, EnumClaseLicencia.CLASE_C,  LocalDate.of(2019, Month.NOVEMBER, 19),  LocalDate.of(2023, Month.DECEMBER, 03));
        t4.getLicencias().add(l3);



        DAO.get().save(t1);
        DAO.get().save(t2);
        DAO.get().save(t3);
        DAO.get().save(t4);
*/
    }

    /**
     * Carga el archivo 'fxml' (la vista) en el parent principal.
     * Se le pasa solo el nombre, sin el '.fxml'.
     */
    private static Parent loadFXML(String fxml)  {
        fxmlLoader = new FXMLLoader(ControllerApp.class.getResource(fxml + ".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException | RuntimeException  e) {
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null, null,"Ocurrió un error al cargar la view \""+fxml+"\".",e);
            e.printStackTrace();
            return null;
        }
    }

    static void salir() {
        Platform.exit();
        System.exit(0);
        HibernateUtil.closeBaseDatos();
    }

    /**
     * vistaFxml: indicar el nombre del archivo FXML de la vista.
     * tituloVentana: indica el titulo que va a poseer la ventana de la vista.
     * @param vistaFxml vista a setear en el root
     * @param tituloVentana titulo de la vista a setear
     */
    static Object setRoot(String vistaFxml, String tituloVentana) {
        scene.setRoot(loadFXML(vistaFxml));
        scene.getRoot().requestFocus();
        stage.setTitle(tituloVentana);
        return fxmlLoader.getController();
    }

    /*
    -------------------------- PARA LA NAVEGACIÓN A FUTURO -------------------------------

    private static List<Parent> scenesAnteriores = new ArrayList<Parent>();
    private static List<String> titulosAnteriores = new ArrayList<String>();

    static void setViewAnterior() {
        scenesAnteriores.add(stage.getScene().getRoot());
        titulosAnteriores.add(stage.getTitle());
    }

    static void getViewAnterior() {
        Integer index = scenesAnteriores.size()-1;
        Parent p = null;
        String t = null;
        try {
            p = scenesAnteriores.get(index);
            t = titulosAnteriores.get(index);
            scene.setRoot(p);
            scene.getRoot().requestFocus();
            stage.setTitle(t);
        }catch(Exception e) {
            scene.setRoot(loadFXML("menu"));
            scene.getRoot().requestFocus();
            stage.setTitle("AlChi: Menú");
        }
        scenesAnteriores.remove(p);
        titulosAnteriores.remove(t);
    }

    static Object getControllerActual() {
        return fxmlLoader.getController();
    }
    */

    /**
     * Setea a un nodo con el style de error.
     * @param nodo elemento de la interfaz a setearle el style de error
     */
    static void setError(Control nodo) {
        nodo.getStylesheets().clear();
        nodo.getStylesheets().add("app/error.css");
    }

    /**
     * Vuelve un nodo a el style que le corresponde.
     * @param nodo elemento de la interfaz a setearle el style
     */
    static void setValido(Control nodo) {
        nodo.getStylesheets().clear();
        nodo.getStylesheets().add("app/styles.css");
    }

    public static void setStyle(@SuppressWarnings("exports") DialogPane dialogPane) {
        dialogPane.getStylesheets().clear();
        dialogPane.getStylesheets().add("app/styles.css");
    }
}

