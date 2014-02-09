package me.hurel.hqlbuilder.builder;

public class JoinQueryBuilder extends AbstractFromQueryBuilder {

    JoinQueryBuilder(HibernateQueryBuilder root, JOIN join, Object object) {
	super(root, join, object);
    }

    public JoinQueryBuilder fetch() {
	fetch = true;
	return this;
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
