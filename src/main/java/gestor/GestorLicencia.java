package gestor;
import exceptions.MenorDeEdadException;
import hibernate.DAO;
import model.Titular;
import model.Vigencia;
import model.Licencia;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

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
Calcular vigencia recibe como parametro la fecha de nacimiento del Titular y su id, retorna
un objeto Vigencia con la cantidad de años de la vigencia y la fecha de vencimiento.
 */
    public static Vigencia calcularVigencia(LocalDate nacimiento, int id_titular)throws MenorDeEdadException {
        Vigencia vigencia = new Vigencia();
        int years = obtenerEdad(nacimiento);
        if(years<17){
            throw new MenorDeEdadException();
        }else{
            if(years < 21){
                String sql = "select licencia.id from Licencia licencia WHERE licencia.titular = " + Integer.toString(id_titular) ;

                DAO dao = DAO.get();
                List<Licencia> lista = (List<Licencia>) dao.getResultList(sql, Licencia.class);

                if(lista.isEmpty() || lista == null){
                    vigencia.setVigencia(1);
                    vigencia.setFechaVencimiento(nacimiento.plusYears(years+1));
                } else {
                    vigencia.setVigencia(3);
                    vigencia.setFechaVencimiento(nacimiento.plusYears(years+3));
                }

            } else if( years < 46){
                vigencia.setVigencia(5);
                vigencia.setFechaVencimiento(nacimiento.plusYears(years+5));
            } else if( years < 60){
                vigencia.setVigencia(4);
                vigencia.setFechaVencimiento(nacimiento.plusYears(years+4));
            }  else if( years < 70){
                vigencia.setVigencia(3);
                vigencia.setFechaVencimiento(nacimiento.plusYears(years+3));
            } else if( years > 70){
                vigencia.setVigencia(1);
                vigencia.setFechaVencimiento(nacimiento.plusYears(years+1));
            }
        }

        return vigencia;
    }

    private static int obtenerEdad(LocalDate nacimiento){
        LocalDate now = LocalDate.now();
        Period diff = Period.between(nacimiento,now);
        return diff.getYears();
    }
    public double calcularCostoLicencia(){
    return 0.0;
    }
}
