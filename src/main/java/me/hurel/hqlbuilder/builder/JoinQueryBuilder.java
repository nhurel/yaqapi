package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.JoinClause;
import me.hurel.hqlbuilder.WithClause;
import me.hurel.hqlbuilder.functions.Function;

public class JoinQueryBuilder extends FromHibernateQueryBuilder implements JoinClause {

    JoinQueryBuilder(HibernateQueryBuilder root, JOIN join, Object object) {
	super(root, join, object);
    }

    public JoinClause fetch() {
	fetch = true;
	return this;
    }

    public <T> WithClause<T> with(T methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WITH, methodCall));
    }

    public <T> WithClause<T> with(Function<T> methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WITH, methodCall));
    }

    public <T> WithClause<T> withGroup(T methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WITH, methodCall).group());
    }

    public <T> WithClause<T> withGroup(Function<T> methodCall) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.WITH, methodCall).group());
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
