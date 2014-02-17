package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.ExistsClause;

public class ExistsHibernateQueryBuilder extends SelectHibernateQueryBuilder implements ExistsClause {

    boolean not = false;

    final String separator;

    ExistsHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, Object objet) {
	super(root, objet);
	this.separator = separator.separator;
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
