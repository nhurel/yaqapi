package me.hurel.hqlbuilder.builder;

public abstract class AbstractOuterJoinQueryBuilder extends AbstractJoinQueryBuilder {

    AbstractOuterJoinQueryBuilder(HibernateQueryBuilder root, JOIN join) {
	super(root, join);
    }

    @Override
    protected void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
