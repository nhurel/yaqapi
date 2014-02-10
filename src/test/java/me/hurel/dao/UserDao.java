package me.hurel.dao;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import me.hurel.entity.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
public class UserDao {

    // private static final Configuration configuration;

    private static final SessionFactory sessionFactory;

    static {
        // configuration = new Configuration();
        // configuration.configure("hibernate.cfg.xml");
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties())
                .build());
    }

    public User getUserByFirstName(String firstName) {
        User user = queryOn(new User());
        try {
            return (User) select(user).from(user).where(user.getFirstName()).isEqualTo(firstName).build(getSessionfactory().openSession())
                    .uniqueResult();
        } finally {
            getSessionfactory().getCurrentSession().close();
        }
    }

    public static SessionFactory getSessionfactory() {
        return sessionFactory;
    }


}
