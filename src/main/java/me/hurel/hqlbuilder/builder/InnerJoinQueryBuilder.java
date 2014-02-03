package me.hurel.hqlbuilder.builder;

public class InnerJoinQueryBuilder extends AbstractJoinQueryBuilder {

    InnerJoinQueryBuilder(HibernateQueryBuilder root, String object, String alias) {
	super(root, JOIN.INNER);
	this.object = object;
	this.alias = alias;
    }

    public InnerJoinQueryBuilder fetch() {
	fetch = true;
	return this;
    }

    @Override
    protected void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
