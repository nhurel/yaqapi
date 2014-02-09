package me.hurel.hqlbuilder.builder;

public class ConditionHibernateQueryBuilder<T> extends HibernateQueryBuilder {

    final T value;

    final String operator;

    ConditionHibernateQueryBuilder(HibernateQueryBuilder root, OPERATOR operator, T value) {
	super(root);
	this.value = value;
	this.operator = operator.operator;
    }

    ConditionHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, T value) {
	super(root);
	this.value = value;
	this.operator = separator.separator;
    }

    public <U> WhereHibernateQueryBuilder<U> and(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall));
    }

    public <U> WhereHibernateQueryBuilder<U> or(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
