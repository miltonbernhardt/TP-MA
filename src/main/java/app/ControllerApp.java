package app;

import enumeration.*;
import herramientas.AlertPanel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import herramientas.HibernateUtil;

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
        primaryStage.setMinWidth(1016);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxWidth(1016);
        primaryStage.setMaxHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
    }

    /** Carga el archivo 'fxml' (la vista) en el parent principal.
        Se le pasa solo el nombre, sin el '.fxml'. */
    private static Parent loadFXML(String fxml)  {
        fxmlLoader = new FXMLLoader(ControllerApp.class.getResource(fxml +".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException | RuntimeException  e) {
            AlertPanel.get(EnumTipoAlerta.EXCEPCION,
                            null,
                            null,
                            "Ocurrió un error al cargar la view \""+fxml+"\".",e);
            e.printStackTrace();
            return null;
        }
    }

    /** Sale de la aplicación cerrando la base de datos por completo. */
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

    /** Guarda la pantalla anterior y su título de ventana */
    static void setViewAnterior() {
        scenesAnteriores.add(stage.getScene().getRoot());
        titulosAnteriores.add(stage.getTitle());
    }

    /** Obtiene la pantalla anterior y su titulo de ventana */
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
            salir();
        }
        scenesAnteriores.remove(p);
        titulosAnteriores.remove(t);
    }
}

