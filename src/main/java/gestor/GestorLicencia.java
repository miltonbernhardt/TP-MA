package gestor;

import enumeration.EnumClaseLicencia;
import hibernate.DAO;
import model.Titular;

import java.util.ArrayList;

public class GestorLicencia {

    private static GestorLicencia instanciaGestor = null;

    private GestorLicencia() {}

    public static GestorLicencia get() {
        if (instanciaGestor == null){
            instanciaGestor = new GestorLicencia();
        }
        return instanciaGestor;
    }

    /*
        Trae desde la Base de Datos, las clases de licencias que tiene permitido solicitar
        el titular actual en pantalla.
     */
    public ArrayList<EnumClaseLicencia> getClasesLicencias(Integer idTitular){

        ArrayList<EnumClaseLicencia> licencias = new ArrayList<EnumClaseLicencia>();
        Titular titular = (Titular)DAO.get().get(Titular.class, idTitular);


        return licencias;
    }

    public double calcularCostoLicencia(){
    return 0;
    }
}
