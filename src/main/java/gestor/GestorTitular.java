package gestor;

import dto.DTOAltaTitular;
import dto.DTOEmitirLicencia;
import enumeration.*;
import hibernate.DAO;
import model.Licencia;
import model.Titular;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorTitular {
    private static GestorTitular instanciaGestor = null;

    private GestorTitular() {}

    public static GestorTitular get() {
        if (instanciaGestor == null){
            instanciaGestor = new GestorTitular();
        }
        return instanciaGestor;
    }

    public boolean verificarTitular (DTOAltaTitular dto){
        //TODO buscar en base de datos los titulares para comparar DNI

        return true;

    }

    public boolean registrarTitular(DTOAltaTitular dto){

        if (titularExistente(dto.getDNI(), dto.getTipoDNI())) {
            return false;
        } else {
            Titular titular = new Titular(dto.getTipoDNI(), dto.getDNI(), dto.getApellido(), dto.getNombre(),
                    dto.getFechaNacimiento(), dto.getGrupoSanguineo(), dto.getFactorRH(), dto.getDonanteOrganos(), dto.getSexo());
            return DAO.get().save(titular);}

    }

    //Metodo para verificar que exista en la base de datos un titular con el mismo dni y tipo de dni
    public boolean titularExistente(String dni, EnumTipoDocumento tipo){

        String consulta= "select count(distinct id_titular) from titular t WHERE t.DNI = " + dni  + " AND t.tipo_dni = " + "'" +tipo+"'";
        Integer existenciaTitular= DAO.get().getCantidad(consulta);

        if (existenciaTitular != 0){
            return true;
        }
        return false;
    };




    public DTOEmitirLicencia buscarTitular(Integer idTitular) {
        DTOEmitirLicencia dto = new DTOEmitirLicencia();

        Titular titular = (Titular) DAO.get().get(Titular.class, idTitular);

        dto.setIdTitular(titular.getId());
        dto.setNombre(titular.getNombre());
        dto.setApellido(titular.getApellido());
        dto.setFechaNacimiento(titular.getFechaNacimiento());
        dto.setTipoDocumento(titular.getTipoDNI());
        dto.setDocumento(titular.getDNI());

        return dto;
    }
    public List<DTOEmitirLicencia> buscarTitulares() {
          /*
            TODO cambiar al implementar buscar/alta titular
         */
        String consulta = "SELECT new dto.DTOEmitirLicencia(t.id, t.fechaNacimiento, t.nombre, t.apellido, t.tipoDNI, t.DNI) "
                + "FROM Titular t ORDER BY t.id";
        return (List<DTOEmitirLicencia>) DAO.get().getResultList(consulta, DTOEmitirLicencia.class);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Licencia> getHistorialLicencias(Integer idTitular){
        return (ArrayList<Licencia>) DAO.get().getResultList("select l from Licencia l where l.titular="+idTitular, Licencia.class);
    }

    public static Integer getEdad(LocalDate fechaNacimiento){
        LocalDate today = LocalDate.now();
        return Period.between(fechaNacimiento, today).getYears();
    }

    public Titular getTitular(Integer idTitular) {
        return (Titular) DAO.get().get(Titular.class, idTitular);
    }
}
