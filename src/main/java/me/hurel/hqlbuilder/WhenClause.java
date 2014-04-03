/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

public interface WhenClause<T> {

    public WhenCondition<T> isNull();

    public WhenCondition<T> isNotNull();

    public WhenCondition<T> isEqualTo(T entity);

    public WhenCondition<T> isNotEqualTo(T entity);

    public WhenCondition<T> isGreaterThan(T entity);

    public WhenCondition<T> isGreaterEqualThan(T entity);

    public WhenCondition<T> isLessThan(T entity);

    public WhenCondition<T> isLessEqualThan(T entity);

    public WhenCondition<T> isLike(T entity);

    public WhenCondition<T> isNotLike(T entity);

    public WhenCondition<T> isIn(T... values);

    public WhenCondition<T> isNotIn(T... values);
}
