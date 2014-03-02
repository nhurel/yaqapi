/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

public interface WhereClause<T> {

    public Condition<T> isNull();

    public Condition<T> isNotNull();

    public Condition<T> isEqualTo(T entity);

    public Condition<T> isNotEqualTo(T entity);

    public Condition<T> isGreaterThan(T entity);

    public Condition<T> isGreaterEqualThan(T entity);

    public Condition<T> isLessThan(T entity);

    public Condition<T> isLessEqualThan(T entity);

    public Condition<T> isLike(T entity);

    public Condition<T> isNotLike(T entity);

    public Condition<T> isIn(T... values);

    public Condition<T> isNotIn(T... values);

}
