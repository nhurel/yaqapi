package me.hurel.hqlbuilder.builder;

public class WhereNullConditionHibernateQueryBuilder<T> extends NullConditionHibernateQueryBuilder<T> {

    public WhereNullConditionHibernateQueryBuilder(HavingHibernateQueryBuilder<T> where) {
	super(where, true);
    }

    public WhereNullConditionHibernateQueryBuilder(HavingHibernateQueryBuilder<T> where, boolean isNull) {
	super(where, isNull);
    }

    public GroupByHibernateQueryBuilder groupBy(Object... properties) {
	return chain(new GroupByHibernateQueryBuilder(this, properties));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
