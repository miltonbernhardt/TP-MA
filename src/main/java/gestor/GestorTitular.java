package gestor;

import app.PanelAlerta;
import database.TitularDAO;
import database.TitularDAOImpl;
import dto.DTOAltaTitular;
import dto.DTOBuscarTitular;
import dto.DTOEmitirLicencia;
import enumeration.*;
import hibernate.DAO;
import model.Licencia;
import model.Titular;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestorTitular {
    private static GestorTitular instanciaGestor = null;
    private static TitularDAO daoTitular = null;

    private GestorTitular() {}

    public static GestorTitular get() {
        if (instanciaGestor == null){
            instanciaGestor = new GestorTitular();
            daoTitular = new TitularDAOImpl();
        }
        return instanciaGestor;
    }

    public boolean registrarTitular(DTOAltaTitular dto){

        if (titularExistente(dto.getDNI(), dto.getTipoDNI())) {
            return false;
        } else {
            Titular titular = new Titular(dto.getTipoDNI(), dto.getDNI(), dto.getApellido(), dto.getNombre(),
                    dto.getFechaNacimiento(),dto.getCalle() ,dto.getNumeroCalle(),dto.getGrupoSanguineo(), dto.getFactorRH(), dto.getDonanteOrganos(), dto.getSexo());
            return DAO.get().save(titular);}

    }

    //Metodo para verificar que exista en la base de datos un titular con el mismo dni y tipo de dni
    public boolean titularExistente(String dni, EnumTipoDocumento tipo){

        String consulta= "select count(distinct id_titular) from titular t WHERE t.DNI = " + dni  + " AND t.tipo_dni = " + "'" +tipo+"'";
        Integer existenciaTitular= DAO.get().getCantidad(consulta);

        return existenciaTitular != 0;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Licencia> getHistorialLicencias(Integer idTitular){
        return (ArrayList<Licencia>) DAO.get().getResultList("select l from Licencia l where l.titular="+idTitular, Licencia.class);
    }

    public static Integer getEdad(LocalDate fechaNacimiento){
        LocalDate today = LocalDate.now();
        return Period.between(fechaNacimiento, today).getYears();
    }

    public Titular getTitular(Integer idTitular)  {
        try {
            return daoTitular.findById(idTitular);
        } catch (Exception e) {
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo obtener el titular.", e);
            return null;
        }
    }

    public List<DTOBuscarTitular> searchTitular(DTOBuscarTitular argumentosBuscar) {
        String argumentos = "";

        boolean first = true;

        if(!argumentosBuscar.getNombre().equals("")) {
            argumentos += " t.nombre LIKE '%"+argumentosBuscar.getNombre()+"%' ";
            first = false;
        }

        if(!argumentosBuscar.getApellido().equals("")) {
            if(first) first = false;
            else argumentos += " AND ";

            argumentos += " t.apellido LIKE '%"+argumentosBuscar.getApellido()+"%' ";
        }

        //ToDo hacer un between dates
        LocalDate fechaMenor = argumentosBuscar.getFechaNacimientoInicial();
        LocalDate fechaMayor = argumentosBuscar.getFechaNacimientoFinal();
        if(fechaMenor != null && fechaMayor != null) {
            if(first) first = false;
            else argumentos += " AND ";

            if(fechaMenor.isAfter(fechaMayor)){
                LocalDate comodin = fechaMenor;
                fechaMenor = fechaMayor;
                fechaMayor = comodin;
            }

            argumentos += " t.fechaNacimiento BETWEEN '"+fechaMenor+"' AND '"+fechaMayor+"' ";
        }
        else{
            if(fechaMenor != null) {
                if(first) first = false;
                else argumentos += " AND ";

                argumentos += " t.fechaNacimiento>='"+fechaMenor+"' ";
            }
            else{
                if(fechaMayor != null) {
                    if(first) first = false;
                    else argumentos += " AND ";

                    argumentos += " t.fechaNacimiento <= '"+fechaMayor+"' ";
                }
            }
        }

        if(argumentosBuscar.getTipoDocumento() != null) {
            if(first) first = false;
            else argumentos += " AND ";

            argumentos += " t.tipoDNI='"+argumentosBuscar.getTipoDocumento()+"' ";
        }

        if(!argumentosBuscar.getDocumento().equals(""))  {
            if(first) first = false;
            else argumentos += " AND ";

            argumentos += " t.DNI LIKE '%"+argumentosBuscar.getDocumento()+"%' ";
        }

        try {
            if(!first) return daoTitular.createListDTOBuscarTitular(" WHERE " + argumentos);
            else return daoTitular.createListDTOBuscarTitular("");

        }
        catch (Exception e){
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo realizar la consulta deseada.", e);
            return new ArrayList<>();
        }

    }
}
