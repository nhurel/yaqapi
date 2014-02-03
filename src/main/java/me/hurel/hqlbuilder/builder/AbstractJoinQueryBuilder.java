package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

public abstract class AbstractJoinQueryBuilder extends HibernateQueryBuilder {

    String object;

    String alias;

    boolean fetch = false;

    final String join;

    AbstractJoinQueryBuilder(HibernateQueryBuilder root, JOIN join) {
	super(root);
	this.join = join.getJunction();
	HQBInvocationHandler invocationHandler = HQBInvocationHandler.getCurrentInvocationHandler();
	if (invocationHandler != null) {
	    invocationHandler.reset();
	}
    }

    public InnerJoinQueryBuilder innerJoin(Object methodCall) {
	String fullPath = HQBInvocationHandler.getCurrentInvocationHandler().getCurrentPath();
	String alias = HQBInvocationHandler.getCurrentInvocationHandler().getCurrentAlias();
	return chain(new InnerJoinQueryBuilder(this, fullPath, alias));
    }

    public InnerJoinQueryBuilder innerJoin(Object methodCall, String alias) {
	andQueryOn(methodCall, alias);
	String fullPath = HQBInvocationHandler.getCurrentInvocationHandler().getCurrentPath();
	return chain(new InnerJoinQueryBuilder(this, fullPath, alias));
    }

    public InnerJoinQueryBuilder innerJoin(String property) {
	return chain(new InnerJoinQueryBuilder(this, property, toSimpleName(property)));
    }

    public InnerJoinQueryBuilder innerJoin(String property, String alias) {
	return chain(new InnerJoinQueryBuilder(this, property, alias));
    }

    public InnerJoinQueryBuilder innerJoinFetch(Object methodCall) {
	return innerJoin(methodCall).fetch();
    }

    public InnerJoinQueryBuilder innerJoinFetch(String property) {
	return innerJoin(property).fetch();
    }

    public InnerJoinQueryBuilder innerJoinFetch(String property, String alias) {
	return innerJoin(property, alias).fetch();
    }

    public LeftJoinQueryBuilder leftJoin(Object methodCall) {
	String fullPath = HQBInvocationHandler.getCurrentInvocationHandler().getCurrentPath();
	return chain(new LeftJoinQueryBuilder(this, fullPath, toSimpleName(fullPath)));
    }

    public LeftJoinQueryBuilder leftJoin(String property) {
	return chain(new LeftJoinQueryBuilder(this, property, toSimpleName(property)));
    }

    public LeftJoinQueryBuilder leftJoin(String property, String alias) {
	return chain(new LeftJoinQueryBuilder(this, property, alias));
    }

    public LeftJoinQueryBuilder leftJoinFetch(Object methodCall) {
	return leftJoin(methodCall).fetch();
    }

    public LeftJoinQueryBuilder leftJoinFetch(String property) {
	return leftJoin(property).fetch();
    }

    public LeftJoinQueryBuilder leftJoinFetch(String property, String alias) {
	return leftJoin(property, alias).fetch();
    }

    public RightJoinQueryBuilder rightJoin(Object methodCall) {
	String fullPath = HQBInvocationHandler.getCurrentInvocationHandler().getCurrentPath();
	return chain(new RightJoinQueryBuilder(this, fullPath, toSimpleName(fullPath)));
    }

    public RightJoinQueryBuilder rightJoin(String property) {
	return chain(new RightJoinQueryBuilder(this, property, toSimpleName(property)));
    }

    public RightJoinQueryBuilder rightJoin(String property, String alias) {
	return chain(new RightJoinQueryBuilder(this, property, alias));
    }

    public RightJoinQueryBuilder rightJoinFetch(Object methodCall) {
	return rightJoin(methodCall).fetch();
    }

    public RightJoinQueryBuilder rightJoinFetch(String property) {
	return rightJoin(property).fetch();
    }

    public RightJoinQueryBuilder rightJoinFetch(String property, String alias) {
	return rightJoin(property, alias).fetch();
    }

    public FromHibernateQueryBuilder andFrom(Class<?> entity) {
	return andFrom(entity, toAlias(entity));
    }

    public FromHibernateQueryBuilder andFrom(Class<?> entity, String alias) {
	return chain(new FromHibernateQueryBuilder(this, entity, alias));
    }

    enum JOIN {
	FROM("FROM"), INNER("INNER JOIN"), LEFT("LEFT JOIN"), RIGHT("RIGHT JOIN"), CARTESIAN(",");

	String junction;

	JOIN(String junction) {
	    this.junction = junction;
	}

	public String getJunction() {
	    return junction;
	}
    }

}
