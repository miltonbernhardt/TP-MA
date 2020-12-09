import database.LicenciaDAO;
import database.LicenciaDAOImpl;
import database.TitularDAO;
import database.TitularDAOImpl;
import dto.DTOEmitirLicencia;
import dto.DTOImprimirLicencia;
import dto.DTOModificarTitular;
import enumeration.*;
import gestor.GestorLicencia;
import gestor.GestorTitular;
import model.Licencia;
import model.Titular;
import model.Vigencia;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestGestorLicencia<licencias> {

    @Test
    public void calcularVigencia_3anios() {
        TitularDAO dao = new TitularDAOImpl();

        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "42000001",
                "PruebaTipoB",
                "caseB1",
                LocalDate.of(2000, Month.MARCH, 21),
                EnumGrupoSanguineo.GRUPO_0,
                EnumFactorRH.FACTOR_POSITIVO,
                true, EnumSexo.MASCULINO);
        Licencia l1 = new Licencia(t1,
                EnumClaseLicencia.CLASE_B,
                LocalDate.of(2018, Month.MARCH, 21),
                LocalDate.of(2019, Month.MARCH, 21));
        //Agrego licencia al titular
        t1.getLicencias().add(l1);
        //Persisto en Base de Datos
        int id = -1;
        try {
            id = dao.save(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Vigencia vigencia = GestorLicencia.calcularVigencia(t1.getFechaNacimiento(), id);
        LocalDate vencimiento = LocalDate.parse("21-03-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 3);
        System.out.println(vigencia.getFechaVencimiento());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
    }

    /*@Test
    public void calcularVigencia2_3anios(){
        LocalDate nacimiento = LocalDate.parse("08-08-2001", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("08-08-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 141);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
        Assert.assertEquals(vigencia.getVigencia(), 3);
        System.out.println(vigencia.getFechaVencimiento());
    }
*/
    @Test
    public void calcularVigencia_5anios(){
        LocalDate nacimiento = LocalDate.parse("08-07-1996", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("08-07-2025", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 111);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 5);
        System.out.println(vigencia.getFechaVencimiento());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
    }
    /*
    Emitir licencia de clase B para una persona de 20 años:
     */
    @Test
    public void emitirLicencia() throws Exception {
        TitularDAO dao = new TitularDAOImpl();
        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "42000001",
                "PruebaTipoB",
                "caseB1",
                LocalDate.of(2000, Month.MARCH, 21),
                EnumGrupoSanguineo.GRUPO_0,
                EnumFactorRH.FACTOR_POSITIVO,
                true, EnumSexo.MASCULINO);
        Licencia l1 = new Licencia(t1,
                EnumClaseLicencia.CLASE_B,
                LocalDate.of(2018, Month.MARCH, 21),
                LocalDate.of(2019, Month.MARCH, 21));
        int id = -1;
        try {
            id = dao.save(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DTOEmitirLicencia dto = new DTOEmitirLicencia();
        dto.setIdTitular(id);
        dto.setFechaNacimiento(t1.getFechaNacimiento());
        dto.setNombre(t1.getNombre());
        dto.setApellido(t1.getApellido());
        dto.setTipoDocumento(t1.getTipoDNI());
        dto.setDocumento(t1.getDNI());
        dto.setCosto(48.0);
        dto.setObservaciones(null);
        dto.setClaseLicencia(l1.getClaseLicencia());
        boolean resultado = GestorLicencia.get().generarLicencia(dto);
        /* t1 = dao.findById(id);
            dao.delete(t1); */
        Assert.assertTrue(resultado);
    }

    @Test
    //28A1 = $28 para licencia clase A por 1 año
    public void testCalcularCostoLicencia28A1(){
        TitularDAO dao = new TitularDAOImpl();
        DTOEmitirLicencia dtoEmitirLicencia = null;
        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "40266367", "PruebaCosto20A1","case20A1",
                LocalDate.of(1007, Month.MARCH, 19), EnumGrupoSanguineo.GRUPO_B,
                EnumFactorRH.FACTOR_POSITIVO, true, EnumSexo.FEMENINO);
        Licencia l1 = new Licencia(t1, EnumClaseLicencia.CLASE_A,
                LocalDate.of(2018, Month.MARCH, 21),
                LocalDate.of(2019, Month.MARCH, 21));
        int id = -1;
        try {
            id = dao.save(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DTOEmitirLicencia dto = new DTOEmitirLicencia();
        dto.setIdTitular(id);
        dto.setFechaNacimiento(t1.getFechaNacimiento());
        dto.setNombre(t1.getNombre());
        dto.setApellido(t1.getApellido());
        dto.setTipoDocumento(t1.getTipoDNI());
        dto.setDocumento(t1.getDNI());
        dto.setCosto(20.0);
        dto.setObservaciones(null);
        dto.setClaseLicencia(l1.getClaseLicencia());
        double costoTotal = GestorLicencia.get().calcularCostoLicencia(dto);
        Assert.assertEquals(costoTotal, 28.0, 0);
    }

    @Test
    //67F5 = $67 para licencia clase F por 5 años
    public void testCalcularCostoLicencia67F5(){
        TitularDAO dao = new TitularDAOImpl();
        DTOEmitirLicencia dtoEmitirLicencia = null;
        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "40266367", "Lozano","Indiana",
                LocalDate.of(1997, Month.MARCH, 19), EnumGrupoSanguineo.GRUPO_B,
                EnumFactorRH.FACTOR_POSITIVO, true, EnumSexo.FEMENINO);
        Licencia l1 = new Licencia(t1, EnumClaseLicencia.CLASE_F,
                LocalDate.of(2020, Month.MARCH, 19),
                LocalDate.of(2025, Month.MARCH, 19));
        int id = -1;
        try {
            id = dao.save(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DTOEmitirLicencia dto = new DTOEmitirLicencia();
        dto.setIdTitular(id);
        dto.setFechaNacimiento(t1.getFechaNacimiento());
        dto.setNombre(t1.getNombre());
        dto.setApellido(t1.getApellido());
        dto.setTipoDocumento(t1.getTipoDNI());
        dto.setDocumento(t1.getDNI());
        dto.setCosto(59.0);
        dto.setObservaciones(null);
        dto.setClaseLicencia(l1.getClaseLicencia());
        double costoTotal = GestorLicencia.get().calcularCostoLicencia(dto);
        Assert.assertEquals(costoTotal, 67.0, 0);
    }

    @Test
    public void testImprimirLicencia() throws Exception {
        TitularDAO dao = new TitularDAOImpl();
        LicenciaDAO licdao = new LicenciaDAOImpl();
        DTOEmitirLicencia dtoEmitirLicencia = null;
        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "40266367", "Lozano", "Indiana",
                LocalDate.of(1067, Month.MARCH, 19), EnumGrupoSanguineo.GRUPO_A,
                EnumFactorRH.FACTOR_POSITIVO, true, EnumSexo.FEMENINO);
        Licencia l1 = new Licencia(t1, EnumClaseLicencia.CLASE_G,
                LocalDate.of(2020, Month.MARCH, 19),
                LocalDate.of(2025, Month.MARCH, 19));
        int id = -1;
        id = licdao.save(l1);

        int idTitular = -1;
        idTitular = dao.save(t1);

        DTOImprimirLicencia dto = new DTOImprimirLicencia(l1.getId(), t1.getId(), l1.getClaseLicencia(),
                l1.getFechaEmision(), l1.getFechaVencimiento(), l1.getObservaciones(), l1.getCosto());
    }


}
