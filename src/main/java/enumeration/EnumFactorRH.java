package enumeration;

public enum EnumFactorRH {
    FACTOR_NEGATIVO("Negativo"),
    FACTOR_POSITIVO("Positivo");

    private String value;

    EnumFactorRH(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static EnumFactorRH getEnum(String value) {
        for(EnumFactorRH v : values()) {
            if(v.getValue().equalsIgnoreCase(value)) return v;
        }
        return null;
    }
}
