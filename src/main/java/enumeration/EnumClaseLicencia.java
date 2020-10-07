package enumeration;

public enum EnumClaseLicencia {
    CLASE_A("Clase A"),
    CLASE_B("Clase B"),
    CLASE_C("Clase C"),
    CLASE_D("Clase D"),
    CLASE_E("Clase E"),
    CLASE_F("Clase F"),
    CLASE_G("Clase G");

    private String value;

    EnumClaseLicencia(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static EnumClaseLicencia getEnum(String value) {
        for(EnumClaseLicencia v : values()) {
            if(v.getValue().equalsIgnoreCase(value)) return v;
        }
        return null;
    }
}
