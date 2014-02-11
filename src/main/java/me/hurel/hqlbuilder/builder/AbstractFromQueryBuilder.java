package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.FromClause;
import me.hurel.hqlbuilder.GroupByClause;
import me.hurel.hqlbuilder.JoinClause;
import me.hurel.hqlbuilder.WhereClause;
import me.hurel.hqlbuilder.functions.Function;
import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

public abstract class AbstractFromQueryBuilder extends HibernateQueryBuilder implements FromClause {

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

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.FromClause#innerJoin(java.lang.Object)
     */
    public JoinQueryBuilder innerJoin(Object methodCall) {
	return chain(new JoinQueryBuilder(this, JOIN.INNER, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * me.hurel.hqlbuilder.builder.FromClause#innerJoinFetch(java.lang.Object)
     */
    public JoinClause innerJoinFetch(Object methodCall) {
	return innerJoin(methodCall).fetch();
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.FromClause#leftJoin(java.lang.Object)
     */
    public JoinQueryBuilder leftJoin(Object methodCall) {
	return chain(new JoinQueryBuilder(this, JOIN.LEFT, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * me.hurel.hqlbuilder.builder.FromClause#leftJoinFetch(java.lang.Object)
     */
    public JoinClause leftJoinFetch(Object methodCall) {
	return leftJoin(methodCall).fetch();
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.FromClause#rightJoin(java.lang.Object)
     */
    public JoinQueryBuilder rightJoin(Object methodCall) {
	return chain(new JoinQueryBuilder(this, JOIN.RIGHT, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * me.hurel.hqlbuilder.builder.FromClause#rightJoinFetch(java.lang.Object)
     */
    public JoinClause rightJoinFetch(Object methodCall) {
	return rightJoin(methodCall).fetch();
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.FromClause#andFrom(java.lang.Object)
     */
    public FromClause andFrom(Object entity) {
	return chain(new FromHibernateQueryBuilder(this, entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.FromClause#where(T)
     */
    public <T> WhereClause<T> where(T methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WHERE, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * me.hurel.hqlbuilder.builder.FromClause#where(me.hurel.hqlbuilder.functions
     * .Function)
     */
    public <T> WhereClause<T> where(Function<T> methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WHERE, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.FromClause#groupBy(java.lang.Object)
     */
    public GroupByClause groupBy(Object... properties) {
	return chain(new GroupByHibernateQueryBuilder(this, properties));
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
