package me.hurel.hqlbuilder;

public interface Condition<T> extends QueryBuilder {
    public <U> WhereClause<U> and(U methodCall);

    public <U> WhereClause<U> or(U methodCall);

    public GroupByClause groupBy(Object... properties);

    public <U> WhereClause<U> orGroup(U methodCall);

    public <U> WhereClause<U> andGroup(U methodCall);

    public Condition<T> closeGroup();
}