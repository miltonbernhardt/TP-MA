import herramientas.AlertPanel;
import database.TitularDAO;
import database.TitularDAOImpl;
import enumeration.*;
import model.Titular;
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
        catch (Exception e){
            AlertPanel.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo obtener el objeto desde la base de datos.", e);
        }

        t1.setApellido("TestDAO updatee");
        try {
            dao.update(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(t1.getApellido(), "TestDAO updatee");

        try {
            dao.delete(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(t1);
    }
}

