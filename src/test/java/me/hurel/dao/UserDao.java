package me.hurel.dao;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;

import java.util.List;

import me.hurel.entity.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class UserDao {

    private static final SessionFactory sessionFactory;

    static {
	Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
	sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
    }

    public User getUserByFirstName(String firstName) {
	User user = queryOn(new User());
	try {
	    return (User) select(user).from(user).where(user.getFirstName()).isEqualTo(firstName).build(getSessionfactory().openSession()).uniqueResult();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserHavingChildren() {
	User user = queryOn(new User());
	try {
	    return (List<User>) select(user).from(user).innerJoinFetch(user.getChildren()).where(size(user.getChildren())).isGreaterEqualThan(1)
		    .build(getSessionfactory().openSession()).list();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserHavingLittleChildren() {
	User user = queryOn(new User());
	User child = andQueryOn(new User());
	try {
	    return (List<User>) select(user).from(user).innerJoinFetch(user.getChildren()).whereExists(distinct(child.getId())).from(child).where(child.getAge())
		    .isLessEqualThan(2).and(child.getFather().getId()).isEqualTo(user.getId()).closeExists().build(getSessionfactory().openSession()).list();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserHavingChildrenHavingLittleChildren() {
	User user = queryOn(new User());
	User child = andQueryOn(new User());
	User littleChild = andQueryOn(new User());
	try {
	    return (List<User>) select(user).from(user).innerJoinFetch(user.getChildren()).whereExists(distinct(child.getId())).from(child)
		    .whereExists(distinct(littleChild.getId())).from(littleChild).where(littleChild.getAge()).isLessEqualThan(2).and(littleChild.getFather().getId())
		    .isEqualTo(child.getId()).closeExists().and(child.getFather().getId()).isEqualTo(user.getId()).closeExists().build(getSessionfactory().openSession()).list();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

    public static SessionFactory getSessionfactory() {
	return sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public String getUserNameHavingMaxChildren() {
	User user = queryOn(new User());
	List<Object[]> result = null;
	try {
	    result = select(user.getLastName(), count("*")).from(user).groupBy(user.getLastName()).orderBy(count("*")).desc().build(getSessionfactory().openSession()).list();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}

	return (String) result.get(0)[0]; // get the first colum of the first
					  // result
    }

}
