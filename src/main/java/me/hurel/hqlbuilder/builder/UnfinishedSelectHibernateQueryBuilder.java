package me.hurel.hqlbuilder.builder;

public class UnfinishedSelectHibernateQueryBuilder extends UnfinishedHibernateQueryBuilder implements HQBSelect {

    Object[] aliases;

    UnfinishedSelectHibernateQueryBuilder() {
    }

    UnfinishedSelectHibernateQueryBuilder(Object methodCall) {
	this.aliases = new Object[] { methodCall };
    }

    UnfinishedSelectHibernateQueryBuilder(Object... methodCall) {
	this.aliases = methodCall;
    }

    public FromHibernateQueryBuilder from(Object entity) {
	return new SelectHibernateQueryBuilder(aliases).from(entity);
    }

}
