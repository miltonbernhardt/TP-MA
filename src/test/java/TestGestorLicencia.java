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
    public void calcularVigencia_1anio() throws MenorDeEdadException {
        LocalDate nacimiento = LocalDate.parse("25-06-2002", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("25-06-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 0);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 1);
        System.out.println(vigencia.getFechaVencimiento());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
    }

    @Test
    public void calcularVigencia_3anios() throws MenorDeEdadException {
        LocalDate nacimiento = LocalDate.parse("21-03-2000", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("21-03-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 100);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 3);
        System.out.println(vigencia.getFechaVencimiento());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
    }

    @Test
    public void calcularVigencia_5anios() throws MenorDeEdadException {
        LocalDate nacimiento = LocalDate.parse("08-07-1996", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("08-07-2025", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento, 111);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 5);
        System.out.println(vigencia.getFechaVencimiento());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);
    }

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

}
