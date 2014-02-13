package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.builder.FromClause;
import me.hurel.hqlbuilder.functions.Function;

public interface AbstractFromClause extends QueryBuilder {

    public JoinClause innerJoin(Object methodCall);

    public JoinClause innerJoinFetch(Object methodCall);

    public JoinClause leftJoin(Object methodCall);

    public JoinClause leftJoinFetch(Object methodCall);

    public JoinClause rightJoin(Object methodCall);

    public JoinClause rightJoinFetch(Object methodCall);

    public FromClause andFrom(Object entity);

    public <T> WhereClause<T> where(T methodCall);

    public <T> WhereClause<T> where(Function<T> methodCall);

    public <T> WhereClause<T> whereGroup(T methodCall);

    public <T> WhereClause<T> whereGroup(Function<T> methodCall);

    public GroupByClause groupBy(Object... properties);

}