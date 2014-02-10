package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.builder.HibernateQueryBuilder.OPERATOR;
import me.hurel.hqlbuilder.builder.HibernateQueryBuilder.SEPARATOR;
import me.hurel.hqlbuilder.functions.Function;

public class HavingHibernateQueryBuilder<T> extends UnfinishedHibernateQueryBuilder {
    final Object value;

    final String operator;

    HavingHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, T entity) {
	super(root);
	this.value = entity;
	this.operator = separator.separator;
    }

    HavingHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, Function<T> entity) {
	super(root);
	this.value = entity;
	this.operator = separator.separator;
    }

    public NullConditionHibernateQueryBuilder<T> isNull() {
	return chain(new NullConditionHibernateQueryBuilder<T>(this));

    }

    public NullConditionHibernateQueryBuilder<T> isNotNull() {
	return chain(new NullConditionHibernateQueryBuilder<T>(this, false));
    }

    public ConditionHibernateQueryBuilder<T> isEqualTo(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isNotEqualTo(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.NOT_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isGreaterThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.GREATER, entity));
    }

    public ConditionHibernateQueryBuilder<T> isGreaterEqualThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.GREATER_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLessThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.LESS, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLessEqualThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.LESS_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLike(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.LIKE, entity));
    }

    public ConditionHibernateQueryBuilder<T> isNotLike(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this.root, OPERATOR.NOT_LIKE, entity));
    }

    public ConditionHibernateQueryBuilder<T> isIn(T... values) {
	return chain(new InConditionHibernateQueryBuilder<T>(this.root, OPERATOR.IN, values));
    }

    public ConditionHibernateQueryBuilder<T> isNotIn(T... values) {
	return chain(new InConditionHibernateQueryBuilder<T>(this.root, OPERATOR.NOT_IN, values));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }
}
