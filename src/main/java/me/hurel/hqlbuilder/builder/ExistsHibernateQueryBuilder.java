package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.ExistsClause;

public class ExistsHibernateQueryBuilder extends SelectHibernateQueryBuilder implements ExistsClause {

    boolean not = false;

    ExistsHibernateQueryBuilder(HibernateQueryBuilder root, Object objet) {
	super(root, objet);
    }

    ExistsHibernateQueryBuilder not() {
	not = true;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
