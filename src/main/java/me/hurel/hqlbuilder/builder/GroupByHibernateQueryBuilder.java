/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.GroupByClause;
import me.hurel.hqlbuilder.OrderByClause;
import me.hurel.hqlbuilder.WhereClause;
import me.hurel.hqlbuilder.functions.Function;

public class GroupByHibernateQueryBuilder extends HibernateQueryBuilder implements GroupByClause {

    final Object[] properties;

    GroupByHibernateQueryBuilder(HibernateQueryBuilder root, Object... properties) {
	super(root);
	this.properties = properties;
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.GroupByClause#having(T)
     */
    public <T> WhereClause<T> having(T property) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.HAVING, property));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * me.hurel.hqlbuilder.builder.GroupByClause#having(me.hurel.hqlbuilder.
     * functions.Function)
     */
    public <T> WhereClause<T> having(Function<T> property) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.HAVING, property));
    }

    public OrderByClause orderBy(Object... orders) {
	return chain(new OrderByHibernateQueryBuilder(this, SEPARATOR.ORDER_BY, orders));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
