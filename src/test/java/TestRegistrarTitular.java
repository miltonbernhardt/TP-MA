import dto.DTOAltaTitular;
import enumeration.EnumFactorRH;
import enumeration.EnumGrupoSanguineo;
import enumeration.EnumSexo;
import enumeration.EnumTipoDocumento;
import hibernate.DAO;
import model.Titular;
import org.junit.Assert;
import org.junit.Test;
import gestor.GestorTitular;

import java.time.LocalDate;
import java.util.Date;

public class TestRegistrarTitular {


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
