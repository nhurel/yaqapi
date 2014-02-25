package me.hurel.hqlbuilder;


public interface SelectClause {

    public FromClause from(Object entity);

    public SelectClause distinct();

}
