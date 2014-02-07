package me.hurel.hqlbuilder.builder;

import java.util.ArrayList;
import java.util.List;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;
import net.sf.cglib.proxy.Enhancer;

public abstract class HibernateQueryBuilder extends UnfinishedHibernateQueryBuilder {

    /**
     * This list chains all the query parts that has been added
     */
    protected List<HibernateQueryBuilder> chain;

    /**
     * This list tracks the explicit joins that have been made
     */
    protected List<Object> joinedEntities;

    /**
     * The only root element of the query
     */
    protected final HibernateQueryBuilder root;

    /**
     * Handler of the proxies. This handler knows the alias and all the paths in
     * the query
     */
    HQBInvocationHandler invocationHandler;

    /**
     * Constructor
     * 
     * @param root
     *            the root element of the query
     */
    HibernateQueryBuilder(HibernateQueryBuilder root) {
	this.root = root;
	this.chain = root.chain;
	this.invocationHandler = root.invocationHandler;
	this.joinedEntities = root.joinedEntities;
    }

    /**
     * Constructor of the first part of the query
     */
    HibernateQueryBuilder() {
	this.root = this;
	this.chain = new ArrayList<HibernateQueryBuilder>();
	this.joinedEntities = new ArrayList<Object>();
    }

    public static UnfinishedSelectHibernateQueryBuilder select() {
	return new UnfinishedSelectHibernateQueryBuilder();
    }

    public static FromHibernateQueryBuilder selectFrom(Object object) {
	return new SelectHibernateQueryBuilder(object).from(object);
    }

    public static UnfinishedSelectHibernateQueryBuilder select(Object methodCall) {
	return new UnfinishedSelectHibernateQueryBuilder(methodCall);
    }

    public static UnfinishedSelectHibernateQueryBuilder select(Object... methodCall) {
	assert methodCall != null;
	return new UnfinishedSelectHibernateQueryBuilder(methodCall);
    }

    /**
     * This method must be called once (<strong>and only once</strong>) before
     * starting a querying. It returns a proxy object for the entity on which
     * the query is build. All entities linked to this proxied one can be
     * accessed through the getters. If another proxy is needed to write the
     * query (meaning, you want to make a full cartesian join on another
     * object), you have to use the {@link #andQueryOn(Object) andQueryOn}
     * method
     * 
     * @param entity
     * @return
     */
    public static <T> T queryOn(T entity) {
        return queryOn(entity, new HQBInvocationHandler());
    }

    @SuppressWarnings("unchecked")
    private static <T> T queryOn(T entity, HQBInvocationHandler handler) {
	T o = entity;
	if (!Enhancer.isEnhanced(entity.getClass())) {
	    Enhancer e = new Enhancer();
	    e.setClassLoader(entity.getClass().getClassLoader());
	    e.setSuperclass(entity.getClass());
	    e.setCallback(handler);
	    e.setUseFactory(true);
	    o = (T) e.create();
	}
        handler.declareAlias(o);
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
     * Get the equivalent HQL query written through the API
     * 
     * @return
     */
    public String getQueryString() {
	HQBQueryStringVisitor visitor = new HQBQueryStringVisitor(HQBInvocationHandler.getCurrentInvocationHandler().getAliases(), HQBInvocationHandler
		.getCurrentInvocationHandler().getPaths(), HQBInvocationHandler.getCurrentInvocationHandler().getParentsEntities(), joinedEntities);
	for (HibernateQueryBuilder builder : chain) {
	    builder.accept(visitor);
	}
	return visitor.getQuery();
    }

    abstract void accept(HQBVisitor visitor);

    <T extends HibernateQueryBuilder> T chain(T queryBuilder) {
	if (this == root) {
	    chain.add(queryBuilder);
	} else {
	    root.chain(queryBuilder);
	}
	return queryBuilder;
    }

    void addJoinedEntity(Object entity) {
	joinedEntities.add(entity);
    }

}
