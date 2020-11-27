import database.TitularDAO;
import database.TitularDAOImpl;
import enumeration.*;
import hibernate.DAO;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Licencia;
import model.Titular;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class TestGestorTitular {

    @Test
    public void InsertarTitularLicencia(){
        TitularDAO dao = new TitularDAOImpl();
        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "40000001",
                "PruebaTipoG",
                "caseG1",
                LocalDate.of(2000, Month.MARCH, 21),
                EnumGrupoSanguineo.GRUPO_0,
                EnumFactorRH.FACTOR_POSITIVO,
                true, EnumSexo.MASCULINO);
        Licencia l1 = new Licencia(t1,
                EnumClaseLicencia.CLASE_G,
                LocalDate.of(2020, Month.FEBRUARY, 01),
                LocalDate.of(2024, Month.AUGUST, 12));
        //Agrego licencia al titular
        t1.getLicencias().add(l1);
        //t1.getLicencias().add(l2);
        //Persisto en Base de Datos
        try {
            dao.save(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}