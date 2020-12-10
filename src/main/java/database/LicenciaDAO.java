package database;

import dto.DTOImprimirLicencia;
import dto.DTOLicenciaExpirada;
import dto.DTOLicenciasVigentes;
import model.Licencia;

import java.util.List;

public interface LicenciaDAO extends BaseDAO<Licencia,Integer> {

    /** "createListDTOLicenciasVigentes" crea instancias del DTOLicenciasVigentes de acuerdo a las entidades Licencia que estén
        relacionadas al titular con el Id pasado como argumento y que estén vigentes.
        Se utiliza para el funcionamiento de ControllerGestiónLicencia.
        En caso de fallo lanza una Exception.
     @param id_titular id del titular al que se le quieren conocer las licencias vigentes. */
    List<DTOLicenciasVigentes> createListDTOLicenciasVigentes (Integer id_titular) throws Exception;

    List<DTOImprimirLicencia> createListDTOimprimirLic (String argumentos) throws Exception;

    List<DTOImprimirLicencia> createListDTOimprimirLicsinTitular(String s);

    /** "createListDTOLicenciaExpirada" crea instancias del DTOLicenciaExpirada de acuerdo a las entidades Licencia que sean resultado de la consulta.
        Se utiliza para el funcionamiento de ControllerListadoLicenciasExpiradas.
        En caso de fallo lanza una Exception.
        @param consulta consulta para obtener las licencias expiradas. */
    List<DTOLicenciaExpirada> createListDTOLicenciaExpirada(String consulta);
}
