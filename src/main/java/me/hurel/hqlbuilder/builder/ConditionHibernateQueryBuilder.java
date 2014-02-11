package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.Condition;
import me.hurel.hqlbuilder.GroupByClause;
import me.hurel.hqlbuilder.WhereClause;

public class ConditionHibernateQueryBuilder<T> extends HibernateQueryBuilder implements Condition<T> {

    final T value;

    final String operator;

    boolean closeGroup = false;

    ConditionHibernateQueryBuilder(HibernateQueryBuilder root, OPERATOR operator, T value) {
	super(root);
	this.value = value;
	this.operator = operator.operator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.Condition#and(U)
     */
    public <U> WhereClause<U> and(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.Condition#or(U)
     */
    public <U> WhereClause<U> or(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.Condition#groupBy(java.lang.Object)
     */
    public GroupByClause groupBy(Object... properties) {
	return chain(new GroupByHibernateQueryBuilder(this, properties));
    }

    public <U> WhereClause<U> orGroup(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall).group());
    }

    public <U> WhereClause<U> andGroup(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall).group());
    }

    public Condition<T> closeGroup() {
	this.closeGroup = true;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
