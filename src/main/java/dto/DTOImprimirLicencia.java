package dto;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;
import model.Titular;

import java.time.LocalDate;

public class DTOImprimirLicencia {


    private String nombre;
   private String apellido;

    private Titular titular;
    private LocalDate fechaNacimiento;
    private int id_titular;
    private int id;
    private EnumClaseLicencia claseLicencia;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String observaciones;
    private float costo;
    private EnumTipoDocumento tipoDocumento;

    private String documento;


    public DTOImprimirLicencia(){}

    public DTOImprimirLicencia(int id, int id_titular,EnumClaseLicencia claseLicencia, LocalDate fechaEmision, LocalDate fechaVencimiento, String observaciones) {
        this.id_titular = id_titular;
        this.id = id;
        this.claseLicencia = claseLicencia;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.observaciones = observaciones;
    }

    public DTOImprimirLicencia(int id, Titular titular ,EnumClaseLicencia claseLicencia, LocalDate fechaEmision, LocalDate fechaVencimiento, String observaciones) {
        this.titular = titular;
        this.id = id;
        this.claseLicencia = claseLicencia;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.observaciones = observaciones;
    }


    public Titular getTitular() {
        return titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    public String getnombreTitular() {
        return nombre;
    }

    public void setnombreTitular(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdTitular() {
        return id_titular;
    }

    public void setIdTitular(Integer idTitular) {
        this.id_titular = idTitular;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    /*@Override
    public String toString() {
        return id +  + claseLicencia.toString() + fechaEmision + observaciones;
    }*/

}
