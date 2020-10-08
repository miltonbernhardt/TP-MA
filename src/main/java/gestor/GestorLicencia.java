package gestor;
import hibernate.DAO;
import model.Titular;
import model.Vigencia;

public class GestorLicencia {


    private static GestorLicencia instanciaGestor = null;

    private GestorLicencia() {}

    public static GestorLicencia get() {
        if (instanciaGestor == null){
            instanciaGestor = new GestorLicencia();
        }
        return instanciaGestor;
    }



    public static Vigencia calcularVigencia(Titular titular){

        Vigencia vigencia = new Vigencia();



        return vigencia;
    }

}
