package enumeration;

public enum EnumTipoCampo {
    LETRAS_ACENTOS("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]*$"),
    SOLO_LETRAS("^[A-Za-zÑñ]*$"),
    SOLO_NUMEROS("^[0-9]*$"),
    NUMEROS_LETRAS("^[0-9A-Za-zÑñ]*$");

    private String value;

    EnumTipoCampo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
