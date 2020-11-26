package gestor;

import database.LicenciaDAO;
import database.LicenciaDAOImpl;
import dto.DTOEmitirLicencia;
import enumeration.EnumClaseLicencia;
import exceptions.MenorDeEdadException;
import hibernate.DAO;
import model.Licencia;
import model.Titular;
import model.Vigencia;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class GestorLicencia {

    private static GestorLicencia instanciaGestor = null;
    private static LicenciaDAO daoLicencia = null;

    private GestorLicencia() {}

    public static GestorLicencia get() {
        if (instanciaGestor == null){
            instanciaGestor = new GestorLicencia();
            daoLicencia = new LicenciaDAOImpl();
        }
        return instanciaGestor;
    }

    /*
    Calcular vigencia recibe como parametro la fecha de nacimiento del Titular y su id, retorna
    un objeto Vigencia con la cantidad de años de la vigencia y la fecha de vencimiento.
     */
    public static Vigencia calcularVigencia(LocalDate nacimiento, int id_titular) throws MenorDeEdadException {
        Vigencia vigencia = new Vigencia();
        int years = GestorTitular.getEdad(nacimiento);
        if(years<17){
            throw new MenorDeEdadException();

        }else{
            if(years < 21){
                String sql = "select count(distinct id_licencia) from licencia WHERE id_titular = " + id_titular;

                Integer cantidadLicencias = DAO.get().getCantidad(sql);

                if(cantidadLicencias == 0){
                    vigencia.setVigencia(1);
                    vigencia.setFechaVencimiento(nacimiento.plusYears(years+1));
                } else {
                    vigencia.setVigencia(3);
                    vigencia.setFechaVencimiento(nacimiento.plusYears(years+3));
                }

            } else if( years < 46){
                vigencia.setVigencia(5);
                vigencia.setFechaVencimiento(nacimiento.plusYears(years+5));
            } else if( years < 60){
                vigencia.setVigencia(4);
                vigencia.setFechaVencimiento(nacimiento.plusYears(years+4));
            }  else if( years < 70){
                vigencia.setVigencia(3);
                vigencia.setFechaVencimiento(nacimiento.plusYears(years+3));
            } else if( years > 70){
                vigencia.setVigencia(1);
                vigencia.setFechaVencimiento(nacimiento.plusYears(years+1));
            }
        }

        return vigencia;
    }

    /*
        Calcula el tiempo de vigencia de la licencia desde que se hizo la emision hasta la fecha actual
     */
    public static Integer getTiempoEnVigencia(LocalDate fechaEmision,LocalDate fechaVencimiento){
        if(fechaVencimiento.isBefore(LocalDate.now()))
            return Period.between(fechaEmision,fechaVencimiento).getYears();
        else return Period.between(fechaEmision,LocalDate.now()).getYears();
    }

    /**
        - Trae desde la Base de Datos, las clases de licencias que tiene
        permitido solicitar el titular actual en pantalla.
        - Se asume que todos los titulares registrados tienen por lo
        menos 17 años, sino no se hubiese registrado como titular en el sistema
        @param idTitular id del titular en la base de datos
     */

    public ArrayList<EnumClaseLicencia> getClasesLicencias(Integer idTitular){

        //instancia de titular actual
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
            switch (licencia.getClaseLicencia()) {
                case CLASE_A:
                    if (licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(0, false);
                    break;
                case CLASE_B:
                    if (licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(1, false);
                    if (licencia.getFechaEmision().isBefore(licenciaB))
                        licenciaB = licencia.getFechaEmision();
                    break;
                case CLASE_C:
                    if (licencia.getFechaVencimiento().isAfter(LocalDate.now())) {
                        flagsClases.set(1, false);
                        flagsClases.set(2, false);
                    }
                    claseC = true;
                    break;
                case CLASE_D:
                    if (licencia.getFechaVencimiento().isAfter(LocalDate.now())) {
                        flagsClases.set(1, false);
                        flagsClases.set(2, false);
                        flagsClases.set(3, false);
                    }
                    claseD = true;
                    break;
                case CLASE_E:
                    if (licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(4, false);
                    claseE = true;
                    break;
                case CLASE_F:
                    if (licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(5, false);
                    break;
                case CLASE_G:
                    if (licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                        flagsClases.set(6, false);
                    break;
            }
        }

        //Conductores profesionales, licencias C, D, E
        Integer edadTitular = GestorTitular.getEdad(titular.getFechaNacimiento());
        Integer vigenciaB = getTiempoEnVigencia(licenciaB,LocalDate.now());

        if(edadTitular >= 21 && vigenciaB > 0){
            if(flagsClases.get(2))
                if(!claseC && edadTitular > 65) flagsClases.set(2,false);
            if(flagsClases.get(3))
                if(!claseD && edadTitular > 65) flagsClases.set(3,false);
            if(flagsClases.get(4))
                if(!claseE && edadTitular > 65) flagsClases.set(4,false);
        }
        else{
            flagsClases.set(2,false);
            flagsClases.set(3,false);
            flagsClases.set(4,false);
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

    /*
    TODO calcularCostoLicencia
    CLASE G A B TIENEN LOS MISMOS VALORES, D E F Y LUEGO C.
    VER TABLA.
    SE LE SUMA AL TOTAL 8
    ver sintaxis de suma (creo q este es el de python)
    public double calcularCostoLicencia(Vigencia vigencia, EnumClaseLicencia.){
        float costoTotal = 0;
        if (G || A ||B ){
               if (VIGENCIA= 5 años){
                costoTotal= + 40;
                }
                if (VIGENCIA= 4 años)){
                costoTotal= +30;
                }
                if (VIGENCIA= 3 años)){
                costoTotal= +25;
                }
                if (VIGENCIA= 1 años)){
                costoTotal= +20;
                }
        }else  if (D || E ||F ){
               if (VIGENCIA= 5 años){
                costoTotal= + 59;
                }
                if (VIGENCIA= 4 años)){
                costoTotal= +29;
                }
                if (VIGENCIA= 3 años)){
                costoTotal= +39;
                }
                if (VIGENCIA= 1 años)){
                costoTotal= +29;
                }
} else FALTA VALORES DE C

    costoTotal= +8;

        return costoTotal;
    }*/

    public Boolean emitirLicencia(DTOEmitirLicencia dto) throws MenorDeEdadException {
        Licencia licencia = new Licencia();
        licencia.setClaseLicencia(dto.getClaseLicencia());
        licencia.setFechaEmision(LocalDate.now());
        LocalDate vencimiento = calcularVigencia(dto.getFechaNacimiento(), dto.getIdTitular()).getFechaVencimiento();
        licencia.setFechaVencimiento(vencimiento);
        licencia.setObservaciones(dto.getObservaciones());

        Titular titular = GestorTitular.get().getTitular(dto.getIdTitular());
        licencia.setTitular(titular);
        /*
        TODO si se encuentra una forma de inicializar lazy relations, cambiar esto y la propiedad en hibernate.cfg.xml (enable_lazy_load_no_trans)
         */
        titular.getLicencias().add(licencia);

        if(!DAO.get().update(titular))
            return false;

        return true;
    }
}
