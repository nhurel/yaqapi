/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.builder.UnfinishedCaseWhenQueryBuilder;
import me.hurel.hqlbuilder.functions.Function;

public interface WhenCondition<T> {

    /**
     * Adds a new condition on the property <br/>
     * example : select(user).from(user).where
     * (user.getFirstName()).isLike("Bob%")<strong>.
     * and(user.getLastName())</strong>.isLike("Smith%")
     *
     * @param methodCall
     *            the property to filter on
     * @return
     */
    <U> WhenClause<U> and(U methodCall);

    /**
     * Adds a new condition on the property <br/>
     * example : select(user).from(user).where
     * (user.getFirstName()).isLike("Bob%")<strong>.
     * or(user.getFirstName())</strong>.isLike("John%")
     *
     * @param methodCall
     *            the property to filter on
     * @return
     */
    <U> WhenClause<U> or(U methodCall);

    /**
     * Adds a new condition on the result of the function applied to property <br/>
     * example : select(user).from(user).where
     * (user.getFirstName()).isLike("Bob%")<strong>.
     * and(size(user.getChildren()))</strong>.isGreaterThan(1)
     *
     * @param methodCall
     *            the property to filter on
     * @return
     */
    <U> WhenClause<U> and(Function<U> methodCall);

    /**
     * Adds a new condition on the result of the function applied to property <br/>
     * example : select(user).from(user).where
     * (user.getAge()).isLessThan(20)<strong>.
     * or(size(user.getChildren()))</strong>.isGreaterThan(1)
     *
     * @param methodCall
     *            the property to filter on
     * @return
     */
    <U> WhenClause<U> or(Function<U> methodCall);

    <U> WhenClause<U> orGroup(U methodCall);

    <U> WhenClause<U> andGroup(U methodCall);

    <U> WhenClause<U> orGroup(Function<U> methodCall);

    <U> WhenClause<U> andGroup(Function<U> methodCall);

    /**
     * Closes a group of condition by adding a closing parenthesis to the query <br/>
     * example select(user).from(user).whereGroup(size(user.getChildren
     * ())).isGreaterThan
     * (1).or(user.getAge()).isLessThan(20).<strong>closeGroup()</strong>
     *
     * @return
     */
    WhenCondition<T> closeGroup();

    public UnfinishedCaseWhenQueryBuilder then(Object value);

}
