package app;

import enumeration.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import database.HibernateUtil;

public class ControllerApp extends Application {
    private static Scene scene;
    private static Stage stage;
    private static FXMLLoader fxmlLoader;

    private static final List<Parent> scenesAnteriores = new ArrayList<>();
    private static final List<String> titulosAnteriores = new ArrayList<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage)  {
        HibernateUtil.apagarLog(true);
        HibernateUtil.getSessionFactory();
        scene = new Scene(Objects.requireNonNull(loadFXML("menuI")));
        primaryStage.getIcons().add(new Image("imagenes/icon-license-1.png"));
        primaryStage.setTitle("Menú");
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
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
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,
                            null,
                            null,
                            "Ocurrió un error al cargar la view \""+fxml+"\".",e);
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
     * Permite establecer la scene en el root, desde otro controller.
     * @param vistaFxml ndicar el nombre del archivo FXML de la vista.
     * @param tituloVentana indica el titulo que va a poseer la ventana de la vista.
     */
    static Object setRoot(String vistaFxml, String tituloVentana) {
        scene.setRoot(loadFXML(vistaFxml));
        scene.getRoot().requestFocus();
        stage.setTitle(tituloVentana);
        return fxmlLoader.getController();
    }


    //-------------------------- PARA LA NAVEGACIÓN A FUTURO -------------------------------

    static void setViewAnterior() {
        scenesAnteriores.add(stage.getScene().getRoot());
        titulosAnteriores.add(stage.getTitle());
    }

    static void getViewAnterior() {
        int index = scenesAnteriores.size()-1;
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


    /**
     * Setea a un nodo con el style de error.
     * @param nodo elemento de la interfaz a setearle el style de error
     */
    static void setError(Control nodo) {
        nodo.getStylesheets().clear();
        nodo.getStylesheets().add("css/error.css");
    }

    /**
     * Vuelve un nodo a el style que le corresponde.
     * @param nodo elemento de la interfaz a setearle el style
     */
    static void setValido(Control nodo) {
        nodo.getStylesheets().clear();
        nodo.getStylesheets().add("css/styles.css");
    }

    public static void setStyle(@SuppressWarnings("exports") DialogPane dialogPane) {
        dialogPane.getStylesheets().clear();
        dialogPane.getStylesheets().add("css/styles.css");
    }
}

