package me.hurel.hqlbuilder.builder;

import org.hibernate.Session;

public class SelectHibernateQueryBuilder extends HibernateQueryBuilder implements HQBSelect {

    String[] aliases;

    SelectHibernateQueryBuilder(Session session, String... selects) {
	super(session);
	this.aliases = selects;
	chain(this);
    }

    public FromHibernateQueryBuilder from(Class<?> entity) {
	return from(entity, toAlias(entity));
    }

    public FromHibernateQueryBuilder from(Class<?> entity, String alias) {
	return chain(new FromHibernateQueryBuilder(this, entity, alias));
    }

    @Override
    public void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
