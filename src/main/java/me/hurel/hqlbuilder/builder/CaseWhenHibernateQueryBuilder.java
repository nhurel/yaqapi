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

public class CaseWhenHibernateQueryBuilder extends HibernateQueryBuilder implements CaseWhenClause{

    final Object value;

    CaseWhenHibernateQueryBuilder(HibernateQueryBuilder root, Object value){
	super(root);
	this.value=value;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }
}
