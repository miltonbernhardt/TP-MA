import app.PanelAlerta;
import database.TitularDAO;
import database.TitularDAOImpl;
import enumeration.*;
import model.Titular;
import org.hibernate.HibernateError;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class TestDAOTitular {

    @Test
    public void start() {
        TitularDAO dao = new TitularDAOImpl();

        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "40000001",
                "TestDAO",
                "Titular",
                LocalDate.of(2000, Month.MARCH, 21),
                EnumGrupoSanguineo.GRUPO_0,
                EnumFactorRH.FACTOR_POSITIVO,
                true, EnumSexo.MASCULINO);

        try{
            dao.save(t1);
        }
        catch (HibernateError e){
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo obtener el objeto desde la base de datos.", e);
        }

        t1.setApellido("TestDAO updatee");
        dao.update(t1);

        Assert.assertEquals(t1.getApellido(), "TestDAO updatee");

        dao.delete(t1);
        System.out.println(t1);
    }
}

