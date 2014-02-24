package me.hurel.hqlbuilder;

public interface OrderByClause extends QueryBuilder {

    OrderByClause asc();

    OrderByClause desc();

    OrderByClause andBy(Object... orders);

}
