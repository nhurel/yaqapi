package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.AbstractFromClause;
import me.hurel.hqlbuilder.GroupByClause;
import me.hurel.hqlbuilder.JoinClause;
import me.hurel.hqlbuilder.OrderByClause;
import me.hurel.hqlbuilder.WhereClause;
import me.hurel.hqlbuilder.functions.Function;

public abstract class AbstractFromQueryBuilder extends HibernateQueryBuilder implements AbstractFromClause {

    AbstractFromQueryBuilder(HibernateQueryBuilder root) {
	super(root);
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.FromClause#innerJoin(java.lang.Object)
     */
    public JoinClause innerJoin(Object methodCall) {
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
    public JoinClause leftJoin(Object methodCall) {
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
    public JoinClause rightJoin(Object methodCall) {
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
	return chain(new FromHibernateQueryBuilder(this, JOIN.CARTESIAN, entity));
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

    public <T> WhereClause<T> whereGroup(T methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WHERE, methodCall).group());
    }

    public <T> WhereClause<T> whereGroup(Function<T> methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WHERE, methodCall).group());
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.FromClause#groupBy(java.lang.Object)
     */
    public GroupByClause groupBy(Object... properties) {
	return chain(new GroupByHibernateQueryBuilder(this, properties));
    }

    public OrderByClause orderBy(Object... orders) {
	return chain(new OrderByHibernateQueryBuilder(this, SEPARATOR.ORDER_BY, orders));
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
