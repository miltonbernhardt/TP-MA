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

/** Controler principal de la aplicación, ya que es quién lanza el programa, y el que tiene los métodos que permiten la navegación entre Scenes. */
public class ControllerApp extends Application {
    private static Scene scene;
    private static Stage stage;
    private static FXMLLoader fxmlLoader;

    /** Atributos que sirven para la navegación entre Scenes, ya que guardan las Scenes visitadas anteriormente
        y el titulo que estas usan para la ventana de la aplicación. */
    private static final List<Parent> scenesAnteriores = new ArrayList<>();
    private static final List<String> titulosAnteriores = new ArrayList<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    /** Inicializa el Stage principal.
        Se modifican los parámetros del Stage para tenga un tamaño y estilo predefinidos por nosotros.
        Se le setea el menuI como Scene al Stage principal. */
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

    /** Carga el archivo 'fxml' (archivo que contiene la forma en que se organiza la Scene) en el parent principal (Stage).
        Se le pasa solo el nombre del archivo sin la extensión '.fxml'. */
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

    /** Cierra la aplicación y la base de datos por completo. */
    static void salir() {
        Platform.exit();
        System.exit(0);
        HibernateUtil.closeBaseDatos();
    }

    /** Permite establecer la Scene en el Stage root, desde otro controller (quienes manejan las lógicas de interfaz).
        Con esto se permite cambiar las Scene elgiendo que pantalla se quiere ver.
        @param vistaFxml indicar el nombre del archivo FXML de la vista.
        @param tituloVentana indica el titulo que va a poseer la ventana de la vista. */
    static Object setRoot(String vistaFxml, String tituloVentana) {
        scene.setRoot(loadFXML(vistaFxml));
        scene.getRoot().requestFocus();
        stage.setTitle(tituloVentana);
        return fxmlLoader.getController();
    }

    /** Guarda la Scene anterior y su título de ventana */
    static void setViewAnterior() {
        scenesAnteriores.add(stage.getScene().getRoot());
        titulosAnteriores.add(stage.getTitle());
    }

    /** Obtiene la Scene anterior y su título de ventana */
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

