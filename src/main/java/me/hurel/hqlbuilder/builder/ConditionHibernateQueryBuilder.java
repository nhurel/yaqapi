package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.Condition;
import me.hurel.hqlbuilder.GroupByClause;
import me.hurel.hqlbuilder.WithClause;
import me.hurel.hqlbuilder.WithCondition;

public class ConditionHibernateQueryBuilder<T> extends AbstractFromQueryBuilder implements Condition<T>, WithCondition<T> {

    final T value;

    final String operator;

    int closeGroup = 0;

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
    public <U> WithClause<U> and(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.Condition#or(U)
     */
    public <U> WithClause<U> or(U methodCall) {
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

    public <U> WithClause<U> orGroup(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall).group());
    }

    public <U> WithClause<U> andGroup(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall).group());
    }

    public WithCondition<T> closeGroup() {
	this.closeGroup++;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
