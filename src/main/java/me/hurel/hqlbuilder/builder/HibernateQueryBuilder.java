package me.hurel.hqlbuilder.builder;

import java.util.ArrayList;
import java.util.List;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

import org.hibernate.Query;
import org.hibernate.Session;

public abstract class HibernateQueryBuilder extends UnfinishedHibernateQueryBuilder {

    /**
     * This list tracks the explicit joins that have been made
     */
    protected List<Object> joinedEntities;

    private boolean visited = false;

    private String queryString;

    private List<Object> parameters;

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
	super(root);
	this.chain = root.chain;
	this.invocationHandler = root.invocationHandler;
	this.joinedEntities = root.joinedEntities;
    }

    /**
     * Constructor of the first part of the query
     */
    HibernateQueryBuilder() {
	// this.root = this;
	this.chain = new ArrayList<UnfinishedHibernateQueryBuilder>();
	this.joinedEntities = new ArrayList<Object>();
    }

    /**
     * Get the equivalent HQL query written through the API
     * 
     * @return
     */
    public String getQueryString() {
	ensureVisited();
	return queryString;
    }

    public List<Object> getParameters() {
	ensureVisited();
	return parameters;
    }

    /**
     * Builds the Hibernate query which this object defines
     * 
     * @param session
     *            the hibernate Session
     * @return Query the built query with all parameters set
     */
    public Query build(Session session) {
	Query query = session.createQuery(getQueryString());
	if (parameters != null) {
	    int i = 0;
	    for (Object parameter : parameters) {
		query.setParameter(i++, parameter);
	    }
	}
	return query;
    }

    private void ensureVisited() {
	if (!visited) {
	    HQBQueryStringVisitor visitor = new HQBQueryStringVisitor(HQBInvocationHandler.getCurrentInvocationHandler().getAliases(), HQBInvocationHandler
		    .getCurrentInvocationHandler().getPaths(), HQBInvocationHandler.getCurrentInvocationHandler().getParentsEntities(), joinedEntities);
	    for (UnfinishedHibernateQueryBuilder builder : chain) {
		builder.accept(visitor);
	    }
	    queryString = visitor.getQuery();
	    parameters = visitor.getParameters();
	}
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
	WHERE("WHERE"), AND("AND"), OR("OR"), HAVING("HAVING");

	String separator;

	SEPARATOR(String separator) {
	    this.separator = separator;
	}
    }

}
