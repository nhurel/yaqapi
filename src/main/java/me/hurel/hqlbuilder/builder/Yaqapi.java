package me.hurel.hqlbuilder.builder;

import java.util.Collection;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;
import me.hurel.hqlbuilder.internal.ProxyUtil;

public class Yaqapi {

    /**
     * This method must be called once (<strong>and only once</strong>) before
     * starting a querying. It returns a proxy object for the entity on which
     * the query is build. All entities linked to this proxied one can be
     * accessed through the getters. If another proxy is needed to write the
     * query (meaning, you want to make a full cartesian join on another
     * object), you have to use the
     * {@link HibernateQueryBuilder#andQueryOn(Object) andQueryOn} method
     * 
     * @param entity
     * @return
     */
    public static <T> T queryOn(T entity) {
	return queryOn(entity, new HQBInvocationHandler());
    }

    private static <T> T queryOn(T entity, HQBInvocationHandler handler) {
	T o = ProxyUtil.buildProxy(entity, entity.getClass(), handler);
	return o;
    }

    /**
     * This method returns a proxy on the given entity. It can be usefull if you
     * need two proxies on two distinct entities when writing a query
     * 
     * @param entity
     * @return
     */
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
	return (U) methodCall;
    }

    /**
     * Start a new query to get only ones entity
     * 
     * @param entity
     *            The entity to query on
     * @return
     */
    public static FromHibernateQueryBuilder selectFrom(Object entity) {
	return new SelectHibernateQueryBuilder(entity).from(entity);
    }

    /**
     * Start a new query to get only distinct ones entity
     * 
     * @param entity
     *            The entity to query on
     * @return
     */
    public static FromHibernateQueryBuilder selectDistinctFrom(Object entity) {
	return new SelectHibernateQueryBuilder(entity).distinct().from(entity);
    }

    /**
     * Start a query to get a property on an entity or an entity itself
     * 
     * @param methodCall
     *            the property to get from the entity oe the entity
     * @return
     */
    public static UnfinishedSelectHibernateQueryBuilder select(Object methodCall) {
	return new UnfinishedSelectHibernateQueryBuilder(methodCall);
    }

    /**
     * Start a query to get distinct value of the property of on an entity or of
     * the entities
     * 
     * @param methodCall
     *            the property to get from the entity or the entity
     * @return
     */
    public static UnfinishedSelectHibernateQueryBuilder selectDistinct(Object methodCall) {
	return new UnfinishedSelectHibernateQueryBuilder(methodCall).distinct();
    }

    /**
     * Start a query to get multiple properties and/or entities
     * 
     * @param methodCall
     *            the properties and entities to get
     * @return
     */
    public static UnfinishedSelectHibernateQueryBuilder select(Object... methodCall) {
	assert methodCall != null;
	return new UnfinishedSelectHibernateQueryBuilder(methodCall);
    }

    /**
     * Start a query to get distinct properties and/or entities
     * 
     * @param methodCall
     *            the properties and entities to get
     * @return
     */
    public static UnfinishedSelectHibernateQueryBuilder selectDistinct(Object... methodCall) {
	assert methodCall != null;
	return new UnfinishedSelectHibernateQueryBuilder(methodCall).distinct();
    }
}
