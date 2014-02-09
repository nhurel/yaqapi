package me.hurel.hqlbuilder.builder;


public class FromHibernateQueryBuilder extends AbstractFromQueryBuilder {

    FromHibernateQueryBuilder(HibernateQueryBuilder root, Object entity) {
	super(root, JOIN.FROM, entity);
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
