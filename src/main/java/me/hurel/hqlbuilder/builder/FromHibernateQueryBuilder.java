package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.FromClause;
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

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
