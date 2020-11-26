package database;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;


public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeBaseDatos() {
        registry.close();
        sessionFactory.close();
    }


    /**
     * true si se quiere mostrar la consola de Hibernate
     * false si no se quiere mostrar la consola de Hibernate
     * @param apagamos
     */
    public static void apagarLog(Boolean apagamos) {
        if(apagamos) {
            Logger log = Logger.getLogger("org.hibernate");
            log.setLevel(Level.OFF);
        }
    }
}
