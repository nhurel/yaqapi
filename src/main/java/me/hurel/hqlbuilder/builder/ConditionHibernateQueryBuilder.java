/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.*;
import me.hurel.hqlbuilder.functions.Function;

public class ConditionHibernateQueryBuilder<T> extends AbstractFromQueryBuilder implements Condition<T>, WithCondition<T>, WhenCondition<T> {

    final Object value;

    final String operator;

    int closeGroup = 0;
    int closeExists = 0;

    ConditionHibernateQueryBuilder(HibernateQueryBuilder root, OPERATOR operator, T value) {
	super(root);
	this.value = getAliasedObject(value);
	this.operator = operator.operator;
    }

    ConditionHibernateQueryBuilder(HibernateQueryBuilder root, OPERATOR operator, Function<T> value) {
	super(root);
	this.value = value;
	this.operator = operator.operator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.Condition#and(U)
     */
    public <U> WhereHibernateQueryBuilder<U> and(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall));
    }

    public <U> WhereHibernateQueryBuilder<U> and(Function<U> methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall));
    }

    public <U> WhereHibernateQueryBuilder<U> and(CaseWhenClause<U> methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.Condition#or(U)
     */
    public <U> WhereHibernateQueryBuilder<U> or(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall));
    }

    public <U> WhereHibernateQueryBuilder<U> or(Function<U> methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall));
    }

    public <U> WhereHibernateQueryBuilder<U> or(CaseWhenClause<U> methodCall) {
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

    public <U> WhereHibernateQueryBuilder<U> orGroup(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall).group());
    }

    public <U> WhereHibernateQueryBuilder<U> orGroup(Function<U> methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall).group());
    }

    public <U> WhereHibernateQueryBuilder<U> orGroup(CaseWhenClause<U> methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.OR, methodCall).group());
    }

    public <U> WhereHibernateQueryBuilder<U> andGroup(U methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall).group());
    }

    public <U> WhereHibernateQueryBuilder<U> andGroup(Function<U> methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall).group());
    }

    public <U> WhereHibernateQueryBuilder<U> andGroup(CaseWhenClause<U> methodCall) {
	return chain(new WhereHibernateQueryBuilder<U>(this, SEPARATOR.AND, methodCall).group());
    }

    public ExistsClause andExists(Object methodCall) {
	return chain(new ExistsHibernateQueryBuilder(this, SEPARATOR.AND, methodCall));
    }

    public ExistsClause andNotExists(Object methodCall) {
	return chain(new ExistsHibernateQueryBuilder(this, SEPARATOR.AND, methodCall).not());
    }

    public ExistsClause orExists(Object methodCall) {
	return chain(new ExistsHibernateQueryBuilder(this, SEPARATOR.OR, methodCall));
    }

    public ExistsClause orNotExists(Object methodCall) {
	return chain(new ExistsHibernateQueryBuilder(this, SEPARATOR.OR, methodCall).not());
    }

    public ConditionHibernateQueryBuilder<T> closeGroup() {
	this.closeGroup++;
	return this;
    }

    public UnfinishedCaseWhenQueryBuilder then(Object value) {
	return chain(new UnfinishedCaseWhenQueryBuilder(this, value));
    }

    public WithCondition<T> closeExists() {
	this.closeExists++;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
