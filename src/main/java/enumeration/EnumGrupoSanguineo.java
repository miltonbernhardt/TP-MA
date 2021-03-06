package enumeration;

public enum EnumGrupoSanguineo {
    GRUPO_A("A"),
    GRUPO_B("B"),
    GRUPO_AB("AB"),
    GRUPO_0("0");

    private String value;

    private EnumGrupoSanguineo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static EnumGrupoSanguineo getEnum(String value) {
        for(EnumGrupoSanguineo v : values()) {
            if(v.getValue().equalsIgnoreCase(value)) return v;
        }
        return null;
    }
}




