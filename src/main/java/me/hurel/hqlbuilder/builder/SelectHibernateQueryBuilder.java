package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.SelectClause;
import me.hurel.hqlbuilder.builder.AbstractFromQueryBuilder.JOIN;

public class SelectHibernateQueryBuilder extends HibernateQueryBuilder implements SelectClause {

    Object[] aliases;

    boolean distinct = false;

    SelectHibernateQueryBuilder(Object... selects) {
	this.aliases = selects;
	chain(this);
    }

    public FromHibernateQueryBuilder from(Object entity) {
	return chain(new FromHibernateQueryBuilder(this, JOIN.FROM, entity));
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
