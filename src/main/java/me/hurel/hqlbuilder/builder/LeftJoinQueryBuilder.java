package me.hurel.hqlbuilder.builder;

public class LeftJoinQueryBuilder extends AbstractOuterJoinQueryBuilder {

    LeftJoinQueryBuilder(HibernateQueryBuilder root, Object object) {
	super(root, JOIN.LEFT, object);
    }

    public LeftJoinQueryBuilder fetch() {
	fetch = true;
	return this;
    }

}
