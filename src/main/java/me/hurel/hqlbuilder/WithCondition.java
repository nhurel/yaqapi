package me.hurel.hqlbuilder;

public interface WithCondition<T> extends Condition<T>, FromClause {

    public <U> WithClause<U> and(U methodCall);

    public <U> WithClause<U> or(U methodCall);

    public GroupByClause groupBy(Object... properties);

    public <U> WithClause<U> orGroup(U methodCall);

    public <U> WithClause<U> andGroup(U methodCall);

    public WithCondition<T> closeGroup();

}
