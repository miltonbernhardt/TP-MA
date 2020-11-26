package dto;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;
import java.time.LocalDate;

public class DTOEmitirLicencia {

    private Integer idTitular;
    private LocalDate fechaNacimiento;
    private String nombre;
    private String apellido;
    private EnumTipoDocumento tipoDocumento;
    private String documento;

    private double costo;
    private String observaciones;
    private EnumClaseLicencia claseLicencia;

    public DTOEmitirLicencia() {}

    public Integer getIdTitular() {
        return idTitular;
    }

    public void setIdTitular(Integer idTitular) {
        this.idTitular = idTitular;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public EnumTipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(EnumTipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EnumClaseLicencia getClaseLicencia() {
        return claseLicencia;
    }

    public void setClaseLicencia(EnumClaseLicencia claseLicencia) {
        this.claseLicencia = claseLicencia;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - " +  tipoDocumento + ": " + documento;
    }
}
