package me.hurel.hqlbuilder.builder;

import java.util.ArrayList;
import java.util.List;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

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
