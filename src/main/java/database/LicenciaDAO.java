package database;

import dto.DTOBuscarTitular;
import dto.DTOImprimirLicencia;
import model.Licencia;

import java.util.List;

public interface LicenciaDAO extends BaseDAO<Licencia,Integer> {

    List<DTOImprimirLicencia> createListDTOimprimirLic (String argumentos) throws Exception;


    List<DTOImprimirLicencia> createListDTOimprimirLicsinTitular(String s);
}
