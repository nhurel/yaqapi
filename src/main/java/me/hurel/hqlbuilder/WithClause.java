/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

public interface WithClause<T> extends WhereClause<T> {

    public WithCondition<T> isNull();

    public WithCondition<T> isNotNull();

    public WithCondition<T> isEqualTo(T entity);

    public WithCondition<T> isNotEqualTo(T entity);

    public WithCondition<T> isGreaterThan(T entity);

    public WithCondition<T> isGreaterEqualThan(T entity);

    public WithCondition<T> isLessThan(T entity);

    public WithCondition<T> isLessEqualThan(T entity);

    public WithCondition<T> isLike(T entity);

    public WithCondition<T> isNotLike(T entity);

    public WithCondition<T> isIn(T... values);

    public WithCondition<T> isNotIn(T... values);
}
