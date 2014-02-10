package me.hurel.hqlbuilder.builder;

import java.util.List;

public abstract class UnfinishedHibernateQueryBuilder {

    /**
     * This list chains all the query parts that has been added
     */
    protected List<UnfinishedHibernateQueryBuilder> chain;

    /**
     * The only root element of the query
     */
    protected final HibernateQueryBuilder root;

    UnfinishedHibernateQueryBuilder() {
	super();
	if (this instanceof HibernateQueryBuilder) {
	    root = (HibernateQueryBuilder) this;
	} else {
	    root = null;
	}
    }

    UnfinishedHibernateQueryBuilder(HibernateQueryBuilder root) {
	super();
	this.root = root;
    }

    <T extends UnfinishedHibernateQueryBuilder> T chain(T queryBuilder) {
	if (this == root) {
	    chain.add(queryBuilder);
	} else {
	    root.chain(queryBuilder);
	}
	return queryBuilder;
    }

    abstract void accept(HQBVisitor visitor);

}
