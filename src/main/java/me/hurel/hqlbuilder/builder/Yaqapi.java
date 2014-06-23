/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.AliasableSelectClause;
import me.hurel.hqlbuilder.FromClause;
import me.hurel.hqlbuilder.SelectClause;
import me.hurel.hqlbuilder.WhenClause;
import me.hurel.hqlbuilder.builder.HibernateQueryBuilder.SEPARATOR;
import me.hurel.hqlbuilder.functions.Function;
import me.hurel.hqlbuilder.functions.Function.FUNCTION;
import me.hurel.hqlbuilder.functions.IntFunction;
import me.hurel.hqlbuilder.functions.LongFunction;
import me.hurel.hqlbuilder.functions.MultiParameterFunction;
import me.hurel.hqlbuilder.functions.ParameterizedFunction;
import me.hurel.hqlbuilder.helpers.CollectionHelper;
import me.hurel.hqlbuilder.internal.HQBInvocationHandler;
import me.hurel.hqlbuilder.internal.ProxyUtil;

import java.util.Collection;

public class Yaqapi {


    /**
     * This method must be called once (<strong>and only once</strong>) before
     * starting a query. It returns a proxy object for the class on which
     * the query is build. All entities linked to this proxied one can be
     * accessed through the getters. If another proxy is needed to write the
     * query (meaning, you want to make a full cartesian join on another
     * object), you have to use the
     * {@link me.hurel.hqlbuilder.builder.Yaqapi#andQueryOn(Object) andQueryOn} method
     *
     * @param entityClass the entity class to build a proxy for. Class must have a public non args constructor
     * @return proxy entity
     */
    public static <T> T queryOn(Class<T> entityClass) {
	return queryOn(entityClass, new HQBInvocationHandler());
    }

    /**
     * This method must be called once (<strong>and only once</strong>) before
     * starting a query. It returns a proxy object for the entity on which
     * the query is build. All entities linked to this proxied one can be
     * accessed through the getters. If another proxy is needed to write the
     * query (meaning, you want to make a full cartesian join on another
     * object), you have to use the
     * {@link me.hurel.hqlbuilder.builder.Yaqapi#andQueryOn(Object) andQueryOn} method
     *
     * @param entity
     * @return
     * @deprecated Use {@link me.hurel.hqlbuilder.builder.Yaqapi#queryOn(Class)} instead
     */
    @Deprecated
    public static <T> T queryOn(T entity) {
	return queryOn(entity, new HQBInvocationHandler());
    }

    private static <T> T queryOn(T entity, HQBInvocationHandler handler) {
	return ProxyUtil.buildProxy(entity, entity.getClass(), handler);
    }

    private static <T> T queryOn(Class<T> entityClass, HQBInvocationHandler handler) {
	return ProxyUtil.buildProxy(entityClass, null, handler);
    }

    /**
     * This method returns a proxy for the given entity class. It can be useful if you
     * need two proxies on two distinct entities when writing a query
     *
     * @param entityClass the entity class to build a proxy for. Class must have a public non args constructor
     * @return
     */
    public static <T> T andQueryOn(Class<T> entityClass) {
	return queryOn(entityClass, HQBInvocationHandler.getCurrentInvocationHandler());
    }

    /**
     * This method returns a proxy on the given entity. It can be useful if you
     * need two proxies on two distinct entities when writing a query
     *
     * @param entity
     * @return
     * @deprecated Use {@link me.hurel.hqlbuilder.builder.Yaqapi#andQueryOn(Class)} instead
     */
    @Deprecated
    public static <T> T andQueryOn(T entity) {
	return queryOn(entity, HQBInvocationHandler.getCurrentInvocationHandler());
    }

    /**
     * This method is a helper to query on a collection property of the entity.
     *
     * @param methodCall
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T, U extends T> U $(Collection<T> methodCall) {
	if (methodCall instanceof CollectionHelper<?>) {
	    return (U) ((CollectionHelper<?>) methodCall).getObject();
	}
	return (U) methodCall;
    }

    /**
     * Start a new query to get only ones entity
     *
     * @param entity The entity to query on
     * @return
     */
    public static FromClause selectFrom(Object entity) {
	return new SelectHibernateQueryBuilder(entity).from(entity);
    }

    /**
     * Start a new query to get only distinct ones entity
     *
     * @param entity The entity to query on
     * @return
     */
    public static FromClause selectDistinctFrom(Object entity) {
	return new SelectHibernateQueryBuilder(entity).distinct().from(entity);
    }

    /**
     * Start a query to get a property on an entity or an entity itself
     *
     * @param methodCall the property to get from the entity oe the entity
     * @return
     */
    public static AliasableSelectClause select(Object methodCall) {
	return new UnfinishedSelectHibernateQueryBuilder(methodCall);
    }

    /**
     * Start a query to get distinct value of the property of on an entity or of
     * the entities
     *
     * @param methodCall the property to get from the entity or the entity
     * @return
     */
    public static AliasableSelectClause selectDistinct(Object methodCall) {
	return new UnfinishedSelectHibernateQueryBuilder(methodCall).distinct();
    }

    /**
     * Start a query to get multiple properties and/or entities
     *
     * @param methodCall the properties and entities to get
     * @return
     */
    public static SelectClause select(Object... methodCall) {
	if (methodCall == null) {
	    throw new NullPointerException();
	}
	return new UnfinishedSelectHibernateQueryBuilder(methodCall);
    }

    /**
     * Start a query to get distinct properties and/or entities
     *
     * @param methodCall the properties and entities to get
     * @return
     */
    public static SelectClause selectDistinct(Object... methodCall) {
	if (methodCall == null) {
	    throw new NullPointerException();
	}
	return new UnfinishedSelectHibernateQueryBuilder(methodCall).distinct();
    }

    @SuppressWarnings("unchecked")
    public static <T> WhenClause<T> caseWhen(T methodCall) {
	if (methodCall == null) {
	    throw new NullPointerException();
	}
	return new WhereHibernateQueryBuilder(SEPARATOR.CASE_WHEN, methodCall);
    }

    public static <T> Function<T> max(T methodCall) {
	return new ParameterizedFunction<T>(FUNCTION.MAX, methodCall);
    }

    public static <T> Function<T> min(T methodCall) {
	return new ParameterizedFunction<T>(FUNCTION.MIN, methodCall);
    }

    public static <T> Function<T> average(T methodCall) {
	return new ParameterizedFunction<T>(FUNCTION.AVERAGE, methodCall);
    }

    public static <T> Function<T> sum(T methodCall) {
	return new ParameterizedFunction<T>(FUNCTION.SUM, methodCall);
    }

    public static <T> LongFunction count(T methodCall) {
	return new LongFunction(FUNCTION.COUNT, methodCall);
    }

    public static <T> IntFunction size(Collection<T> methodCall) {
	return new IntFunction(FUNCTION.SIZE, methodCall);
    }

    public static <T> Function<T> distinct(T methodCall) {
	return new ParameterizedFunction<T>(FUNCTION.DISTINCT, methodCall);
    }

    public static Function<String> upper(String methodCall) {
	return new ParameterizedFunction<String>(FUNCTION.UPPER, methodCall);
    }

    public static Function<String> lower(String methodCall) {
	return new ParameterizedFunction<String>(FUNCTION.LOWER, methodCall);
    }

    public static <T> Function<T> coalesce(T... methodCall){
        return new MultiParameterFunction<T>(FUNCTION.COALESCE, methodCall);
    }

}
