package me.hurel.hqlbuilder.builder;

public class LeftJoinQueryBuilder extends AbstractOuterJoinQueryBuilder {

    LeftJoinQueryBuilder(HibernateQueryBuilder root, String object, String alias) {
	super(root, JOIN.LEFT);
	this.object = object;
	this.alias = alias;
    }

    public LeftJoinQueryBuilder fetch() {
	fetch = true;
	return this;
    }

}
