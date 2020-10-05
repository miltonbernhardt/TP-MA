package model;

import enumeration.EnumFactorRH;
import enumeration.EnumGrupoSanguineo;
import enumeration.EnumSexo;
import enumeration.EnumTipoDNI;

import java.util.ArrayList;
import java.util.Date;

public class Titular {

    //Puede ser nulo
    //TODO anotacion hibernate
    private ArrayList<Licencia> licencia;

    private EnumTipoDNI tipoDNI;
    private String DNI;
    private String apellido;
    private String nombre;
    private Date fechaNacimiento;
    private String calle;
    private Integer numeroCalle;
    private EnumGrupoSanguineo grupoSanguineo;
    private EnumFactorRH factorRH;
    private Boolean donanteOrganos;
    private EnumSexo sexo;

    public EnumTipoDNI getTipoDNI() {
        return tipoDNI;
    }

    public void setTipoDNI(EnumTipoDNI tipoDNI) {
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(Integer numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public EnumGrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(EnumGrupoSanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public EnumFactorRH getFactorRH() {
        return factorRH;
    }

    public void setFactorRH(EnumFactorRH factorRH) {
        this.factorRH = factorRH;
    }

    public Boolean getDonanteOrganos() {
        return donanteOrganos;
    }

    public void setDonanteOrganos(Boolean donanteOrganos) {
        this.donanteOrganos = donanteOrganos;
    }

    public EnumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }
}
