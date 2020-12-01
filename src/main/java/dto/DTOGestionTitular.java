package dto;

import enumeration.*;

import java.time.LocalDate;

public class DTOGestionTitular {
    private int idTitular;
    private LocalDate fechaNacimiento;
    private String nombre;
    private String apellido;
    private EnumTipoDocumento tipoDocumento;
    private String documento;

    private LocalDate fechaNacimientoInicial;
    private LocalDate fechaNacimientoFinal;

    public DTOGestionTitular(){}

    public DTOGestionTitular(int idTitular, LocalDate fechaNacimiento, String nombre, String apellido, EnumTipoDocumento tipoDocumento, String documento) {
        this.idTitular = idTitular;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
    }

    public int getIdTitular() {
        return idTitular;
    }

    public void setIdTitular(int idTitular) {
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

    public LocalDate getFechaNacimientoInicial() {
        return fechaNacimientoInicial;
    }

    public void setFechaNacimientoInicial(LocalDate fechaNacimientoInicial) {
        this.fechaNacimientoInicial = fechaNacimientoInicial;
    }

    public LocalDate getFechaNacimientoFinal() {
        return fechaNacimientoFinal;
    }

    public void setFechaNacimientoFinal(LocalDate fechaNacimientoFinal) {
        this.fechaNacimientoFinal = fechaNacimientoFinal;
    }
}
