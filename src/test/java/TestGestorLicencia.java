import dto.DTOEmitirLicencia;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;
import gestor.GestorLicencia;
import model.Vigencia;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestGestorLicencia {

    @Test
    public void calcularVigencia_1anio() {
        LocalDate nacimiento = LocalDate.parse("25-06-2002", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("25-06-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 0);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 1);
        System.out.println(vigencia.getFechaVencimiento());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
    }

    @Test
    public void calcularVigencia_3anios() {
        LocalDate nacimiento = LocalDate.parse("21-03-2000", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("21-03-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 100);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 3);
        System.out.println(vigencia.getFechaVencimiento());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
    }

    @Test
    public void calcularVigencia_5anios() {
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
