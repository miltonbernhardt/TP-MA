package dto;

import enumeration.EnumSexo;

public class DTOModificarTitular {
    private String DNI;
    private String nombre;
    private int id;
    private String apellido;
    private String calle;
    private int numeroCalle;
    private Boolean donante;
    private EnumSexo sexo;

    public DTOModificarTitular() {
    }

    public DTOModificarTitular(int id, String nombre, String apellido, String calle, int numero, Boolean donante, EnumSexo sexo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.calle = calle;
        this.numeroCalle = numero;
        this.donante = donante;
        this.sexo = sexo;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(int numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public Boolean getDonante() {
        return donante;
    }

    public void setDonante(Boolean donante) {
        this.donante = donante;
    }

    public EnumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }
}
