package database;

import dto.DTOGestionTitular;
import dto.DTOModificarTitular;
import model.Titular;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import java.util.List;

public class TitularDAOImpl extends BaseDAOImpl<Titular,Integer> implements TitularDAO{

    private Session session;

    public TitularDAOImpl() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    @Override
    public List<DTOGestionTitular> createListDTOBuscarTitular(String argumentos) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        String consulta = "SELECT new dto.DTOGestionTitular(t.id, t.fechaNacimiento, t.nombre, t.apellido,t.tipoDNI, t.DNI, t.calle, t.numeroCalle, t.donanteOrganos, t.sexo) FROM Titular t "
                + argumentos + " ORDER BY t.nombre, t.apellido ASC ";

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            return session.createQuery(consulta, DTOGestionTitular.class).getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public void ModificarTitular(DTOModificarTitular dtoTitular) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            Titular titular = new Titular();

            titular.setNombre(dtoTitular.getNombre());
            titular.setApellido(dtoTitular.getApellido());
            titular.setCalle(dtoTitular.getCalle());
            titular.setNumeroCalle(dtoTitular.getNumeroCalle());
            titular.setSexo(dtoTitular.getSexo());
            titular.setDonanteOrganos(dtoTitular.getDonante());
            titular.setId(dtoTitular.getId());

            // save the student object
            String hql = "UPDATE Titular set nombre = :FirstName, apellido = :LastName, calle = :Calle, numeroCalle = :NroCalle, sexo = :Sexo, donanteOrganos = :Donante " + "WHERE id = :TitularId";
            Query query = session.createQuery(hql);
            query.setParameter("FirstName", titular.getNombre());
            query.setParameter("LastName", titular.getApellido());
            query.setParameter("Calle", titular.getCalle());
            query.setParameter("NroCalle", titular.getNumeroCalle());
            query.setParameter("Sexo", titular.getSexo());
            query.setParameter("Donante", titular.getDonanteOrganos());
            query.setParameter("TitularId", titular.getId());
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


}
