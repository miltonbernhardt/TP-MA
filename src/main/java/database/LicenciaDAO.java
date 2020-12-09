package database;

import dto.DTOImprimirLicencia;
import dto.DTOLicenciaExpirada;
import dto.DTOLicenciasVigentes;
import model.Licencia;

import java.util.List;

public interface LicenciaDAO extends BaseDAO<Licencia,Integer> {

    List<DTOLicenciasVigentes> createListDTOLicenciasVigentes (Integer id_titular) throws Exception;

    List<DTOImprimirLicencia> createListDTOimprimirLic (String argumentos) throws Exception;

    List<DTOImprimirLicencia> createListDTOimprimirLicsinTitular(String s);

    static List<DTOLicenciaExpirada> createListDTOLicenciaExpirada(String consulta) {
        return null;
    }
}
