/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.ExistsClause;

public class ExistsHibernateQueryBuilder extends SelectHibernateQueryBuilder implements ExistsClause {

    boolean not = false;

    final String separator;

    ExistsHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, Object objet) {
	super(root, objet);
	this.separator = separator.separator;
    }

    ExistsHibernateQueryBuilder not() {
	not = true;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
