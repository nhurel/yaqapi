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

public class UnfinishedCaseWhenQueryBuilder extends HibernateQueryBuilder {

    final Object value;

    UnfinishedCaseWhenQueryBuilder(HibernateQueryBuilder root, Object value){
	super(root);
	this.value=value;
    }
    
    public <T> WhenClause<T> when(T methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WHEN, methodCall));
    }

    public <T> CaseWhenClause<T> whenElse(T value){
	return chain(new CaseWhenHibernateQueryBuilder(this, value));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }


}
