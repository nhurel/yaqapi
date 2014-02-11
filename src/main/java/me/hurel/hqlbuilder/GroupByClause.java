package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.functions.Function;

public interface GroupByClause extends QueryBuilder {

    public <T> WhereClause<T> having(T property);

    public <T> WhereClause<T> having(Function<T> property);

}