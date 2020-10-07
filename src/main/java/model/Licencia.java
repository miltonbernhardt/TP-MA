package model;

import enumeration.EnumClaseLicencia;
import java.util.Date;
import java.util.Date;

public class Licencia {

    private Titular titular;

    private Integer id;
    private EnumClaseLicencia claseLicencia;
    private Date fechaEmision;
    private Date fechaVencimiento;
    private String observaciones;

    public Licencia(){}

    /**
     * Constructor con campos OBLIGATORIOS para generar fuera de la interfaz, sin insercciones sql manuales
     * @param titular
     * @param claseLicencia
     * @param fechaEmision
     * @param fechaVencimiento
     */
    public Licencia(Titular titular, EnumClaseLicencia claseLicencia, Date fechaEmision, Date fechaVencimiento) {
        this.titular = titular;
        this.claseLicencia = claseLicencia;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Titular getTitular() {
        return titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnumClaseLicencia getClaseLicencia() {
        return claseLicencia;
    }

    public void setClaseLicencia(EnumClaseLicencia claseLicencia) {
        this.claseLicencia = claseLicencia;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
