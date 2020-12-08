import dto.DTOAltaTitular;
import dto.DTOEmitirLicencia;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoDocumento;
import exceptions.MenorDeEdadException;
import gestor.GestorLicencia;
import gestor.GestorTitular;
import model.Vigencia;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestGestorLicencia {

    @Test
    public void calcularVigencia_1anio() {
        LocalDate nacimiento = LocalDate.parse("08-08-2001", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("08-08-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 141);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 1);
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
    public void emitirLicencia() throws MenorDeEdadException {
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
    public void emitirLicencia2() throws MenorDeEdadException {
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
    public void emitirLicencia3() throws MenorDeEdadException {
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
