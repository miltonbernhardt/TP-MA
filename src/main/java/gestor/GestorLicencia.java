package gestor;

import app.PanelAlerta;
import com.sun.javafx.runtime.SystemProperties;
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
import java.util.List;

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
        Calcula el tiempo de vigencia de la licencia
     */
    public static Integer getTiempoEnVigencia(LocalDate date){
        return 0;
    }

    /*
        Trae desde la Base de Datos, las clases de licencias que tiene permitido solicitar
        el titular actual en pantalla.
     */

    public ArrayList<EnumClaseLicencia> getClasesLicencias(Integer idTitular){

        ArrayList<EnumClaseLicencia> Claseslicencias = new ArrayList<EnumClaseLicencia>();
        ArrayList<Licencia> historialLicencias = new ArrayList<Licencia>();
        Titular titular = (Titular)DAO.get().get(Titular.class, idTitular);
        Boolean claseLicencia = true;

        //retorna el historial de licencias obtenidad/actuales del titular actual
        historialLicencias = GestorTitular.getHistorialLicencias(idTitular);

        //Instancia auxiliar de licencia
        Licencia licencia = new Licencia();

        //Se asume que todos los titulares registrados tienen por lo menos 17 anos, sino no se hubiese registrado

        //Clase A --> valida si esta en condiciones de solicitar una licencia clase A
        for(int l = 0; l < historialLicencias.size(); l++) {
                licencia = historialLicencias.get(l);
                if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_A)){
                if(licencia.getFechaVencimiento().isAfter(LocalDate.now())){
                    claseLicencia = false;
                    break;
                }
            }
        }
        if(claseLicencia) Claseslicencias.add(EnumClaseLicencia.CLASE_A);
        else claseLicencia = true;

        //Clase B --> se valida si esta en condiciones de solicitar una licencia clase B
        for(int l = 0; l < historialLicencias.size(); l++) {
            licencia = historialLicencias.get(l);
            if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_B) ||
               licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_C) ||
               licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_D)){
                if(licencia.getFechaVencimiento().isAfter(LocalDate.now())){
                    claseLicencia = false;
                    break;
                }
            }
        }
        if(claseLicencia)
            Claseslicencias.add(EnumClaseLicencia.CLASE_B);
        claseLicencia = false;

        if(GestorTitular.getEdad(titular.getFechaNacimiento()) > 20){
            Boolean primeraLicencia = true;
            //Clase C --> se valida si esta en condiciones de solicitar una licencia clase C
            for(int l = 0; l < historialLicencias.size(); l++) {
                licencia = historialLicencias.get(l);
                if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_C) ||
                   licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_D)){
                    primeraLicencia = false;
                    if(licencia.getFechaVencimiento().isAfter(LocalDate.now())){
                        claseLicencia = false;
                        break;
                    }
                    else claseLicencia = true;
                }
                else{
                    if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_B)){
                        if(GestorLicencia.getTiempoEnVigencia(licencia.getFechaEmision())>=1)
                            claseLicencia = true;
                    }
                }
                if(claseLicencia)
                    if(!primeraLicencia || primeraLicencia && GestorTitular.getEdad(titular.getFechaNacimiento()) < 66)
                        Claseslicencias.add(EnumClaseLicencia.CLASE_C);
            }


        }





        return Claseslicencias;
    }

    public double calcularCostoLicencia(){
    return 0;
    }

    public Boolean emitirLicencia(Integer idTitular, EnumClaseLicencia claseLicenciaElegida, String observaciones) {

/*

        Titular titular = (Titular) GestorTitular.get().getTitular(idTitular);

        if(!DAO.get().save(licencia))
            return false;

        if(!DAO.get().update(titular))
            return false;*/

        Boolean valido = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Titular titular = null;

        try {
            session.beginTransaction();
            titular = session.get(Titular.class, idTitular);
        }
        catch (HibernateException exception) {
            valido = false;
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo obtener el objeto desde la base de datos.", exception);
        }


        Licencia licencia = new Licencia();
        licencia.setClaseLicencia(claseLicenciaElegida);
        licencia.setFechaEmision(LocalDate.now());
        LocalDate vencimiento = calcularVigenciaLicencia(titular.getFechaNacimiento());
        licencia.setFechaVencimiento(vencimiento);
        licencia.setObservaciones(observaciones);
        licencia.setTitular(titular);
        titular.getLicencias().add(licencia);
        DAO.get().update(titular);

        DAO.get().save(licencia);/*
        try {
            session.save(licencia);
            session.getTransaction().commit();
        }
        catch (HibernateException exception) {
            valido = false;
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo guardar en la base de datos.", exception);
            session.getTransaction().rollback();
        }*/
        session.close();




 /*
        ArrayList<Licencia> licencias = GestorTitular.get().getHistorialLicencias( idTitular);
        if(licencias == null || licencias.size() == 0){
            licencias = new ArrayList<>();
        }
        else{
            licencias.add(licencia);
        }
        titular.setLicencias(licencias);
*/
        return valido;
    }

    private LocalDate calcularVigenciaLicencia(LocalDate fechaNacimiento) {
        //TODO corregir cuando se implemente "Calcular Vigencia de Licencia"
        return fechaNacimiento.plusYears(30);
    }
}
