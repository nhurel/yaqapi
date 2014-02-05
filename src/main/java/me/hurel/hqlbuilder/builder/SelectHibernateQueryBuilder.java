package me.hurel.hqlbuilder.builder;

public class SelectHibernateQueryBuilder extends HibernateQueryBuilder implements HQBSelect {

    Object[] aliases;

    SelectHibernateQueryBuilder(Object... selects) {
	this.aliases = selects;
	chain(this);
    }

    public FromHibernateQueryBuilder from(Object entity) {
	return chain(new FromHibernateQueryBuilder(this, entity));
    }

    @Override
    protected void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
