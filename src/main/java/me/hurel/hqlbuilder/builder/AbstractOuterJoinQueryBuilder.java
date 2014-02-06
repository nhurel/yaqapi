package me.hurel.hqlbuilder.builder;

public abstract class AbstractOuterJoinQueryBuilder extends AbstractJoinQueryBuilder {

    AbstractOuterJoinQueryBuilder(HibernateQueryBuilder root, JOIN join, Object object) {
	super(root, join, object);
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
