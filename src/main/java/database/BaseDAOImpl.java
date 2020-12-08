package database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.NoResultException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;

public class BaseDAOImpl <T, E extends Serializable> implements BaseDAO<T, E>{
    private Session session;

    public BaseDAOImpl() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    @Override
    public Integer save(T instance) throws HibernateException {
        Integer id;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            id = (Integer) session.save(instance);
            session.getTransaction().commit();
        }
        catch (HibernateException exception) {
            session.getTransaction().rollback();
            exception.printStackTrace();
            throw exception;
        }

        return id;
    }

    @Override
    public void update(T instance) throws HibernateException {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            session.merge(instance);
            session.getTransaction().commit();
        }
        catch (HibernateException exception) {
            session.getTransaction().rollback();
            exception.printStackTrace();
            throw exception;
        }
    }


    @Override
    public void delete(T persistentInstance) throws HibernateException {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            session.delete(persistentInstance);
            session.getTransaction().commit();
        }
        catch (HibernateException exception) {
            session.getTransaction().rollback();
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public T findById(E id) throws HibernateException {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            return session.get(getEntityClass(), id);
        }
        catch (Exception exception) {
            session.getTransaction().rollback();
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public T findByQuery(String consultaSql) throws HibernateException {
        session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            return session.createQuery(consultaSql, getEntityClass()).getSingleResult();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public List<T> findAll() throws HibernateException {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            Query<T> query = session.createQuery("SELECT e FROM " + getEntityClass().getName() + " e");
            return query.list();
        }
        catch (Exception exception) {
            session.getTransaction().rollback();
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public List<T> findAllByQuery(String consultaSql) throws HibernateException {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            return session.createQuery(consultaSql, getEntityClass()).getResultList();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public Integer getCantidad(String consultaSql) throws NoResultException {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            int i = 0;
            Object o = null;
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            o = session.createSQLQuery(consultaSql).getSingleResult();
            if(o != null) {
                i = ((BigInteger) o).intValue();
            }
            return i;
        }
        catch (NoResultException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
