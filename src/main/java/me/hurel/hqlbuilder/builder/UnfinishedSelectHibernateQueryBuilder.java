package me.hurel.hqlbuilder.builder;

public class UnfinishedSelectHibernateQueryBuilder extends UnfinishedHibernateQueryBuilder implements HQBSelect {

    Object[] aliases;

    boolean distinct = false;

    UnfinishedSelectHibernateQueryBuilder(Object methodCall) {
	this.aliases = new Object[] { methodCall };
    }

    UnfinishedSelectHibernateQueryBuilder(Object... methodCall) {
	this.aliases = methodCall;
    }

    public FromHibernateQueryBuilder from(Object entity) {
	SelectHibernateQueryBuilder select = new SelectHibernateQueryBuilder(aliases);
	if (distinct) {
	    select = select.distinct();
	}
	return select.from(entity);
    }

    public UnfinishedSelectHibernateQueryBuilder distinct() {
	distinct = true;
	return this;
    }

}
