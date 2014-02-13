package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.functions.Function;

public interface JoinClause extends FromClause {

    public JoinClause fetch();

    public <T> WithClause<T> with(T methodCall);

    public <T> WithClause<T> with(Function<T> methodCall);

    public <T> WithClause<T> withGroup(T methodCall);

    public <T> WithClause<T> withGroup(Function<T> methodCall);
}