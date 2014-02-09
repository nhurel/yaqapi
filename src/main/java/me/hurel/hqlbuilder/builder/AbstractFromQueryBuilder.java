package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

public abstract class AbstractFromQueryBuilder extends HibernateQueryBuilder {

    Object object;

    boolean fetch = false;

    final String join;

    AbstractFromQueryBuilder(HibernateQueryBuilder root, JOIN join, Object object) {
	super(root);
	this.join = join.getJunction();
	this.object = object;
	addJoinedEntity(object);
	HQBInvocationHandler invocationHandler = HQBInvocationHandler.getCurrentInvocationHandler();
	if (invocationHandler != null) {
	    invocationHandler.reset();
	}
    }

    public JoinQueryBuilder innerJoin(Object methodCall) {
	return chain(new JoinQueryBuilder(this, JOIN.INNER, methodCall));
    }

    public JoinQueryBuilder innerJoinFetch(Object methodCall) {
	return innerJoin(methodCall).fetch();
    }

    public JoinQueryBuilder leftJoin(Object methodCall) {
	return chain(new JoinQueryBuilder(this, JOIN.LEFT, methodCall));
    }

    public JoinQueryBuilder leftJoinFetch(Object methodCall) {
	return leftJoin(methodCall).fetch();
    }

    public JoinQueryBuilder rightJoin(Object methodCall) {
	return chain(new JoinQueryBuilder(this, JOIN.RIGHT, methodCall));
    }

    public JoinQueryBuilder rightJoinFetch(Object methodCall) {
	return rightJoin(methodCall).fetch();
    }

    public AbstractFromQueryBuilder andFrom(Object entity) {
	return chain(new FromHibernateQueryBuilder(this, entity));
    }

    public <T> WhereHibernateQueryBuilder<T> where(T methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WHERE, methodCall));
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
