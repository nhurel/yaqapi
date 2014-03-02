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

/**
 * Interface defining methods available after writing a condition
 * 
 * @author nathan
 * 
 * @param <T>
 */
public interface Condition<T> extends QueryBuilder {

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
    public <U> WhereClause<U> and(U methodCall);

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
    public <U> WhereClause<U> or(U methodCall);

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
    public <U> WhereClause<U> and(Function<U> methodCall);

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
    public <U> WhereClause<U> or(Function<U> methodCall);

    /**
     * Adds a group by clause on the given properties
     * 
     * @param properties
     *            One or more properties to group by
     * @return
     */
    public GroupByClause groupBy(Object... properties);

    public <U> WhereClause<U> orGroup(U methodCall);

    public <U> WhereClause<U> andGroup(U methodCall);

    public <U> WhereClause<U> orGroup(Function<U> methodCall);

    public <U> WhereClause<U> andGroup(Function<U> methodCall);

    /**
     * Closes a group of condition by adding a closing parenthesis to the query <br/>
     * example select(user).from(user).whereGroup(size(user.getChildren
     * ())).isGreaterThan
     * (1).or(user.getAge()).isLessThan(20).<strong>closeGroup()</strong>
     * 
     * @return
     */
    public Condition<T> closeGroup();

    /**
     * Closes an exists clause by adding a closing parenthesis to the query <br/>
     * example :
     * select(city).from(city).whereExists(user.getId()).from(user).innerJoin
     * (user
     * .getAdress()).where(user.getAdress().getCity().getId()).isEqualTo(city
     * .getId()).<strong>closeExists()</strong>
     * 
     * @return
     */
    public Condition<T> closeExists();

    /**
     * Adds an order by clause on the given properties
     * 
     * @param properties
     *            One or more properties to order by
     * @return
     */
    public OrderByClause orderBy(Object... orders);

    public ExistsClause andExists(Object methodCall);

    public ExistsClause andNotExists(Object methodCall);

    public ExistsClause orExists(Object methodCall);

    public ExistsClause orNotExists(Object methodCall);
}
