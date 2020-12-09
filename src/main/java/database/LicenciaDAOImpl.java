package database;

import dto.DTOImprimirLicencia;
import dto.DTOLicenciaExpirada;
import dto.DTOLicenciasVigentes;
import model.Licencia;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.time.LocalDate;
import java.util.List;

public class LicenciaDAOImpl extends BaseDAOImpl<Licencia,Integer> implements LicenciaDAO{

    private static Session session;

    public LicenciaDAOImpl() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    @Override
    public List<DTOLicenciasVigentes> createListDTOLicenciasVigentes(Integer id_titular){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        String hoy = LocalDate.now().toString();
        String consulta = "SELECT new dto.DTOLicenciasVigentes(l.id, l.claseLicencia, l.fechaEmision, l.fechaVencimiento, l.observaciones, l.costo, l.titular.id) FROM Licencia l WHERE l.titular.id='"+id_titular.toString()+"' AND l.fechaVencimiento > '"+hoy+"'";

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            return session.createQuery(consulta, DTOLicenciasVigentes.class).getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public List<DTOImprimirLicencia> createListDTOimprimirLic(String argumentos) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();

        String consulta = "SELECT new dto.DTOImprimirLicencia(l.id , t.id ,l.claseLicencia, l.fechaEmision,l.fechaVencimiento, l.observaciones, l.costo) FROM Licencia l, Titular t "
                + argumentos+ " ORDER BY l.id ASC ";

        System.out.println("ultima consulta es"  + consulta);
        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            return session.createQuery(consulta, DTOImprimirLicencia.class).getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
    //ToDO borrar o no?
    @Override
    public List<DTOImprimirLicencia> createListDTOimprimirLicsinTitular(String s) {
        return null;
    }

    /* @Override
         public List<DTOImprimirLicencia> createListDTOimprimirLicsinTitular(String argumentos) {

             session = HibernateUtil.getSessionFactory().getCurrentSession();

             String consulta = "SELECT new dto.DTOImprimirLicencia(l.id , l.titular ,l.claseLicencia, l.fechaEmision,l.fechaVencimiento, l.observaciones , ) FROM Licencia l , Titular t"
                     + argumentos+ " ORDER BY l.id ASC ";

             System.out.println("ultima consulta es"  + consulta);
             try {
                 if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                     session.beginTransaction();
                 return session.createQuery(consulta, DTOImprimirLicencia.class).getResultList();
             } catch (Exception exception) {
                 exception.printStackTrace();
                 throw exception;
             }
         }
 */
    public static List<DTOLicenciaExpirada> createListDTOLicenciaExpirada(String argumentos) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
//        (l.id, t.apellido, t.nombre, t.tipoDNI, t.DNI, l.claseLicencia, l.fechaVencimiento)

        String consulta = "SELECT new dto.DTOLicenciaExpirada(l.id, t.apellido, t.nombre, t.tipoDNI, t.DNI, l.claseLicencia, l.fechaVencimiento) FROM Licencia l JOIN Titular t ON (l.titular = t.id)"
                + argumentos;
        System.out.println("ultima consulta es"  + consulta);
        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            return session.createQuery(consulta, DTOLicenciaExpirada.class).getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

}
