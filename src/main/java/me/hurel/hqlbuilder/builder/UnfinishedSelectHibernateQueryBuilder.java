package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.FromClause;
import me.hurel.hqlbuilder.SelectClause;

public class UnfinishedSelectHibernateQueryBuilder extends UnfinishedHibernateQueryBuilder implements SelectClause {

    Object[] aliases;

    boolean distinct = false;

    UnfinishedSelectHibernateQueryBuilder(Object methodCall) {
	this.aliases = new Object[] { methodCall };
    }

    UnfinishedSelectHibernateQueryBuilder(Object... methodCall) {
	this.aliases = methodCall;
    }

    public FromClause from(Object entity) {
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
