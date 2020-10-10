package hibernate;

import java.util.List;

import enumeration.EnumTipoAlerta;
import model.Licencia;
import model.Titular;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import app.PanelAlerta;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.stat.SessionStatistics;

public class DAO {

    private static DAO instanciaDAO = null;

    private DAO() {}

    public static DAO get() {
        if (instanciaDAO == null){
            instanciaDAO = new DAO();
        }
        return instanciaDAO;
    }

    /**
     * Save busca persistir objetoAGuardar que se le pase como párametro.
     * Retorna true si la operació fue exitosa, false en caso contrario.
     * @param objetoAGuardar
     */
    public Boolean save(Object objetoAGuardar) {
        Boolean valido = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            session.save(objetoAGuardar);
            session.getTransaction().commit();
        }
        catch (HibernateException exception) {
            valido = false;
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo guardar en la base de datos.", exception);
            exception.printStackTrace();
            session.getTransaction().rollback();
        }

        return valido;
    }

    /**
     * Update busca hacer actualizar objetoAActualizar si está en la base de datos.
     * Retorna true si la operació fue exitosa, false en caso contrario.
     * @param objetoAActualizar
     */
    public Boolean update(Object objetoAActualizar) {
        Boolean valido = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            session.merge(objetoAActualizar);
            session.getTransaction().commit();
        }
        catch (HibernateException exception) {
            valido = false;
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo actualizar en la base de datos.", exception);
            session.getTransaction().rollback();
        }

        return valido;
    }

    /**
     * Get retorna el objeto con el idObjeto pasado por parametro.
     * Retorna true si la operació fue exitosa, false en caso contrario.
     * claseObjeto sirve para obtener el objeto correcto, basta con pasar "nombre de la clase".class
     * @param claseObjeto
     * @param idObjeto
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object get(Class claseObjeto, Integer idObjeto) {
        Object tipo = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            tipo = session.get(claseObjeto, idObjeto);
        }
        catch (HibernateException exception) {
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo obtener el objeto desde la base de datos.", exception);
        }

        return tipo;
    }

    /**
     * Función que retorna un objeto, resultado de la consultaSQL que se le
     * pasó como párametro.
     * claseObjeto sirve para obtener el objeto correcto, basta con pasar "nombre de la clase".class
     * Ej de consultaSQL "select l from Licencia l where l.id_titular=idTitular"
     * @param consultaSQL
     * @param claseObjeto
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object getSingleResult(String consultaSQL, Class claseObjeto) {
        Object objeto = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            objeto = session.createQuery(consultaSQL, claseObjeto).getSingleResult();
        }
        catch (HibernateException exception) {
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo obtener el objeto desde la base de datos.", exception);
        }

        return objeto;
    }

    /**
     * Retorna un List que es el resultado de la consultaSQL pasada como párametro.
     * claseObjetos sirve para obtener los objetos correctos, basta con pasar "nombre de la clase".class
     * Ej de consultaSQL "select l from Licencia l where l.id_titular=idTitular"
     * @param consultaSQL
     * @param claseObjetos
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<? extends Object> getResultList(String consultaSQL, Class claseObjetos) {
        List<? extends Object> lista = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            lista = session.createQuery(consultaSQL, claseObjetos).getResultList();
        }
        catch (HibernateException exception) {
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo obtener la lista de objetos desde la base de datos.", exception);
        }

        return lista;
    }
}
