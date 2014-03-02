/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

public interface WithCondition<T> extends Condition<T>, AbstractFromClause {

    public <U> WithClause<U> and(U methodCall);

    public <U> WithClause<U> or(U methodCall);

    public GroupByClause groupBy(Object... properties);

    public <U> WithClause<U> orGroup(U methodCall);

    public <U> WithClause<U> andGroup(U methodCall);

    public WithCondition<T> closeGroup();

}
