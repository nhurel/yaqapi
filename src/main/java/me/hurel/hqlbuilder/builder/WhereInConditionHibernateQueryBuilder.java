package me.hurel.hqlbuilder.builder;


public class WhereInConditionHibernateQueryBuilder<T> extends WhereConditionHibernateQueryBuilder<T> {
    final T[] values;

    WhereInConditionHibernateQueryBuilder(HibernateQueryBuilder root, OPERATOR operator, T... values) {
	super(root, operator, null);
	this.values = values;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }
}
