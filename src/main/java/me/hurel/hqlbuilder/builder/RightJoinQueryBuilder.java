package me.hurel.hqlbuilder.builder;

public class RightJoinQueryBuilder extends AbstractOuterJoinQueryBuilder {

    RightJoinQueryBuilder(HibernateQueryBuilder root, Object object) {
	super(root, JOIN.RIGHT);
	this.object = object;
    }

    public RightJoinQueryBuilder fetch() {
	fetch = true;
	return this;
    }

}
