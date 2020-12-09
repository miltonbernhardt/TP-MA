package dto;

import enumeration.EnumClaseLicencia;

import java.time.LocalDate;

public class DTOLicenciasVigentes {

    private int id_licencia;
    private EnumClaseLicencia claseLicencia;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String observaciones;
    private float costo;

    private int id_titular;

    public DTOLicenciasVigentes(Integer id_licencia, EnumClaseLicencia claseLicencia, LocalDate fechaEmision, LocalDate fechaVencimiento, String observaciones, float costo, Integer id_titular) {
        this.id_licencia = id_licencia;
        this.claseLicencia = claseLicencia;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.observaciones = observaciones;
        this.costo = costo;
        this.id_titular = id_titular;
    }

    public int getId_licencia() {
        return id_licencia;
    }

    public void setId_licencia(int id_licencia) {
        this.id_licencia = id_licencia;
    }

    public EnumClaseLicencia getClaseLicencia() {
        return claseLicencia;
    }

    public void setClaseLicencia(EnumClaseLicencia claseLicencia) {
        this.claseLicencia = claseLicencia;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getId_titular() {
        return id_titular;
    }

    public void setId_titular(int id_tituolar) {
        this.id_titular = id_tituolar;
    }
}
