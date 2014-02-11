package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.SelectClause;

public class SelectHibernateQueryBuilder extends HibernateQueryBuilder implements SelectClause {

    Object[] aliases;

    boolean distinct = false;

    SelectHibernateQueryBuilder(Object... selects) {
	this.aliases = selects;
	chain(this);
    }

    public FromHibernateQueryBuilder from(Object entity) {
	return chain(new FromHibernateQueryBuilder(this, entity));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

    public SelectHibernateQueryBuilder distinct() {
	this.distinct = true;
	return this;
    }

}
