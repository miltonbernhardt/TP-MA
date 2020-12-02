package enumeration;

public enum EnumTipoCampo {
    SOLO_LETRAS("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*$"),
    SOLO_LETRAS_2("[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]"),
    SOLO_NUMEROS("^[0-9]*$"),
    FECHA("");

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

    public static EnumTipoCampo getEnum(String value) {
        for(EnumTipoCampo v : values()) {
            if(v.getValue().equalsIgnoreCase(value)) return v;
        }
        return null;
    }
}
