package enumeration;

public enum EnumClaseLicencia {
    CLASE_A("Clase A", "Ciclomotores, motocicletas y triciclos motorizados."),
    CLASE_B("Clase B", "Automóviles y camionetas con acoplado."),
    CLASE_C("Clase C", "Camiones sin acoplado y los comprendidos en la clase B."),
    CLASE_D("Clase D", "Servicio de transporte de pasajeros, emergencia, seguridad y los comprendidos en la " +
            "clase B o C, según el caso."),
    CLASE_E("Clase E", "Camiones articulados o con acoplado, maquinaria especial no agrícola y los " +
            "comprendidos en la clase B y C."),
    CLASE_F("Clase F", "Automotores especialmente adaptados para discapacitados."),
    CLASE_G("Clase G", "Tractores agrícolas y maquinaria especial agrícola.");

    private String value;
    private String descripcion;

    EnumClaseLicencia(String value, String descripcion) {
        this.value = value;
        this.descripcion = descripcion;
    }

    public String getValue() {
        return value;
    }
    public String getDescripcion() {
        return descripcion;
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
