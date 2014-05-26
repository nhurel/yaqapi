/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.CaseWhenClause;
import me.hurel.hqlbuilder.WhenClause;
import me.hurel.hqlbuilder.WhereClause;
import me.hurel.hqlbuilder.WithClause;
import me.hurel.hqlbuilder.functions.Function;

public class WhereHibernateQueryBuilder<T> extends HibernateQueryBuilder implements WhereClause<T>, WithClause<T>, WhenClause<T> {
    final Object value;

    final String operator;

    boolean group = false;

    WhereHibernateQueryBuilder(SEPARATOR separator, T entity) {
	super();
	this.operator = separator.separator;
	this.value = entity;
	chain(this);
    }

    WhereHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, T entity) {
	super(root);
	this.value = getAliasedObject(entity);
	this.operator = separator.separator;
    }

    WhereHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, Function<T> entity) {
	super(root);
	this.value = entity;
	this.operator = separator.separator;
    }

    WhereHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, CaseWhenClause<T> entity) {
	super(root);
	this.value = entity;
	this.operator = separator.separator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isNull()
     */
    public NullConditionHibernateQueryBuilder<T> isNull() {
	return chain(new NullConditionHibernateQueryBuilder<T>(this));

    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isNotNull()
     */
    public NullConditionHibernateQueryBuilder<T> isNotNull() {
	return chain(new NullConditionHibernateQueryBuilder<T>(this, false));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isEqualTo(T)
     */
    public ConditionHibernateQueryBuilder<T> isEqualTo(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isEqualTo(Function<T> function) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.EQUAL, function));
    }


    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isNotEqualTo(T)
     */
    public ConditionHibernateQueryBuilder<T> isNotEqualTo(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.NOT_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isNotEqualTo(Function<T> entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.NOT_EQUAL, entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isGreaterThan(T)
     */
    public ConditionHibernateQueryBuilder<T> isGreaterThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.GREATER, entity));
    }

    public ConditionHibernateQueryBuilder<T> isGreaterThan(Function<T> entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.GREATER, entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isGreaterEqualThan(T)
     */
    public ConditionHibernateQueryBuilder<T> isGreaterEqualThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.GREATER_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isGreaterEqualThan(Function<T> entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.GREATER_EQUAL, entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isLessThan(T)
     */
    public ConditionHibernateQueryBuilder<T> isLessThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LESS, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLessThan(Function<T> entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LESS, entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isLessEqualThan(T)
     */
    public ConditionHibernateQueryBuilder<T> isLessEqualThan(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LESS_EQUAL, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLessEqualThan(Function<T> entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LESS_EQUAL, entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isLike(T)
     */
    public ConditionHibernateQueryBuilder<T> isLike(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LIKE, entity));
    }

    public ConditionHibernateQueryBuilder<T> isLike(Function<T> entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.LIKE, entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isNotLike(T)
     */
    public ConditionHibernateQueryBuilder<T> isNotLike(T entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.NOT_LIKE, entity));
    }

    public ConditionHibernateQueryBuilder<T> isNotLike(Function<T> entity) {
	return chain(new ConditionHibernateQueryBuilder<T>(this, OPERATOR.NOT_LIKE, entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isIn(T)
     */
    public ConditionHibernateQueryBuilder<T> isIn(T... values) {
	return chain(new InConditionHibernateQueryBuilder<T>(this, OPERATOR.IN, values));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.WhereCLause#isNotIn(T)
     */
    public ConditionHibernateQueryBuilder<T> isNotIn(T... values) {
	return chain(new InConditionHibernateQueryBuilder<T>(this, OPERATOR.NOT_IN, values));
    }

    public WhereHibernateQueryBuilder<T> group() {
	this.group = true;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }
}
