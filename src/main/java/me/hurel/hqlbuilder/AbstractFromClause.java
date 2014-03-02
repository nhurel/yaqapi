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
 * Interface defining methods available when writing a FROM-like clause
 * 
 * @author nathan
 * 
 */
public interface AbstractFromClause extends QueryBuilder {

    /**
     * Make an inner join with the specified property<br/>
     * example :
     * select(user).from(user)<strong>.innerJoin(user.getChildren())</strong>
     * 
     * @param methodCall
     *            the property of the object to join with
     * @return
     */
    public JoinClause innerJoin(Object methodCall);

    /**
     * Make an inner join with the specified property and specifies the property
     * has to be fetched<br/>
     * example :
     * select(user).from(user)<strong>.innerJoinFetch(user.getChildren(
     * ))</strong>
     * 
     * @param methodCall
     *            the property of the object to join with
     * @return
     */
    public JoinClause innerJoinFetch(Object methodCall);

    /**
     * Make a left join with the specified property<br/>
     * example :
     * select(user).from(user)<strong>.leftJoin(user.getChildren())</strong>
     * 
     * @param methodCall
     *            the property of the object to join with
     * @return
     */
    public JoinClause leftJoin(Object methodCall);

    /**
     * Make a left join with the specified property and specifies the property
     * has to be fetched<br/>
     * example :
     * select(user).from(user)<strong>.leftJoinFetch(user.getChildren()
     * )</strong>
     * 
     * @param methodCall
     *            the property of the object to join with
     * @return
     */
    public JoinClause leftJoinFetch(Object methodCall);

    /**
     * Make a right join with the specified property<br/>
     * example :
     * select(user).from(user)<strong>.rightJoin(user.getChildren())</strong>
     * 
     * @param methodCall
     *            the property of the object to join with
     * @return
     */
    public JoinClause rightJoin(Object methodCall);

    /**
     * Make a right join with the specified property and specifies the property
     * has to be fetched<br/>
     * example :
     * select(user).from(user)<strong>.rightJoinFetch(user.getChildren(
     * ))</strong>
     * 
     * @param methodCall
     *            the property of the object to join with
     * @return
     */
    public JoinClause rightJoinFetch(Object methodCall);

    /**
     * Make a cross join with the given entity<br/>
     * example : select(city).from(city)<strong>.andFrom(country)</strong>
     * 
     * @param entity
     *            The entity to join with
     * @return
     */
    public FromClause andFrom(Object entity);

    /**
     * Adds a where clause to the query with filtering on the given property.<br/>
     * example :
     * select(user).from(user)<strong>.where(user.getAge())</strong>.isEqualTo
     * (5)
     * 
     * @param methodCall
     *            the property to filter on
     * @return
     */
    public <T> WhereClause<T> where(T methodCall);

    /**
     * Adds a where clause to the query with filtering on the result of the
     * given function<br>
     * example :
     * select(user).from(user)<strong>.where(size(user.getChildren())</strong>
     * .isGreaterThan(1)
     * 
     * @param methodCall
     *            the function applied to a property
     * @return
     */
    public <T> WhereClause<T> where(Function<T> methodCall);

    /**
     * Adds a where clause inside a grouping parenthesis. The method
     * {@link Condition#closeGroup()} has to be called to close the parenthesis
     * when all conditions has been added <br/>
     * example :
     * select(user).from(user).<strong>whereGroup(user.getAge())</strong>
     * .isEqualTo(5).or(user.getAge()).isEqualTo(6).closeGroup()
     * 
     * @param methodCall
     *            the property to filter on
     * @return
     */
    public <T> WhereClause<T> whereGroup(T methodCall);

    /**
     * Adds a where clause on the result of a function, inside a grouping
     * parenthesis. The method {@link Condition#closeGroup()} has to be called
     * to close the parenthesis when all conditions has been added <br/>
     * example :
     * select(user).from(user).<strong>whereGroup(size(user.getChildren
     * ()))</strong>
     * .isGreaterThan(1).or(user.getAge()).isLessThan(20).closeGroup()
     * 
     * @param methodCall
     *            the property to filter on
     * @return
     */
    public <T> WhereClause<T> whereGroup(Function<T> methodCall);

    /**
     * Adds a group by clause on the given properties
     * 
     * @param properties
     *            One or more properties to group on
     * @return
     */
    public GroupByClause groupBy(Object... properties);

}
