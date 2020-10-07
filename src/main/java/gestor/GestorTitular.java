package gestor;

public class GestorTitular {
    private static GestorTitular instanciaGestor = null;

    private GestorTitular() {}

    public static GestorTitular get() {
        if (instanciaGestor == null){
            instanciaGestor = new GestorTitular();
        }
        return instanciaGestor;
    }


}
