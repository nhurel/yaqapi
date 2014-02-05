package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

public abstract class AbstractJoinQueryBuilder extends HibernateQueryBuilder {

    Object object;

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
	return chain(new InnerJoinQueryBuilder(this, methodCall));
    }

    public InnerJoinQueryBuilder innerJoin(Object methodCall, String alias) {
	andQueryOn(methodCall, alias);
	return chain(new InnerJoinQueryBuilder(this, methodCall));
    }

    public InnerJoinQueryBuilder innerJoinFetch(Object methodCall) {
	return innerJoin(methodCall).fetch();
    }

    public LeftJoinQueryBuilder leftJoin(Object methodCall) {
	return chain(new LeftJoinQueryBuilder(this, methodCall));
    }

    public LeftJoinQueryBuilder leftJoinFetch(Object methodCall) {
	return leftJoin(methodCall).fetch();
    }

    public RightJoinQueryBuilder rightJoin(Object methodCall) {
	return chain(new RightJoinQueryBuilder(this, methodCall));
    }

    public RightJoinQueryBuilder rightJoinFetch(Object methodCall) {
	return rightJoin(methodCall).fetch();
    }

    public FromHibernateQueryBuilder andFrom(Object entity) {
	return chain(new FromHibernateQueryBuilder(this, entity));
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
