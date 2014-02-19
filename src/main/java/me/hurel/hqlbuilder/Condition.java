package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.functions.Function;

public interface Condition<T> extends QueryBuilder {

    public <U> WhereClause<U> and(U methodCall);

    public <U> WhereClause<U> or(U methodCall);

    public <U> WhereClause<U> and(Function<U> methodCall);

    public <U> WhereClause<U> or(Function<U> methodCall);

    public GroupByClause groupBy(Object... properties);

    public <U> WhereClause<U> orGroup(U methodCall);

    public <U> WhereClause<U> andGroup(U methodCall);

    public <U> WhereClause<U> orGroup(Function<U> methodCall);

    public <U> WhereClause<U> andGroup(Function<U> methodCall);

    public Condition<T> closeGroup();

    public Condition<T> closeExists();

    public ExistsClause andExists(Object methodCall);

    public ExistsClause andNotExists(Object methodCall);

    public ExistsClause orExists(Object methodCall);

    public ExistsClause orNotExists(Object methodCall);
}