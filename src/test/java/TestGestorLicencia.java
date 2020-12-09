import database.TitularDAO;
import database.TitularDAOImpl;
import dto.DTOEmitirLicencia;
import enumeration.*;
import gestor.GestorLicencia;
import model.Licencia;
import model.Titular;
import model.Vigencia;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TestGestorLicencia {

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
        //t1.getLicencias().add(l2);
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

    @Test
    public void calcularVigencia2_3anios(){
        LocalDate nacimiento = LocalDate.parse("08-08-2001", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("08-08-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 141);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
        Assert.assertEquals(vigencia.getVigencia(), 3);
        System.out.println(vigencia.getFechaVencimiento());
    }

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
    Emitir licencia de clase B para una persona de 23 años:
     */
    @Test
    public void emitirLicencia() {
        DTOEmitirLicencia dto = new DTOEmitirLicencia();
        dto.setIdTitular(122);
        dto.setFechaNacimiento(LocalDate.of(1997, 3, 19));
        dto.setNombre("INDIANA");
        dto.setApellido("LOZANO");
        dto.setTipoDocumento(EnumTipoDocumento.DNI);
        dto.setDocumento("40266367");
        dto.setCosto(48.0);
        dto.setObservaciones(null);
        dto.setClaseLicencia(EnumClaseLicencia.CLASE_B);
        boolean resultado = GestorLicencia.get().generarLicencia(dto);
        Assert.assertTrue(resultado);
    }
    /*
    	Emitir licencia de clase B para una persona de 23 años.
    	En este caso, la prueba falla dado que el idTitular pasado a
    	dto.setIdTitular no corresponde al idTitular de la persona en cuestión.
     */
    @Test
    public void emitirLicencia2() {
        DTOEmitirLicencia dto = new DTOEmitirLicencia();
        dto.setIdTitular(150);
        dto.setFechaNacimiento(LocalDate.of(1997, 3, 19));
        dto.setNombre("INDIANA");
        dto.setApellido("LOZANO");
        dto.setTipoDocumento(EnumTipoDocumento.DNI);
        dto.setDocumento("40266367");
        dto.setCosto(48.0);
        dto.setObservaciones(null);
        dto.setClaseLicencia(EnumClaseLicencia.CLASE_B);

        GestorLicencia gestorLicencia = GestorLicencia.get();
        boolean resultado = GestorLicencia.get().generarLicencia(dto);
        Assert.assertTrue(resultado);
    }

    /*
    	Emitir licencia de clase B para una persona menor de edad.
    	En este caso, la prueba falla.
     */
   @Test
    public void emitirLicencia3() {
        DTOEmitirLicencia dto = new DTOEmitirLicencia();
        dto.setIdTitular(150);
        dto.setFechaNacimiento(LocalDate.of(2008, 3, 19));
        dto.setNombre("INDIANA");
        dto.setApellido("PÉREZ");
        dto.setTipoDocumento(EnumTipoDocumento.DNI);
        dto.setDocumento("48755984");
        dto.setCosto(48.0);
        dto.setObservaciones(null);
        dto.setClaseLicencia(EnumClaseLicencia.CLASE_B);

        GestorLicencia gestorLicencia = GestorLicencia.get();
        boolean resultado = GestorLicencia.get().generarLicencia(dto);
        Assert.assertTrue(resultado);
    }
}
