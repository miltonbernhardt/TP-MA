package enumeration;

public enum EnumSexo {
    FEMENINO("Femenino"),
    MASCULINO("Masculino");

    private String value;

    EnumSexo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static EnumSexo getEnum(String value) {
        for(EnumSexo v : values()) {
            if(v.getValue().equalsIgnoreCase(value)) return v;
        }
        return null;
    }
}
