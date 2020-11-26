package database;

import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.List;

// T, es el tipo del objeto mientras que E es el tipo del identificador
public interface BaseDAO<T, E extends Serializable> {

    /**
     * "save" persiste la instancia en la base de datos.
     * En caso de fallo lanza una HibernateException y muestra un panel con los detalles.
     * @param instance entidad a persistir.
     */
    public void save(T instance) throws HibernateException;

    /**
     * "update" actualiza la instancia si está en la base de datos.
     * En caso de fallo lanza una HibernateException y muestra un panel con los detalles.
     * @param instance entidad a actulizar.
     */
    public void update(T instance) throws HibernateException;

    /**
     * "update" borra la instancia si está en la base de datos.
     * En caso de fallo lanza una HibernateException y muestra un panel con los detalles.
     * @param persistentInstance entidad a borrar.
     */
    public void delete(T persistentInstance) throws HibernateException;

    /**
     * "findById" busca una instancia de una entidad en la base de datos según su ID.
     * En caso de fallo lanza una HibernateException y muestra un panel con los detalles.
     * @param id atributo clave para buscar la entidad.
     */
    public T findById(E id) throws HibernateException;

    /**
     * "getEntity" busca una instancia de una entidad en la base de datos.
     * En caso de fallo lanza una HibernateException y muestra un panel con los detalles.
     * @param consultaSql mediante la consulta se indica que instancia de una entidad se busca.
     */
    public T findByQuery(String consultaSql) throws HibernateException;

    /**
     * "findAll" busca todas las instancia de una entidad en la base de datos.
     * En caso de fallo lanza una HibernateException y muestra un panel con los detalles.
     */
    public List<T> findAll() throws HibernateException;

    /**
     * "getAllEntities" busca todas instancia de una entidad en la base de datos que cumplan con la consulta.
     * En caso de fallo lanza una HibernateException y muestra un panel con los detalles.
     * @param consultaSql Ej de consultaSQL "select l from Licencia l where l.titular=idTitular"  - usar los nombre de atributos de las clases JAVA.
     */
    public List<T> findAllByQuery(String consultaSql) throws HibernateException;

    /**
     *
     * "getAllEntities" busca todas instancia de una entidad en la base de datos que cumplan con la consulta.
     * En caso de fallo lanza una HibernateException y muestra un panel con los detalles.
     *
     * ------ Usar los nombre de atributos de las clases SQL. ----------
     *
     * @param consultaSql Ej de consultaSQL "select count(distinct id_licencia) from licencia WHERE id_titular = id_titular"
     */
    public Integer getCantidad(String consultaSql) throws HibernateException;
}
