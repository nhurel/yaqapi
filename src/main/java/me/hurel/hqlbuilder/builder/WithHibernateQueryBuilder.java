package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.functions.Function;

public class WithHibernateQueryBuilder<T> extends WhereHibernateQueryBuilder<T> {

    WithHibernateQueryBuilder(HibernateQueryBuilder root, T entity) {
	super(root, SEPARATOR.WITH, entity);
    }

    WithHibernateQueryBuilder(HibernateQueryBuilder root, Function<T> function) {
	super(root, SEPARATOR.WITH, function);
    }

}
