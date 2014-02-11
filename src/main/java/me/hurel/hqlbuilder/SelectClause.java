package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.builder.FromHibernateQueryBuilder;

public interface SelectClause {

    public FromHibernateQueryBuilder from(Object entity);

    public SelectClause distinct();

}
