package me.hurel.hqlbuilder.builder;

public class InnerJoinQueryBuilder extends AbstractJoinQueryBuilder {

    InnerJoinQueryBuilder(HibernateQueryBuilder root, Object object) {
	super(root, JOIN.INNER, object);
    }

    public InnerJoinQueryBuilder fetch() {
	fetch = true;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
