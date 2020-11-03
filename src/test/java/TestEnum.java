import enumeration.EnumClaseLicencia;
import org.junit.Test;

public class TestEnum {

    @Test
    public void start() {

        EnumClaseLicencia c = EnumClaseLicencia.CLASE_F;
            switch(c)
            {
                case CLASE_A:
                    System.out.println("Clase A");
                    break;
                case CLASE_B:
                    break;
                case CLASE_C:
                    break;
                case CLASE_D:
                    break;
                case CLASE_E:
                    break;
                case CLASE_F:
                    break;
                case CLASE_G:
                    break;
                default :
                    // Declaraciones
            }
        }
    }

