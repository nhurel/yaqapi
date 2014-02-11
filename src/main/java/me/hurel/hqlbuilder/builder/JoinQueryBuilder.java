package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.JoinClause;

public class JoinQueryBuilder extends AbstractFromQueryBuilder implements JoinClause {

    JoinQueryBuilder(HibernateQueryBuilder root, JOIN join, Object object) {
	super(root, join, object);
    }

    public JoinClause fetch() {
	fetch = true;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
