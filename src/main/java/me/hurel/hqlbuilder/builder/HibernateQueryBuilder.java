/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import java.util.ArrayList;
import java.util.List;

import me.hurel.hqlbuilder.QueryBuilder;
import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HibernateQueryBuilder extends UnfinishedHibernateQueryBuilder implements QueryBuilder {

    private final Logger LOGGER = LoggerFactory.getLogger(HibernateQueryBuilder.class);

    /**
     * This list tracks the explicit joins that have been made
     */
    protected List<Object> joinedEntities;

    private boolean visited = false;

    private String queryString;

    private List<Object> parameters;

    /**
     * This list chains all the query parts that has been added
     */
    protected List<HibernateQueryBuilder> chain;

    /**
     * The only root element of the query
     */
    protected final HibernateQueryBuilder root;

    <T extends HibernateQueryBuilder> T chain(T queryBuilder) {
	if (this == root) {
	    chain.add(queryBuilder);
	} else {
	    root.chain(queryBuilder);
	}
	return queryBuilder;
    }

    abstract void accept(HQBVisitor visitor);

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
	super();
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

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.QueryBuilder#getQueryString()
     */
    public String getQueryString() {
	ensureVisited();
	return queryString;
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.QueryBuilder#getParameters()
     */
    public List<Object> getParameters() {
	ensureVisited();
	return parameters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * me.hurel.hqlbuilder.builder.QueryBuilder#build(org.hibernate.Session)
     */
    public Query build(Session session) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug(getQueryString());
	}
	Query query = session.createQuery(getQueryString());
	if (parameters != null) {
	    int i = 1;
	    for (Object parameter : parameters) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("setting parameter [{}] to value [{}]", i, parameter);
		}
		query.setParameter(Integer.toString(i++), parameter);
	    }
	}
	return query;
    }

    private void ensureVisited() {
	if (!visited) {
	    HQBQueryStringVisitor visitor = new HQBQueryStringVisitor(HQBInvocationHandler.getCurrentInvocationHandler().getAliases(), HQBInvocationHandler
		    .getCurrentInvocationHandler().getPaths(), HQBInvocationHandler.getCurrentInvocationHandler().getParentsEntities(), joinedEntities);
	    for (HibernateQueryBuilder builder : chain) {
		builder.accept(visitor);
	    }
	    queryString = visitor.getQuery();
	    parameters = visitor.getParameters();
	}
	visited = true;
    }

    void addJoinedEntity(Object entity) {
	joinedEntities.add(entity);
    }

    enum OPERATOR {
	IS_NULL("IS NULL"), IS_NOT_NULL("IS NOT NULL"), EQUAL("="), NOT_EQUAL("<>"), //
	LIKE("LIKE"), NOT_LIKE("NOT LIKE"), GREATER(">"), GREATER_EQUAL(">="), LESS("<"), //
	LESS_EQUAL("<="), IN("IN"), NOT_IN("NOT IN");

	String operator;

	OPERATOR(String operator) {
	    this.operator = operator;
	}
    }

    enum SEPARATOR {
	WHERE("WHERE"), WITH("WITH"), AND("AND"), OR("OR"), HAVING("HAVING"), ORDER_BY("ORDER BY"), COMMA(",");

	String separator;

	SEPARATOR(String separator) {
	    this.separator = separator;
	}
    }

}
