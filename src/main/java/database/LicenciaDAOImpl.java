package database;


import dto.DTOImprimirLicencia;
import model.Licencia;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

public class LicenciaDAOImpl extends BaseDAOImpl<Licencia,Integer> implements LicenciaDAO{

    private Session session;

    public LicenciaDAOImpl() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    @Override
    public List<DTOImprimirLicencia> createListDTOimprimirLic(String argumentos) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();

        String consulta = "SELECT new dto.DTOImprimirLicencia(l.id , t.id ,l.claseLicencia, l.fechaEmision,l.fechaVencimiento, l.observaciones) FROM Licencia l, Titular t "
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
    @Override
        public List<DTOImprimirLicencia> createListDTOimprimirLicsinTitular(String argumentos) {

            session = HibernateUtil.getSessionFactory().getCurrentSession();

            String consulta = "SELECT new dto.DTOImprimirLicencia(l.id , l.titular ,l.claseLicencia, l.fechaEmision,l.fechaVencimiento, l.observaciones) FROM Licencia l , Titular t"
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
}
