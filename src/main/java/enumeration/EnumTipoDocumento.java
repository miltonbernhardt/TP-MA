package enumeration;

public enum EnumTipoDocumento {
    LIBRETA_CIVIL("Libreta civil"),
    LIBRETA_DE_ENROLAMIENTO("Libreta de enrolamiento"),
    PASAPORTE("Pasaporte"),
    DNI("DNI");

    private String value;

    private EnumTipoDocumento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static EnumTipoDocumento getEnum(String value) {
        for(EnumTipoDocumento v : values()) {
            if(v.getValue().equalsIgnoreCase(value)) return v;
        }
        return null;
    }
}
