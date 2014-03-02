/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.functions.Function;

public interface GroupByClause extends QueryBuilder {

    public <T> WhereClause<T> having(T property);

    public <T> WhereClause<T> having(Function<T> property);

    public OrderByClause orderBy(Object... orders);

}
