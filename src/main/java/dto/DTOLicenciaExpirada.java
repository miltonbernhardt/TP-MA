package dto;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;

import java.time.LocalDate;

public class DTOLicenciaExpirada {

    private String nroLicencia;
    private String apellido;
    private String nombre;
    private String tipoDNI;
    private String DNI;
    private String claseLicencia;
    private String fechaVencimientoLicencia;
    private boolean rangofechas;
    private String fechaInicial;
    private String fechaFinal;
    private boolean ordenamientoDescendente;


    public DTOLicenciaExpirada() {

        this.nroLicencia = null;
        this.apellido = null;
        this.nombre = null;
        this.tipoDNI = null ;
        this.DNI = null;
        this.fechaVencimientoLicencia = null;
        this.claseLicencia = null;
        this.rangofechas = false;
        this.fechaInicial = null;
        this.fechaFinal = null;
        this.ordenamientoDescendente = false;
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
        return nroLicencia;
    }

    public void setNroLicencia(String nroLicencia) {
        this.nroLicencia = nroLicencia;
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
