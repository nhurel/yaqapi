package me.hurel.hqlbuilder.builder;

public class WhereConditionHibernateQueryBuilder<T> extends ConditionHibernateQueryBuilder<T> {

    WhereConditionHibernateQueryBuilder(HibernateQueryBuilder root, OPERATOR operator, T value) {
	super(root, operator, value);
    }

    public GroupByHibernateQueryBuilder groupBy(Object... properties) {
	return chain(new GroupByHibernateQueryBuilder(this, properties));
    }
}
