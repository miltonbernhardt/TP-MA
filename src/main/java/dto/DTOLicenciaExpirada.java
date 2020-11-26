package dto;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;

import java.time.LocalDate;

public class DTOLicenciaExpirada {

    private EnumTipoDocumento tipoDNI;
    private String DNI;
    private String apellido;
    private String nombre;
    private Integer nroLicencia;
    private EnumClaseLicencia claseLicencia;
    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
    private boolean ordenamientoDescendente;

    public EnumTipoDocumento getTipoDNI() {
        return tipoDNI;
    }

    public void setTipoDNI(EnumTipoDocumento tipoDNI) {
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

    public Integer getNroLicencia() {
        return nroLicencia;
    }

    public void setNroLicencia(Integer nroLicencia) {
        this.nroLicencia = nroLicencia;
    }

    public EnumClaseLicencia getClaseLicencia() {
        return claseLicencia;
    }

    public void setClaseLicencia(EnumClaseLicencia claseLicencia) {
        this.claseLicencia = claseLicencia;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public boolean isOrdenamientoDescendente() {
        return ordenamientoDescendente;
    }

    public void setOrdenamientoDescendente(boolean ordenamientoDescendente) {
        this.ordenamientoDescendente = ordenamientoDescendente;
    }
}
