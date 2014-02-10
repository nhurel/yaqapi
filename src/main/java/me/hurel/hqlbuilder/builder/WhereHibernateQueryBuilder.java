package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.builder.HibernateQueryBuilder.OPERATOR;
import me.hurel.hqlbuilder.builder.HibernateQueryBuilder.SEPARATOR;
import me.hurel.hqlbuilder.functions.Function;

public class WhereHibernateQueryBuilder<T> extends HavingHibernateQueryBuilder<T> {

    WhereHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, T entity) {
	super(root, separator, entity);
    }

    WhereHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, Function<T> entity) {
	super(root, separator, entity);
    }

    public WhereNullConditionHibernateQueryBuilder<T> isNull() {
	return chain(new WhereNullConditionHibernateQueryBuilder<T>(this));

    }

    public WhereNullConditionHibernateQueryBuilder<T> isNotNull() {
	return chain(new WhereNullConditionHibernateQueryBuilder<T>(this, false));
    }

    public ConditionHibernateQueryBuilder<T> isEqualTo(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.EQUAL, entity));
    }

    public WhereConditionHibernateQueryBuilder<T> isNotEqualTo(T entity) {
	return chain(new WhereConditionHibernateQueryBuilder<T>(this.root, OPERATOR.NOT_EQUAL, entity));
    }

    public WhereConditionHibernateQueryBuilder<T> isGreaterThan(T entity) {
	return chain(new WhereConditionHibernateQueryBuilder<T>(this.root, OPERATOR.GREATER, entity));
    }

    public WhereConditionHibernateQueryBuilder<T> isGreaterEqualThan(T entity) {
	return chain(new WhereConditionHibernateQueryBuilder<T>(this.root, OPERATOR.GREATER_EQUAL, entity));
    }

    public WhereConditionHibernateQueryBuilder<T> isLessThan(T entity) {
	return chain(new WhereConditionHibernateQueryBuilder<T>(this.root, OPERATOR.LESS, entity));
    }

    public WhereConditionHibernateQueryBuilder<T> isLessEqualThan(T entity) {
	return chain(new WhereConditionHibernateQueryBuilder<T>(this.root, OPERATOR.LESS_EQUAL, entity));
    }

    public WhereConditionHibernateQueryBuilder<T> isLike(T entity) {
	return chain(new WhereConditionHibernateQueryBuilder<T>(this.root, OPERATOR.LIKE, entity));
    }

    public WhereConditionHibernateQueryBuilder<T> isNotLike(T entity) {
	return chain(new WhereConditionHibernateQueryBuilder<T>(this.root, OPERATOR.NOT_LIKE, entity));
    }

    public WhereConditionHibernateQueryBuilder<T> isIn(T... values) {
	return chain(new WhereInConditionHibernateQueryBuilder<T>(this.root, OPERATOR.IN, values));
    }

    public WhereConditionHibernateQueryBuilder<T> isNotIn(T... values) {
	return chain(new WhereInConditionHibernateQueryBuilder<T>(this.root, OPERATOR.NOT_IN, values));
    }

}
