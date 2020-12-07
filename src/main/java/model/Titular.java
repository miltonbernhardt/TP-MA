package model;

import enumeration.EnumFactorRH;
import enumeration.EnumGrupoSanguineo;
import enumeration.EnumSexo;
import enumeration.EnumTipoDocumento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Titular {

    private List<Licencia> licencias;

    public Integer id;
    private EnumTipoDocumento tipoDNI;
    private String DNI;
    private String apellido;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String calle;
    private Integer numeroCalle;
    private EnumGrupoSanguineo grupoSanguineo;
    private EnumFactorRH factorRH;
    private Boolean donanteOrganos;
    private EnumSexo sexo;

    public Titular(){
        if(licencias==null){
            this.licencias = new ArrayList<>();
        }
    }

    /**
     * Constructor con campos OBLIGATORIOS para generar fuera de la interfaz, sin insercciones sql manuales
     */
    public Titular(EnumTipoDocumento tipoDNI, String DNI, String apellido, String nombre, LocalDate fechaNacimiento, EnumGrupoSanguineo grupoSanguineo,
                   EnumFactorRH factorRH, Boolean donanteOrganos, EnumSexo sexo) {
        this.licencias = new ArrayList<>();
        this.tipoDNI = tipoDNI;
        this.DNI = DNI;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.grupoSanguineo = grupoSanguineo;
        this.factorRH = factorRH;
        this.donanteOrganos = donanteOrganos;
        this.sexo = sexo;
    }

    public Titular(EnumTipoDocumento tipoDNI, String dni, String apellido, String nombre, LocalDate fechaNacimiento, String calle, Integer numeroCalle, EnumGrupoSanguineo grupoSanguineo, EnumFactorRH factorRH, Boolean donanteOrganos, EnumSexo sexo) {
        this.licencias = new ArrayList<>();
        this.tipoDNI = tipoDNI;
        this.DNI = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.grupoSanguineo = grupoSanguineo;
        this.factorRH = factorRH;
        this.donanteOrganos = donanteOrganos;
        this.sexo = sexo;
        this.calle=calle;
        this.numeroCalle =numeroCalle;

    }

    public List<Licencia> getLicencias() {
        return licencias;
    }

    public void setLicencias(List<Licencia> licencias) {
        this.licencias = licencias;
    }

    public void setId(Integer id) { this.id = id; }

    public Integer getId() { return id; }

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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
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

    @Override
    public String toString() {
        return "Titular{" +
                "tipoDNI=" + tipoDNI +
                ", DNI='" + DNI + '\'' +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
