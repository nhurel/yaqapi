package me.hurel.hqlbuilder.builder;

public class InConditionHibernateQueryBuilder<T> extends ConditionHibernateQueryBuilder<T> {

    final T[] values;

    InConditionHibernateQueryBuilder(HibernateQueryBuilder root, OPERATOR operator, T... values) {
	super(root, operator, null);
	this.values = values;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
