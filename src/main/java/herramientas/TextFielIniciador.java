package herramientas;

import enumeration.EnumTipoCampo;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class TextFielIniciador {

    static public void soloLetras(TextField campo){
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(EnumTipoCampo.SOLO_LETRAS.getValue()).matcher(newValue).matches())
                campo.setText(oldValue);
        });
    }

    static public void soloNumeros(TextField campo){
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(EnumTipoCampo.SOLO_NUMEROS.getValue()).matcher(newValue).matches())
                campo.setText(oldValue);
        });
    }

    static public void letrasAcento(TextField campo){
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(EnumTipoCampo.LETRAS_ACENTOS.getValue()).matcher(newValue).matches())
                campo.setText(oldValue);
        });
    }

    static public void letrasNumero(TextField campo){
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.compile(EnumTipoCampo.NUMEROS_LETRAS.getValue()).matcher(newValue).matches())
                campo.setText(oldValue);
        });
    }
}
