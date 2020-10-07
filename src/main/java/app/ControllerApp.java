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
import java.text.ParseException;

import hibernate.HibernateUtil;

public class ControllerApp extends Application {
    private static Scene scene;
    private static Stage stage;
    private static FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ParseException {
        HibernateUtil.apagarLog(true);
        HibernateUtil.getSessionFactory();
        scene = new Scene(loadFXML("app"));
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

        /*
        //----------------------------------------- para probar la base de datos --------------------------------------

        Titular t1 = new Titular(EnumTipoDNI.DNI, "40000000", "López", "Juan", new SimpleDateFormat("dd/MM/yyyy").parse("21/03/2000"),
                EnumGrupoSanguineo.GRUPO_0, EnumFactorRH.FACTOR_POSITIVO, true, EnumSexo.MASCULINO);

        Titular t2 = new Titular(EnumTipoDNI.DNI, "30000000", "Martinez", "Giulana", new SimpleDateFormat("dd/MM/yyyy").parse("12/08/1989"),
                EnumGrupoSanguineo.GRUPO_AB, EnumFactorRH.FACTOR_NEGATIVO, false, EnumSexo.FEMENINO);

        Licencia l1 = new Licencia(t1, EnumClaseLicencia.CLASE_C,  new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2020"),  new SimpleDateFormat("dd/MM/yyyy").parse("21/03/2000"));
        Licencia l2 = new Licencia(t2, EnumClaseLicencia.CLASE_A,  new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2020"),  new SimpleDateFormat("dd/MM/yyyy").parse("12/08/2022"));

        t1.getLicencias().add(l1);
        t2.getLicencias().add(l2);

        DAO.get().save(t1);
        DAO.get().save(t2);
        */

    }

    /**
     * Carga el archivo 'fxml' (la vista) en el parent principal.
     * Se le pasa solo el nombre, sin el '.fxml'.
     * @param fxml
     * @return
     */
    private static Parent loadFXML(String fxml)  {
        fxmlLoader = new FXMLLoader(ControllerApp.class.getResource(fxml + ".fxml"));
        try {
            Parent p = fxmlLoader.load();
            return p;
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
     * @param vistaFxml
     * @param tituloVentana
     * @return
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
     * @param nodo
     */
    static void setError(Control nodo) {
        nodo.getStylesheets().clear();
        nodo.getStylesheets().add("app/error.css");
    }

    /**
     * Vuelve un nodo a el style que le corresponde.
     * @param nodo
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

