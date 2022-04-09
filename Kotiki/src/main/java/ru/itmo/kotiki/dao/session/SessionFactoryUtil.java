package ru.itmo.kotiki.dao.session;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.itmo.kotiki.dao.model.CatsEntity;
import ru.itmo.kotiki.dao.model.FriendsOfCatsEntity;
import ru.itmo.kotiki.dao.model.OwnersEntity;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(CatsEntity.class);
                configuration.addAnnotatedClass(OwnersEntity.class);
                configuration.addAnnotatedClass(FriendsOfCatsEntity.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Exception : " + e.getLocalizedMessage());
            }
        }
        return sessionFactory;
    }
}
