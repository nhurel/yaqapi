package me.hurel.hqlbuilder;

public interface WithClause<T> extends WhereClause<T> {

    public WithCondition<T> isNull();

    public WithCondition<T> isNotNull();

    public WithCondition<T> isEqualTo(T entity);

    public WithCondition<T> isNotEqualTo(T entity);

    public WithCondition<T> isGreaterThan(T entity);

    public WithCondition<T> isGreaterEqualThan(T entity);

    public WithCondition<T> isLessThan(T entity);

    public WithCondition<T> isLessEqualThan(T entity);

    public WithCondition<T> isLike(T entity);

    public WithCondition<T> isNotLike(T entity);

    public WithCondition<T> isIn(T... values);

    public WithCondition<T> isNotIn(T... values);
}
