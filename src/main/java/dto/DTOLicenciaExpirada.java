package dto;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;

import java.time.LocalDate;

public class DTOLicenciaExpirada {

    private String id; // nroLicencia
    private String apellido;
    private String nombre;
    private String tipoDNI;
    private String DNI;
    private String claseLicencia;
    private String fechaVencimiento;
    private boolean rangofechas;
    private String fechaInicial;
    private String fechaFinal;
    private boolean ordenamientoDescendente;


    public DTOLicenciaExpirada() {
//l.id, t.apellido, t.nombre, t.tipoDNI, t.DNI, l.claseLicencia, l.fechaVencimiento
        this.id = null;
        this.apellido = null;
        this.nombre = null;
        this.tipoDNI = null ;
        this.DNI = null;
        this.fechaVencimiento = null;
        this.claseLicencia = null;
        this.rangofechas = false;
        this.fechaInicial = null;
        this.fechaFinal = null;
        this.ordenamientoDescendente = false;
    }

    public DTOLicenciaExpirada(String idLicencia, String apellido, String nombre, String tipoDNI, String dni, String claseLicencia, String fechaVencimiento){

        this.id = idLicencia;
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDNI = tipoDNI ;
        this.DNI = dni;
        this.fechaVencimiento = fechaVencimiento;
        this.claseLicencia = claseLicencia;

    }
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public boolean isRangofechas() {
        return rangofechas;
    }

    public void setRangofechas(boolean rangofechas) {
        this.rangofechas = rangofechas;
    }

    public String getTipoDNI() {
        return tipoDNI;
    }

    public void setTipoDNI(String tipoDNI) {
        this.tipoDNI = tipoDNI;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNroLicencia() {
        return id;
    }

    public void setNroLicencia(String nroLicencia) {
        this.id = nroLicencia;
    }

    public String getClaseLicencia() {
        return claseLicencia;
    }

    public void setClaseLicencia(String claseLicencia) {
        this.claseLicencia = claseLicencia;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public boolean isOrdenamientoDescendente() {
        return ordenamientoDescendente;
    }

    public void setOrdenamientoDescendente(boolean ordenamientoDescendente) {
        this.ordenamientoDescendente = ordenamientoDescendente;
    }
}
