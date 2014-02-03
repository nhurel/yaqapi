package me.hurel.hqlbuilder.builder;

import org.hibernate.Session;

public class UnfinishedSelectHibernateQueryBuilder extends UnfinishedHibernateQueryBuilder implements HQBSelect {

    private String[] aliases;
    private boolean initialised;

    UnfinishedSelectHibernateQueryBuilder(Session session) {
	super(session);
	initialised = false;
    }

    UnfinishedSelectHibernateQueryBuilder(Session session, String... aliases) {
	super(session);
	this.aliases = aliases;
	initialised = aliases != null;
    }

    public FromHibernateQueryBuilder from(Class<?> entity) {
	if (initialised) {
	    return new SelectHibernateQueryBuilder(session, aliases).from(entity);
	} else {
	    return new SelectHibernateQueryBuilder(session, toAlias(entity)).from(entity);
	}
    }

    public FromHibernateQueryBuilder from(Class<?> entity, String alias) {
	if (initialised) {
	    return new SelectHibernateQueryBuilder(session, aliases).from(entity, alias);
	} else {
	    return new SelectHibernateQueryBuilder(session, alias).from(entity, alias);
	}
    }
}
