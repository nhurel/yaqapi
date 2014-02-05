package me.hurel.hqlbuilder.builder;

public class FromHibernateQueryBuilder extends AbstractJoinQueryBuilder {

    FromHibernateQueryBuilder(HibernateQueryBuilder root, Object entity) {
	super(root, JOIN.FROM);
	this.object = entity;
    }

    @Override
    protected void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
