import database.TitularDAO;
import database.TitularDAOImpl;
import dto.DTOAltaTitular;
import enumeration.*;
import gestor.GestorTitular;
import hibernate.DAO;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Licencia;
import model.Titular;
import org.junit.Assert;
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

    @Test
    public void registrarTitular(){
        DTOAltaTitular dto = new DTOAltaTitular();
        dto.setTipoDNI(EnumTipoDocumento.DNI);
        dto.setDNI("39631740");
        dto.setApellido("Weidmann");
        dto.setNombre("camila");
        dto.setFechaNacimiento(LocalDate.of(1996, 7, 8));
        dto.setGrupoSanguineo(EnumGrupoSanguineo.GRUPO_A);
        dto.setFactorRH(EnumFactorRH.FACTOR_POSITIVO);
        dto.setDonanteOrganos(true);
        dto.setSexo(EnumSexo.FEMENINO);

        GestorTitular gestorTitular = GestorTitular.get();
        boolean resultado = gestorTitular.registrarTitular(dto);
        Assert.assertFalse(resultado);
    }
}