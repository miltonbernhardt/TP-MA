import exceptions.MenorDeEdadException;
import gestor.GestorLicencia;
import model.Vigencia;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestorLicenciaTest {

    @Test
    public void calcularVigencia() throws MenorDeEdadException {

        LocalDate nacimiento = LocalDate.parse("25-06-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate vencimiento = LocalDate.parse("25-06-2025", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Vigencia vigencia = GestorLicencia.calcularVigencia(nacimiento);
        System.out.println(vigencia.getVigencia());
        Assert.assertEquals(vigencia.getVigencia(), 5);
        System.out.println(vigencia.getFechaVencimiento());
        Assert.assertEquals(vigencia.getFechaVencimiento(), vencimiento);

    }
}
