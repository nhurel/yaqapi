/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.OrderByClause;
import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

public class OrderByHibernateQueryBuilder extends HibernateQueryBuilder implements OrderByClause {
    Object[] aliases;

    PRIORITY priority = null;

    String separator;

    OrderByHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, Object... orders) {
	super(root);
	this.aliases = HQBInvocationHandler.getCurrentInvocationHandler().poll(orders);
	this.separator = separator.separator;
    }

    public OrderByClause asc() {
	this.priority = PRIORITY.ASC;
	return this;
    }

    public OrderByClause desc() {
	this.priority = PRIORITY.DESC;
	return this;
    }

    public OrderByClause andBy(Object... orders) {
	return chain(new OrderByHibernateQueryBuilder(this, SEPARATOR.COMMA, orders));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

    public static enum PRIORITY {
	ASC("ASC"), DESC("DESC");
	String priority;

	PRIORITY(String priority) {
	    this.priority = priority;
	}
    }
}
