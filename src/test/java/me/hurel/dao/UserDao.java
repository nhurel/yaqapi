/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.dao;

import me.hurel.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;

public class UserDao {

    private static final SessionFactory sessionFactory;

    static {
	Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
	sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
    }

    public static SessionFactory getSessionfactory() {
	return sessionFactory;
    }

    public User getUserByFirstName(String firstName) {
	User user = queryOn(User.class);
	try {
	    return (User) select(user).from(user).where(user.getFirstName()).isEqualTo(firstName).build(getSessionfactory().openSession()).uniqueResult();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserHavingChildren() {
	User user = queryOn(User.class);
	try {
	    return (List<User>) select(user).from(user).innerJoinFetch(user.getChildren()).where(size(user.getChildren())).isGreaterEqualThan(1)
			    .build(getSessionfactory().openSession()).list();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserHavingLittleChildren() {
	User user = queryOn(User.class);
	User child = andQueryOn(User.class);
	try {
	    return (List<User>) select(user).from(user).innerJoinFetch(user.getChildren()).whereExists(distinct(child.getId())).from(child).where(child.getAge())
			    .isLessEqualThan(2).and(child.getFather().getId()).isEqualTo(user.getId()).closeExists().build(getSessionfactory().openSession()).list();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserHavingChildrenHavingLittleChildren() {
	User user = queryOn(User.class);
	User child = andQueryOn(User.class);
	User littleChild = andQueryOn(User.class);
	try {
	    return (List<User>) select(user).from(user).innerJoinFetch(user.getChildren()).whereExists(distinct(child.getId())).from(child)
			    .whereExists(distinct(littleChild.getId())).from(littleChild).where(littleChild.getAge()).isLessEqualThan(2).and(littleChild.getFather().getId())
			    .isEqualTo(child.getId()).closeExists().and(child.getFather().getId()).isEqualTo(user.getId()).closeExists().build(getSessionfactory().openSession()).list();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

    @SuppressWarnings("unchecked")
    public String getUserNameHavingMaxChildren() {
	User user = queryOn(User.class);
	List<Object[]> result = null;
	try {
	    result = select(user.getLastName(), count("*")).from(user).groupBy(user.getLastName()).orderBy(count("*")).desc().build(getSessionfactory().openSession()).list();
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}

	return (String) result.get(0)[0]; // get the first colum of the first result
    }

    public boolean hasParent(String firstName) {
	User user = queryOn(User.class);
	Boolean result=null;
	try {
	    result =(Boolean) select(caseWhen(user.getFather().getId()).isNull().then(false).whenElse(true)).from(user).where(user.getFirstName()).isEqualTo(firstName).build(getSessionfactory().openSession()).uniqueResult();
	    return result;
	} finally {
	    getSessionfactory().getCurrentSession().close();
	}
    }

}
