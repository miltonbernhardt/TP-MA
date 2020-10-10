package gestor;

import app.PanelAlerta;
import com.sun.javafx.runtime.SystemProperties;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.bind.v2.TODO;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoAlerta;
import hibernate.DAO;
import hibernate.HibernateUtil;
import javafx.util.converter.DateStringConverter;
import model.Licencia;
import model.Titular;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestorLicencia {

    private static GestorLicencia instanciaGestor = null;

    private GestorLicencia() {}

    public static GestorLicencia get() {
        if (instanciaGestor == null){
            instanciaGestor = new GestorLicencia();
        }
        return instanciaGestor;
    }

    /*
        Calcula el tiempo de vigencia de la licencia desde que se hizo la emision
        hasta la fecha actual
     */
    //TODO Programar
    public static Integer getTiempoEnVigencia(LocalDate date){
        return 0;
    }

    /**
        - Trae desde la Base de Datos, las clases de licencias que tiene
        permitido solicitar el titular actual en pantalla.
        - Se asume que todos los titulares registrados tienen por lo
        menos 17 años, sino no se hubiese registrado como titular en el sistema
        @param idTitular
     */

    public ArrayList<EnumClaseLicencia> getClasesLicencias(Integer idTitular){

        //intancia de titular actual
        Titular titular = (Titular)DAO.get().get(Titular.class, idTitular);
        //historial de licencias del titular
        ArrayList<Licencia> historialLicencias = GestorTitular.getHistorialLicencias(idTitular);
        //Instancia auxiliar de licencia
        Licencia licencia = new Licencia();
        //Banderas index 0:A, 1:B, 2:C, 3:D, 4:E, 5:F, 6:G / que puede tener o no el titular
        ArrayList<Boolean> flagsClases = new ArrayList<Boolean>();
        //inicializacion de las banderas
        for(int i = 0; i < 7; i++) flagsClases.add(true);
        //Variable que guarda la fecha de emision de la licencia B, si el titular tuvo/tiene
        LocalDate licenciaB = LocalDate.now();
        //Banderas licencias del tipo profesional, clase C, D, E
        Boolean claseC = false, claseD = false, claseE = false;

        for(int l = 0; l < historialLicencias.size(); l++) {
                licencia = historialLicencias.get(l);
            switch(licencia.getClaseLicencia())
            {
                case CLASE_A:
                    if(licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(0,false);
                    break;
                case CLASE_B:
                    if(licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(1,false);
                    if(licencia.getFechaEmision().isBefore(licenciaB))
                        licenciaB = licencia.getFechaEmision();
                    break;
                case CLASE_C:
                    if(licencia.getFechaVencimiento().isAfter(LocalDate.now())) {
                        flagsClases.set(1,false);
                        flagsClases.set(2,false);
                    }
                    claseC = true;
                    break;
                case CLASE_D:
                    if(licencia.getFechaVencimiento().isAfter(LocalDate.now())) {
                        flagsClases.set(1,false);
                        flagsClases.set(2,false);
                        flagsClases.set(3,false);
                    }
                    claseD = true;
                    break;
                case CLASE_E:
                    if(licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(4,false);
                    claseE = true;
                        break;
                case CLASE_F:
                    if(licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(5,false);
                    break;
                case CLASE_G:
                    if(licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(6,false);
                    break;
            }
            //Conductores profesionales, licencias C, D, E
            Integer edadTitular = GestorTitular.getEdad(titular.getFechaNacimiento());
            Integer vigenciaB = getTiempoEnVigencia(licenciaB);
            if(edadTitular > 21 && vigenciaB > 0){
                if(flagsClases.get(2))
                    if(!claseC && edadTitular > 65) flagsClases.set(2,false);
                if(flagsClases.get(3))
                    if(!claseD && edadTitular > 65) flagsClases.set(3,false);
                if(flagsClases.get(4))
                    if(!claseC && edadTitular > 65) flagsClases.set(4,false);
            }
            else{
                flagsClases.set(2,false);
                flagsClases.set(3,false);
                flagsClases.set(4,false);
            }
        }
        //licencias que es posible solicitar por el titular en cuestion
        ArrayList<EnumClaseLicencia> Claseslicencias = new ArrayList<EnumClaseLicencia>();
        if(flagsClases.get(0)) Claseslicencias.add(EnumClaseLicencia.CLASE_A);
        if(flagsClases.get(1)) Claseslicencias.add(EnumClaseLicencia.CLASE_B);
        if(flagsClases.get(2)) Claseslicencias.add(EnumClaseLicencia.CLASE_C);
        if(flagsClases.get(3)) Claseslicencias.add(EnumClaseLicencia.CLASE_D);
        if(flagsClases.get(4)) Claseslicencias.add(EnumClaseLicencia.CLASE_E);
        if(flagsClases.get(5)) Claseslicencias.add(EnumClaseLicencia.CLASE_F);
        if(flagsClases.get(6)) Claseslicencias.add(EnumClaseLicencia.CLASE_G);

        return Claseslicencias;
    }

    //TODO calcularCostoLicencia
    public double calcularCostoLicencia(){
    return 0;
    }

    /**
     *
     * @param idTitular
     * @param claseLicenciaElegida
     * @param observaciones
     */
    public Boolean emitirLicencia(Integer idTitular, EnumClaseLicencia claseLicenciaElegida, String observaciones) {
        Titular titular = (Titular) GestorTitular.get().getTitular(idTitular);

        Licencia licencia = new Licencia();
        licencia.setClaseLicencia(claseLicenciaElegida);
        licencia.setFechaEmision(LocalDate.now());
        LocalDate vencimiento = calcularVigenciaLicencia(titular.getFechaNacimiento());
        licencia.setFechaVencimiento(vencimiento);
        licencia.setObservaciones(observaciones);
        licencia.setTitular(titular);

        //TODO si se encuentra una forma de inicializar lazy relations, cambiar esto y la propiedad en hibernate.cfg.xml (enable_lazy_load_no_trans)
        titular.getLicencias().add(licencia);

        if(!DAO.get().update(titular))
            return false;

        return true;
    }

    /**
     * Calcula la vigencia de la licencia a partir de la fecha de nacimiento
     * TODO corregir cuando se implemente "Calcular Vigencia de Licencia"
     * @param fechaNacimiento
     */
    private LocalDate calcularVigenciaLicencia(LocalDate fechaNacimiento) {
        return fechaNacimiento.plusYears(30);
    }
}
