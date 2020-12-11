package herramientas;

import javafx.scene.control.TextField;
import java.util.regex.Pattern;

public class TextFielIniciador {

    final static private String LETRAS_ACENTOS = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]*$";
    final static private String SOLO_LETRAS = "^[A-Za-zÑñ]*$";
    final static private String SOLO_NUMEROS = "^[0-9]*$";
    final static private String NUMEROS_LETRAS = "^[0-9A-Za-zÑñ]*$";

    /** Permite que un TextField acepte espacios en blanco y todas las letras sin acentos (mayúsculas y minúsculas) */
    static public void soloLetras(TextField campo){
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(SOLO_LETRAS).matcher(newValue).matches())
                campo.setText(oldValue);
        });
    }


    /** Permite que un TextField números */
    static public void soloNumeros(TextField campo){
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(SOLO_NUMEROS).matcher(newValue).matches())
                campo.setText(oldValue);
        });
    }

    /** Permite que un TextField acepte espacios en blanco y todas las letras con o sin acentos (mayúsculas y minúsculas) */
    static public void letrasAcento(TextField campo){
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(LETRAS_ACENTOS).matcher(newValue).matches())
                campo.setText(oldValue);
        });
    }

    /** Permite que un TextField acepte números y todas las letras sin acentos (mayúsculas y minúsculas) */
    static public void letrasNumero(TextField campo){
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(NUMEROS_LETRAS).matcher(newValue).matches())
                campo.setText(oldValue);
        });
    }
}
