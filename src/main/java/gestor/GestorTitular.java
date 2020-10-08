package gestor;

import dto.DTOEmitirLicencia;
import hibernate.DAO;
import model.Licencia;
import model.Titular;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
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


    public DTOEmitirLicencia buscarTitular() {
        DTOEmitirLicencia dto  = new DTOEmitirLicencia();

        //TODO implementar cuando se haga una interfaz de busqueda/alta de titular
        Titular titular = (Titular) DAO.get().get(Titular.class, 102);

        dto.setIdTitular(titular.getId());
        dto.setNombre(titular.getNombre());
        dto.setApellido(titular.getApellido());
        dto.setFechaNacimiento(titular.getFechaNacimiento());
        dto.setTipoDocumento(titular.getTipoDNI());
        dto.setDocumento(titular.getDNI());

        return  dto;
    }

    public static ArrayList<Licencia> getHistorialLicencias(Integer idTitular){
        return (ArrayList<Licencia>) DAO.get().getResultList("select * from licencia where id_titular="+idTitular, Licencia.class);
    }

    public Integer getEdad(LocalDate fechaNacimiento){
        LocalDate today = LocalDate.now();

        return Period.between(fechaNacimiento, today).getYears();
    }
}
