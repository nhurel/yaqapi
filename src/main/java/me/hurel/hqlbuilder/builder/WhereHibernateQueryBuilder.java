package me.hurel.hqlbuilder.builder;

public class WhereHibernateQueryBuilder<T> extends ConditionHibernateQueryBuilder<T> {

    WhereHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, T entity) {
	super(root, separator, entity);
    }

    public NullConditionHibernateQueryBuilder<T> isNull() {
	return chain(new NullConditionHibernateQueryBuilder<T>(this));

    }

    public NullConditionHibernateQueryBuilder<T> isNotNull() {
	return chain(new NullConditionHibernateQueryBuilder<T>(this, false));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

    public ConditionHibernateQueryBuilder<T> isEqualTo(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isNotEqualTo(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.NOT_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isGreaterThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.GREATER, entity));
    }

    public ConditionHibernateQueryBuilder<T> isGreaterEqualThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.GREATER_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLessThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LESS, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLessEqualThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LESS_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLike(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LIKE, entity));
    }

    public ConditionHibernateQueryBuilder<T> isNotLike(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.NOT_LIKE, entity));
    }

}
