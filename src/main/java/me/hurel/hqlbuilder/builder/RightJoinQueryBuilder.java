package me.hurel.hqlbuilder.builder;


public class RightJoinQueryBuilder extends AbstractOuterJoinQueryBuilder {

    RightJoinQueryBuilder(HibernateQueryBuilder root, String object, String alias) {
	super(root, JOIN.RIGHT);
	this.object = object;
	this.alias = alias;
    }

    public RightJoinQueryBuilder fetch() {
	fetch = true;
	return this;
    }

}
