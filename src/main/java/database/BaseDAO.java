package database;

import java.io.Serializable;
import java.util.List;

// T, es el tipo del objeto mientras que E es el tipo del identificador
public interface BaseDAO<T, E extends Serializable> {

    /** "save" persiste la instancia en la base de datos.
        Retorna el ID de la entidad.
        En caso de fallo lanza una Exception.
        @param instance entidad a persistir. */
    Integer save(T instance) throws Exception;

    /** "update" actualiza la instancia si está en la base de datos.
        En caso de fallo lanza una Exception.
        @param instance entidad a actulizar. */
    void update(T instance) throws Exception;

    /** "update" borra la instancia si está en la base de datos.
         * En caso de fallo lanza una Exception.
         * @param persistentInstance entidad a borrar. */
    void delete(T persistentInstance) throws Exception;

    /** "findById" busca una instancia de una entidad en la base de datos según su ID.
         * En caso de fallo lanza una Exception.
        @param id atributo clave para buscar la entidad. */
    T findById(E id) throws Exception;

    /** "getEntity" busca una instancia de una entidad en la base de datos.
         * En caso de fallo lanza una Exception.
         * @param consultaSql mediante la consulta se indica que instancia de una entidad se busca. */
    T findByQuery(String consultaSql) throws Exception;

    /** "findAll" busca todas las instancia de una entidad en la base de datos.
         * En caso de fallo lanza una Exception. */
    List<T> findAll() throws Exception;

    /** "getAllEntities" busca todas instancia de una entidad en la base de datos que cumplan con la consulta.
        En caso de fallo lanza una Exception.
        @param consultaSql Ej de consultaSQL "select l from Licencia l where l.titular=idTitular"  - usar los nombre de atributos de las clases JAVA. */
    List<T> findAllByQuery(String consultaSql) throws Exception;

    /** "getAllEntities" busca todas instancia de una entidad en la base de datos que cumplan con la consulta.
        En caso de fallo lanza una Exception.
        ------ Usar los nombre de atributos de las clases SQL. ----------
        @param consultaSql Ej de consultaSQL "select count(distinct id_licencia) from licencia WHERE id_titular = id_titular"*/
    Integer getCantidad(String consultaSql) throws Exception;
}
