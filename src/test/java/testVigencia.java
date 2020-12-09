import database.LicenciaDAO;
import database.LicenciaDAOImpl;
import database.TitularDAO;
import database.TitularDAOImpl;
import dto.DTOLicenciasVigentes;
import model.Licencia;
import model.Titular;
import org.junit.Test;

import java.util.List;


public class testVigencia {

    @Test
    public void start() throws Exception {
        LicenciaDAO dao = new LicenciaDAOImpl();
        List<DTOLicenciasVigentes> list = dao.createListDTOLicenciasVigentes(136);
        for(DTOLicenciasVigentes dto:list){
            System.out.println(dto.getId_licencia() + " id_titular: "+dto.getId_titular());
        }

        DTOLicenciasVigentes dto = list.get(0);
        Licencia l = dao.findById(dto.getId_licencia());
        l.setObservaciones(dto.getObservaciones());
        
        dao.update(l);
        
        
    }
}

