package me.hurel.hqlbuilder;


public interface FromClause extends AbstractFromClause {

    public ExistsClause whereExists(Object methodCall);

    public ExistsClause whereNotExists(Object methodCall);

    public OrderByClause orderBy(Object... orders);

}