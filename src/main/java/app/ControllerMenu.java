package app;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ControllerMenu {
    @FXML private AnchorPane panUsuarios;
    @FXML private AnchorPane panValor;
    @FXML private AnchorPane panVigencia;
    @FXML private AnchorPane panRequerimientos;
    @FXML private AnchorPane panRecuerda;

    @FXML private ImageView arrowUsuario;
    @FXML private ImageView arrowValor;
    @FXML private ImageView arrowVigencia;
    @FXML private ImageView arrowRequerimiento;
    @FXML private ImageView arrowRecuerda;

    @FXML private ImageView santaFe;

    /**
     * Setea como vista principal la información de vigencia
     */
    public void onRecuerdaButtonClicked(){
        boolean isPresent = panRecuerda.isVisible();
        onlyInfoVisible();
        santaFe.setVisible(isPresent);
        panRecuerda.setVisible(!isPresent);
        arrowRecuerda.setVisible(!isPresent);
    }

    /**
     * Setea como vista principal la información de vigencia
     */
    public void onVigenciaButtonClicked(){
        boolean isPresent = panVigencia.isVisible();
        onlyInfoVisible();
        santaFe.setVisible(isPresent);
        panVigencia.setVisible(!isPresent);
        arrowVigencia.setVisible(!isPresent);
    }

    /**
     * Setea como vista principal la información de valores
     */
    public void onValorButtonClicked(){
        boolean isPresent = panValor.isVisible();
        onlyInfoVisible();
        santaFe.setVisible(isPresent);
        panValor.setVisible(!isPresent);
        arrowValor.setVisible(!isPresent);
    }

    /**
     * Setea como vista principal la información de requerimientos
     */
    public void onRequerimientoButtonClicked(){
        boolean isPresent = panRequerimientos.isVisible();
        onlyInfoVisible();
        santaFe.setVisible(isPresent);
        panRequerimientos.setVisible(!isPresent);
        arrowRequerimiento.setVisible(!isPresent);
    }

    /**
     * Setea como vista principal la información de usuario
     */
    public void onUsuarioButtonClicked(){
        boolean isPresent = panUsuarios.isVisible();
        onlyInfoVisible();
        santaFe.setVisible(isPresent);
        panUsuarios.setVisible(!isPresent);
        arrowUsuario.setVisible(!isPresent);
    }

    /**
     * Setea las vistas de información a la vista por default
     */
    private void onlyInfoVisible(){
        santaFe.setVisible(true);

        arrowUsuario.setVisible(false);
        arrowValor.setVisible(false);
        arrowVigencia.setVisible(false);
        arrowRequerimiento.setVisible(false);
        arrowRecuerda.setVisible(false);

        panUsuarios.setVisible(false);
        panValor.setVisible(false);
        panVigencia.setVisible(false);
        panRequerimientos.setVisible(false);
        panRecuerda.setVisible(false);
    }

    @FXML
    private void darAltaTitular() {
        ControllerAltaTitular.get();
    }

    @FXML
    private void emitirLicencia() {
        ControllerEmitirLicencia.get();
    }

    @FXML
    private void salir() {
        ControllerApp.salir();
    }
}
