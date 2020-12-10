package gestor;

import dto.DTOLicenciasVigentes;
import herramientas.AlertPanel;
import database.LicenciaDAO;
import database.LicenciaDAOImpl;
import database.TitularDAOImpl;
import dto.DTOEmitirLicencia;
import dto.DTOImprimirLicencia;
import dto.DTOLicenciaExpirada;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoAlerta;
import model.Licencia;
import model.Titular;
import model.Vigencia;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

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

    /** Calcular vigencia recibe como parámetro la fecha de nacimiento del Titular y su id, retorna
        un objeto Vigencia con la cantidad de años de la vigencia y la fecha de vencimiento. */
    public static Vigencia calcularVigencia(LocalDate nacimiento, int id_titular)  {
        Vigencia vigencia = new Vigencia();
        int years = GestorTitular.getEdad(nacimiento);

            if(years < 21){
                String sql = "select count(distinct id_licencia) from licencia WHERE id_titular = " + id_titular;

                Integer cantidadLicencias = 0;
                try {
                    cantidadLicencias = daoLicencia.getCantidad(sql);
                } catch (Exception ignored) { }

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

        return vigencia;
    }


    /** Calcula el tiempo de vigencia de la licencia desde que se hizo la emisión hasta la fecha actual */
    public static Integer getTiempoEnVigencia(LocalDate fechaEmision,LocalDate fechaVencimiento){
        if(fechaVencimiento.isBefore(LocalDate.now()))
            return Period.between(fechaEmision,fechaVencimiento).getYears();
        else return Period.between(fechaEmision,LocalDate.now()).getYears();
    }


    /** Trae desde la Base de Datos, las clases de licencias que tiene
        permitido solicitar el titular actual en pantalla.
        Se asume que todos los titulares registrados tienen por lo
        menos 17 años, sino no se hubiese registrado como titular en el sistema
        @param idTitular id del titular en la base de datos */
    public ArrayList<EnumClaseLicencia> getClasesLicencias(Integer idTitular){
        //ToDo Coli que muestre las que puede renovar
        //instancia de titular actual
        Titular titular = GestorTitular.get().getTitular(idTitular);
        //historial de licencias del titular
        List<Licencia> historialLicencias = new ArrayList<>();
        try {
            historialLicencias = getHistorialLicencias(idTitular);
        } catch (Exception ignored) { }
        //Instancia auxiliar de licencia
        Licencia licencia;
        //Banderas index 0:A, 1:B, 2:C, 3:D, 4:E, 5:F, 6:G / que puede tener o no el titular
        ArrayList<Boolean> flagsClases = new ArrayList<>();
        //inicialización de las banderas
        for(int i = 0; i < 7; i++) flagsClases.add(true);
        //Variable que guarda la fecha de emisión de la licencia B, si el titular tuvo/tiene
        LocalDate licenciaB = LocalDate.now();

        for (Licencia historialLicencia : historialLicencias) {
            licencia = historialLicencia;
            if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_B)){
                if (licencia.getFechaEmision().isBefore(licenciaB))
                    licenciaB = licencia.getFechaEmision();
            }
            else if(licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_C) &&
               licencia.getFechaVencimiento().isAfter(LocalDate.now()))
                flagsClases.set(1, false);
            else if((licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_D) ||
                     licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_E)) &&
                     licencia.getFechaVencimiento().isAfter(LocalDate.now())) {
                flagsClases.set(1, false);
                flagsClases.set(2, false);
            }
        }
        //Conductores profesionales, licencias C, D, E
        int edadTitular = GestorTitular.getEdad(titular.getFechaNacimiento());
        Integer vigenciaB = getTiempoEnVigencia(licenciaB,LocalDate.now());

        if(edadTitular >= 21 && vigenciaB > 0){
            if(flagsClases.get(2) && edadTitular > 65) flagsClases.set(2,false);
            if(flagsClases.get(3) && edadTitular > 65) flagsClases.set(3,false);
            if(flagsClases.get(4) && edadTitular > 65) flagsClases.set(4,false);
        }
        else{
            flagsClases.set(2,false);
            flagsClases.set(3,false);
            flagsClases.set(4,false);
        }

        //licencias que es posible solicitar por el titular en cuestión
        ArrayList<EnumClaseLicencia> ClasesLicencias = new ArrayList<>();
        if(flagsClases.get(0)) ClasesLicencias.add(EnumClaseLicencia.CLASE_A);
        if(flagsClases.get(1)) ClasesLicencias.add(EnumClaseLicencia.CLASE_B);
        if(flagsClases.get(2)) ClasesLicencias.add(EnumClaseLicencia.CLASE_C);
        if(flagsClases.get(3)) ClasesLicencias.add(EnumClaseLicencia.CLASE_D);
        if(flagsClases.get(4)) ClasesLicencias.add(EnumClaseLicencia.CLASE_E);
        if(flagsClases.get(5)) ClasesLicencias.add(EnumClaseLicencia.CLASE_F);
        if(flagsClases.get(6)) ClasesLicencias.add(EnumClaseLicencia.CLASE_G);

        return ClasesLicencias;
    }


    /** Calcula el costo de la licencia. */
    public double calcularCostoLicencia(DTOEmitirLicencia dtoEmitirLicencia) {
        LocalDate fechaV = dtoEmitirLicencia.getFechaNacimiento();
        int vig = calcularVigencia(fechaV, dtoEmitirLicencia.getIdTitular()).vigencia;
        String clase = dtoEmitirLicencia.getClaseLicencia().getValue();

        double costoTotal = 0;
        if (clase.equals("Clase A") || clase.equals("Clase B") || clase.equals("Clase G")){
            switch (vig){
                case 1:
                    costoTotal = costoTotal+20;
                    break;
                case 3:
                    costoTotal = costoTotal+25;
                    break;
                case 4:
                    costoTotal = costoTotal +30;
                    break;
                case 5:
                    costoTotal = costoTotal+ 40;
                    break;
            }
        }
        else {
            if (clase.equals("Clase F") || clase.equals("Clase D") || clase.equals("Clase E")) {
                switch (vig){
                    case 1:
                    case 4:
                        costoTotal = costoTotal + 29;
                        break;
                    case 3:
                        costoTotal = costoTotal + 39;
                        break;
                    case 5:
                        costoTotal = costoTotal + 59;
                        break;
                }
            }
            else {
                if(clase.equals("Clase C")) {
                    switch (vig){
                        case 1:
                            costoTotal = costoTotal + 23;
                            break;
                        case 3:
                            costoTotal = costoTotal + 30;
                            break;
                        case 4:
                            costoTotal = costoTotal + 35;
                            break;
                        case 5:
                            costoTotal = costoTotal + 47;
                            break;
                    }
                }
            }
        }

        return costoTotal + 8;
    }

    /** Genera una entidad licencia en la base de datos.
        Retorna true si la inserción resulta exitosa, sino retorna false.
        @param dtoEmitirLicencia dto que posee los datos de la licencia a generar. */
    public Boolean generarLicencia(DTOEmitirLicencia dtoEmitirLicencia){
        Licencia licencia = new Licencia();
        licencia.setClaseLicencia(dtoEmitirLicencia.getClaseLicencia());
        licencia.setFechaEmision(LocalDate.now());
        LocalDate vencimiento = calcularVigencia(dtoEmitirLicencia.getFechaNacimiento(), dtoEmitirLicencia.getIdTitular()).getFechaVencimiento();
        licencia.setFechaVencimiento(vencimiento);
        licencia.setObservaciones(dtoEmitirLicencia.getObservaciones());
        licencia.setCosto((float) calcularCostoLicencia(dtoEmitirLicencia));
        Titular titular = GestorTitular.get().getTitular(dtoEmitirLicencia.getIdTitular());
        licencia.setTitular(titular);
        titular.getLicencias().add(licencia);

        try {
            new TitularDAOImpl().update(titular);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Actualiza las observaciones de una licencia vigente
     * @param dtoLicenciaRenovar
     * @return
     */
    public Boolean renovarObservaciones(DTOLicenciasVigentes dtoLicenciaRenovar){
        LicenciaDAO dao = new LicenciaDAOImpl();
        Licencia licencia = null;
        try {
            licencia = dao.findById(dtoLicenciaRenovar.getId_licencia());
        } catch (Exception e) {
            return false;
        }
        licencia.setObservaciones(dtoLicenciaRenovar.getObservaciones());
        try {
            dao.update(licencia);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dependiendo de la nueva licencia, revocar las vigentes correspondientes,
     * Nueva C --> expira a B
     * Nueva D --> expira a B o C, depende cual tenga vigente
     * Nueva E --> expira a B o C, depende cual tenga vigente
     * @param dtoLicenciaRenovar
     * @return
     */
    public Boolean renovarTipoLicencia(DTOEmitirLicencia dtoEmitirLicencia, List<DTOLicenciasVigentes> dtoLicenciaRenovar){
        //TodO renovarTipoLicencia
        LicenciaDAO dao = new LicenciaDAOImpl();
        Licencia licenciaExpirar = null;
        for (DTOLicenciasVigentes licencia : dtoLicenciaRenovar) {
            if (licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_B) ||
                licencia.getClaseLicencia().equals(EnumClaseLicencia.CLASE_C)) {
                try {
                    licenciaExpirar = dao.findById(licencia.getId_licencia());
                } catch (Exception e) {
                    return false;
                }
                licenciaExpirar.setFechaVencimiento(LocalDate.now());
                try {
                    dao.update(licenciaExpirar);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return generarLicencia(dtoEmitirLicencia);
    }

    /** Obtiene las licencias que están asociadas a un titular.
        @param idTitular id del titular al que se le quiere consultar sus licencias. */
    public static List<Licencia> getHistorialLicencias(Integer idTitular) throws Exception {
        return daoLicencia.findAllByQuery("select l from Licencia l where l.titular="+idTitular);
    }


    public List<DTOImprimirLicencia> searchLic(DTOImprimirLicencia argumentosBuscar) {
        String argumentos = "";

        boolean first = true;

        if(argumentosBuscar.getId() != 0) {

            if(first) first = false;
            else argumentos += " AND ";
            System.out.println("licencia id " +argumentosBuscar.getId());
            argumentos += " l.id= "+argumentosBuscar.getId()+" ";
        }

        if(argumentosBuscar.getIdTitular() != 0) {

            System.out.println("titular " +argumentosBuscar.getIdTitular());
            if(first){
                first = false;
            }
            else argumentos += " AND l.titular = t.id AND ";
            argumentos += " t.id= "+argumentosBuscar.getIdTitular()+" ";

        }
        if(argumentosBuscar.getClaseLicencia() != null) {
            String campo;
            campo = argumentosBuscar.getClaseLicencia().getValue();
            campo = campo.replaceAll(" ", "_");
            System.out.println("campo clase" + campo);
            if(first) first = false;
            else argumentos += " AND ";
            argumentos += " l.claseLicencia='"+campo+"' ";
        }

        //TODO aca solo estaba para una fecha de emision especifica, hacer el beetwen y guardarlos en parametros
        LocalDate fechaEmision = argumentosBuscar.getFechaEmision();

        if(fechaEmision !=null) {
            if(first) first = false;
            else argumentos += " AND ";

            argumentos += " l.fechaEmision='" + fechaEmision+ "'";
        }

        System.out.println(argumentos);
        try {
            if(!first){ argumentos += " AND l.titular = t.id ";
                System.out.println(argumentos);
            return daoLicencia.createListDTOimprimirLic(" WHERE " + argumentos);}
            else return daoLicencia.createListDTOimprimirLic("");

        }
        catch (Exception e){
            AlertPanel.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo realizar la consulta deseada.", e);
            return new ArrayList<>();
        }

    }


    public ArrayList<EnumClaseLicencia> obtenerLicencias(int id_titular){
        ArrayList<EnumClaseLicencia> Claseslicencias = new ArrayList<>();
        //instancia de titular actual
        Licencia licencia;

        //historial de licencias del titular
        List<Licencia> historialLicencias = new ArrayList<>();
        try {
            historialLicencias = getHistorialLicencias(id_titular);
        } catch (Exception ignored) { }

        for (Licencia historialLicencia : historialLicencias) {
            licencia = historialLicencia;
            Claseslicencias.add(licencia.getClaseLicencia());
        }
        return Claseslicencias;
    }


    public static List<DTOLicenciaExpirada> obtenerListadoLicenciasExpiradas(DTOLicenciaExpirada filtros){
        String consulta = armarConsultaLicenciasExpiradas(filtros);
        try {
            List<DTOLicenciaExpirada> listDTOLicenciaExpirada = daoLicencia.createListDTOLicenciaExpirada(consulta);
            return listDTOLicenciaExpirada;
        }
        catch (Exception e){
            AlertPanel.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo realizar la consulta deseada.", e);
            return new ArrayList<>();
        }
    }


    private static String armarConsultaLicenciasExpiradas(DTOLicenciaExpirada filtro)
    {
        String consulta = "";

        if(filtro.isRangofechas()){
            // consulta = consulta + " WHERE l.id = t.id AND DATE(l.fechaVencimiento) BETWEEN  '"+ filtro.getFechaInicial() + "' AND '" + filtro.getFechaFinal() + "'";
            consulta = consulta + " WHERE DATE(l.fechaVencimiento) BETWEEN  '"+ filtro.getFechaInicial() + "' AND '" + filtro.getFechaFinal() + "'";
        } else if(filtro.getFechaInicial() != null){
            consulta = consulta + " WHERE DATE(l.fechaVencimiento) >= '"+ filtro.getFechaInicial() +"'";
            //consulta = consulta + " WHERE l.id = t.id AND DATE(l.fechaVencimiento) = '"+ filtro.getFechaInicial() +"'";
        } else{
            //consulta = consulta + " WHERE l.id = t.id AND DATE(l.fechaVencimiento) = '"+ LocalDate.now().toString()+"'";
            consulta = consulta + " WHERE DATE(l.fechaVencimiento) = '"+ LocalDate.now().toString()+"'";
        }

        if(filtro.getNroLicencia() != 0 ){
            consulta += " AND l.id = " + filtro.getNroLicencia();
        }
        if (filtro.getClaseLicencia() != null ){

            String clase = filtro.getClaseLicencia().getValue().replaceAll(" ", "_");
            consulta += " AND l.claseLicencia = '" + clase + "'";
        }
        if (!filtro.getApellido().equals("")){
            consulta += " AND t.apellido LIKE '%" + filtro.getApellido() + "%'";
        }
        if (!filtro.getNombre().equals("")){
            consulta += " AND t.nombre LIKE '%" + filtro.getNombre() + "%'";
        }
        if (filtro.getTipoDNI() !=null){
            String tipoDNI = filtro.getTipoDNI().getValue().replaceAll(" ", "_");
            consulta += " AND t.tipoDNI = '" + tipoDNI + "'";
        }
        if (!filtro.getDNI().equals("")){
            consulta += " AND t.DNI LIKE '%" + filtro.getDNI() + "%'";
        }

        //if(!filtro.getApellido().isEmpty() && consulta.equalsIgnoreCase("select * from licencia ")) {
        //  consulta = consulta + " apellido = " + auxap ;
        //}

        return consulta;
    }

    public List<DTOLicenciasVigentes> getLicenciasVigentes(int idTitular) {
        try {
            return daoLicencia.createListDTOLicenciasVigentes(idTitular);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}


