package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.ExistsClause;
import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

public class FromHibernateQueryBuilder extends AbstractFromQueryBuilder implements FromClause {

    Object object;

    boolean fetch = false;

    final String join;

    FromHibernateQueryBuilder(HibernateQueryBuilder root, JOIN join, Object object) {
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
     * @see me.hurel.hqlbuilder.builder.FromClause#whereExists(java.lang.Object)
     */
    public ExistsClause whereExists(Object methodCall) {
	return chain(new ExistsHibernateQueryBuilder(this, SEPARATOR.WHERE, methodCall));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * me.hurel.hqlbuilder.builder.FromClause#whereNotExists(java.lang.Object)
     */
    public ExistsClause whereNotExists(Object methodCall) {
	return chain(new ExistsHibernateQueryBuilder(this, SEPARATOR.WHERE, methodCall).not());
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
