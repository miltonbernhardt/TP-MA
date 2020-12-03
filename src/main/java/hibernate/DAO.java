package hibernate;

import java.math.BigInteger;
import java.util.List;

import database.HibernateUtil;
import enumeration.EnumTipoAlerta;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import app.PanelAlerta;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 * @deprecated  Se crearon mejores DAOs para Licencia y Titular {@link database}
 */
@Deprecated
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
     * @param objetoAGuardar objeto a guardar
     */
    @Deprecated
    public Boolean save(Object objetoAGuardar) {
        boolean valido = true;
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
     * @param objetoAActualizar objeto a actulizar
     */
    @Deprecated
    public Boolean update(Object objetoAActualizar) {
        boolean valido = true;
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
     * @param claseObjeto sirve para obtener el objeto correcto, basta con pasar "nombre de la clase".class
     * @param idObjeto id del objeto en la base de datos
     */
    @Deprecated
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
     * @param consultaSQL Ej de consultaSQL "select l from Licencia l where l.titular=idTitular" - usar los nombre de atributos de las clases JAVA
     * @param claseObjeto sirve para obtener el objeto correcto, basta con pasar "nombre de la clase".class
     */
    @Deprecated
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
     * NO FUNCA, CREAR UNO QUE EN VEZ DE OBJETO USE LA ENTIDAD QUE QUIERAN
     * Retorna un List que es el resultado de la consultaSQL pasada como párametro.
     * @param consultaSQL Ej de consultaSQL "select l from Licencia l where l.titular=idTitular"  - usar los nombre de atributos de las clases JAVA
     * @param claseObjetos sirve para obtener los objetos correctos, basta con pasar "nombre de la clase".class
     */
    @Deprecated
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




    /**
     * Ej de uso - select count(distinct id_licencia) from licencia WHERE id_titular = id_titular;
     * Usar los nombre de atributos de las clases SQL
     */
    @Deprecated
    public Integer getCantidad(String consultaSQL) {
        Integer i = 0;
        Object o = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            o = session.createSQLQuery(consultaSQL).getSingleResult();
        }catch (javax.persistence.NoResultException exception) {
            PanelAlerta.get(EnumTipoAlerta.EXCEPCION,null,null,"No se pudo realizar la consulta deseada.", exception);
            o = null;
        }

        if(o != null) {

            i = ((BigInteger) o).intValue();

        }

        return i;
    }

}
