package gestor;

import com.sun.javafx.runtime.SystemProperties;
import enumeration.EnumClaseLicencia;
import hibernate.DAO;
import javafx.util.converter.DateStringConverter;
import model.Licencia;
import model.Titular;
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

    public void emitirLicencia(Integer idTitular, EnumClaseLicencia claseLicenciaElegida) {
        Licencia licencia = new Licencia();
        licencia.setClaseLicencia(claseLicenciaElegida);
        licencia.setFechaEmision(LocalDate.now());

        Titular titular = (Titular) GestorTitular.get().getTitular(idTitular);
        //TODO calcular bien la fecha de venc y asociar bien al titular

    }
}
