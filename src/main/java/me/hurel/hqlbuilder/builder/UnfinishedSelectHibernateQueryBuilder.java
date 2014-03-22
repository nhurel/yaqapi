/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.AliasableSelectClause;
import me.hurel.hqlbuilder.FromClause;
import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

import java.util.LinkedHashMap;

public class UnfinishedSelectHibernateQueryBuilder extends UnfinishedHibernateQueryBuilder implements AliasableSelectClause {

    Object last;

    LinkedHashMap<Object, String> map;

    boolean distinct = false;

    UnfinishedSelectHibernateQueryBuilder(Object methodCall) {
	Object[] objects = HQBInvocationHandler.getCurrentInvocationHandler().poll(new Object[] { methodCall });
	map = new LinkedHashMap<Object, String>(objects.length);
	for (Object o : objects) {
	    add(o);
	}
    }

    private void add(Object o) {
	map.put(o, null);
	last = o;
    }

    UnfinishedSelectHibernateQueryBuilder(Object... methodCall) {
	Object[] objects = HQBInvocationHandler.getCurrentInvocationHandler().poll(methodCall);
	map = new LinkedHashMap<Object, String>(objects.length);
	for (Object o : objects) {
	    add(o);
	}
    }

    public FromClause from(Object entity) {
	SelectHibernateQueryBuilder select = new SelectHibernateQueryBuilder();
	select.map = map;
	if (distinct) {
	    select = select.distinct();
	}
	return select.from(entity);
    }

    public UnfinishedSelectHibernateQueryBuilder distinct() {
	distinct = true;
	return this;
    }

    public AliasableSelectClause as(String alias) {
	map.put(last, alias);
	return this;
    }

    public AliasableSelectClause andSelect(Object methodCall) {
	Object[] objects = HQBInvocationHandler.getCurrentInvocationHandler().poll(new Object[] { methodCall });
	for (Object o : objects) {
	    add(o);
	}
	return this;
    }
}
