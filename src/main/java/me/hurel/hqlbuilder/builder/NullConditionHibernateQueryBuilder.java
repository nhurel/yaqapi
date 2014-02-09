package me.hurel.hqlbuilder.builder;

public class NullConditionHibernateQueryBuilder<T> extends ConditionHibernateQueryBuilder<T> {

    public NullConditionHibernateQueryBuilder(WhereHibernateQueryBuilder<T> where) {
	this(where, true);
    }

    public NullConditionHibernateQueryBuilder(WhereHibernateQueryBuilder<T> where, boolean isNull) {
	super(where, isNull ? OPERATOR.IS_NULL : OPERATOR.IS_NOT_NULL, (T) null);
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
