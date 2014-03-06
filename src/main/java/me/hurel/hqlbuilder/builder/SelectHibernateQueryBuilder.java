/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.FromClause;
import me.hurel.hqlbuilder.SelectClause;
import me.hurel.hqlbuilder.builder.AbstractFromQueryBuilder.JOIN;
import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

public class SelectHibernateQueryBuilder extends HibernateQueryBuilder implements SelectClause {

    Object[] aliases;

    boolean distinct = false;

    SelectHibernateQueryBuilder(Object... selects) {
	this.aliases = HQBInvocationHandler.getCurrentInvocationHandler().poll(selects);
	chain(this);
    }

    SelectHibernateQueryBuilder(HibernateQueryBuilder root, Object... selects) {
	super(root);
	this.aliases = HQBInvocationHandler.getCurrentInvocationHandler().poll(selects);
    }

    public FromClause from(Object entity) {
	return chain(new FromHibernateQueryBuilder(this, JOIN.FROM, entity));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

    public SelectHibernateQueryBuilder distinct() {
	this.distinct = true;
	return this;
    }

}
