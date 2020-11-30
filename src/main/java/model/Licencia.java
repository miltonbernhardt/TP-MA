package model;

import enumeration.EnumClaseLicencia;
import java.time.LocalDate;

public class Licencia {



    private Titular titular;



    private Integer id_titular;

    private int id;
    private EnumClaseLicencia claseLicencia;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String observaciones;
    private float costo;

    public Licencia(){}

    /**
     * Constructor con campos OBLIGATORIOS para generar fuera de la interfaz, sin insercciones sql manuales
     * @param titular
     * @param claseLicencia
     * @param fechaEmision
     * @param fechaVencimiento
     */
    public Licencia(Titular titular, EnumClaseLicencia claseLicencia, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        this.titular = titular;

        this.claseLicencia = claseLicencia;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.observaciones= "";
    }

    public Licencia(Integer id_titular, int id, EnumClaseLicencia claseLicencia, LocalDate fechaEmision, LocalDate fechaVencimiento, String observaciones) {
        this.id_titular = id_titular;
        this.id = id;
        this.claseLicencia = claseLicencia;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.observaciones = observaciones;
    }
    public Integer getId_titular() {
        return id_titular;
    }

    public void setId_titular(Integer id_titular) {
        this.id_titular = id_titular;
    }
    public Titular getTitular() {
        return titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}