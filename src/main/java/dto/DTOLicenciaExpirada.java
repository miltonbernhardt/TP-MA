package dto;

import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;

import java.time.LocalDate;

public class DTOLicenciaExpirada {


    private int id; // nroLicencia
    private String apellido;
    private String nombre;
    private EnumTipoDocumento tipoDNI;
    private String DNI;
    private EnumClaseLicencia claseLicencia;
    private LocalDate fechaVencimiento;
    private boolean rangofechas;
    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
    private boolean ordenamientoDescendente;


    public DTOLicenciaExpirada() {
//l.id, t.apellido, t.nombre, t.tipoDNI, t.DNI, l.claseLicencia, l.fechaVencimiento
        this.id = 0;
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

    public DTOLicenciaExpirada(int idLicencia, String apellido, String nombre, EnumTipoDocumento tipoDNI, String DNI, EnumClaseLicencia claseLicencia, LocalDate fechaVencimiento){

        this.id = idLicencia;
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDNI = tipoDNI ;
        this.DNI = DNI;
        this.fechaVencimiento = fechaVencimiento;
        this.claseLicencia = claseLicencia;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public boolean isRangofechas() {
        return rangofechas;
    }

    public void setRangofechas(boolean rangofechas) {
        this.rangofechas = rangofechas;
    }

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

    public int getNroLicencia() {
        return id;
    }

    public void setNroLicencia(int nroLicencia) {
        this.id = nroLicencia;
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
