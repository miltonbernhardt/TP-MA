package database;

import dto.DTOBuscarTitular;
import model.Titular;
import java.util.List;

public interface TitularDAO extends BaseDAO<Titular, Integer> {

    /**
     * "createListDTOBuscarTitular" crea instancias del dtoBuscarTitular de acuerdo a las entidades
     *      Titular que coincidan con los argumentos de la consulta.
     * En caso de fallo lanza una Exception.
     * @param argumentos son los argumentos para la consulta.
     */
    List<DTOBuscarTitular> createListDTOBuscarTitular (String argumentos) throws Exception;
}