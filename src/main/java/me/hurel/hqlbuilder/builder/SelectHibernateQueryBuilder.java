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

import java.util.LinkedHashMap;
import java.util.Map;

public class SelectHibernateQueryBuilder extends HibernateQueryBuilder implements SelectClause {

    Map<Object, String> map;

    boolean distinct = false;

    SelectHibernateQueryBuilder(Object... selects) {
	Object[] objects = HQBInvocationHandler.getCurrentInvocationHandler().poll(selects);
	map = new LinkedHashMap<Object, String>(objects.length);
	for(Object o :objects){
	    map.put(o,null);
	}
	chain(this);
    }

    SelectHibernateQueryBuilder(HibernateQueryBuilder root, Object... selects) {
	super(root);
	Object[] objects = HQBInvocationHandler.getCurrentInvocationHandler().poll(selects);
	map = new LinkedHashMap<Object, String>(objects.length);
	for(Object o :objects){
	    map.put(o,null);
	}
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
