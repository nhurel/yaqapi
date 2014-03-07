/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

public class NullConditionHibernateQueryBuilder<T> extends ConditionHibernateQueryBuilder<T> {

    public NullConditionHibernateQueryBuilder(WhereHibernateQueryBuilder<T> where) {
	this(where, true);
    }

    public NullConditionHibernateQueryBuilder(WhereHibernateQueryBuilder<T> where, boolean isNull) {
	super(where.root, isNull ? OPERATOR.IS_NULL : OPERATOR.IS_NOT_NULL, (T) null);
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
