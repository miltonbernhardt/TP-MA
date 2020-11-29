import exceptions.MenorDeEdadException;
import gestor.GestorLicencia;
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
}
