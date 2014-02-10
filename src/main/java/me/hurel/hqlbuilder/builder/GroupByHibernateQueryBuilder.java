package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.functions.Function;

public class GroupByHibernateQueryBuilder extends HibernateQueryBuilder {

    final Object[] properties;

    GroupByHibernateQueryBuilder(HibernateQueryBuilder root, Object... properties) {
	super(root);
	this.properties = properties;
    }

    public <T> WhereHibernateQueryBuilder<T> having(T property) {
	return chain(new WhereHibernateQueryBuilder<T>(this.root, SEPARATOR.HAVING, property));
    }

    public <T> WhereHibernateQueryBuilder<T> having(Function<T> property) {
	return chain(new WhereHibernateQueryBuilder<T>(this.root, SEPARATOR.HAVING, property));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
