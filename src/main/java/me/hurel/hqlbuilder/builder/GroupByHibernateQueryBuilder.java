package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.GroupByClause;
import me.hurel.hqlbuilder.WhereClause;
import me.hurel.hqlbuilder.functions.Function;

public class GroupByHibernateQueryBuilder extends HibernateQueryBuilder implements GroupByClause {

    final Object[] properties;

    GroupByHibernateQueryBuilder(HibernateQueryBuilder root, Object... properties) {
	super(root);
	this.properties = properties;
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.builder.GroupByClause#having(T)
     */
    public <T> WhereClause<T> having(T property) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.HAVING, property));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * me.hurel.hqlbuilder.builder.GroupByClause#having(me.hurel.hqlbuilder.
     * functions.Function)
     */
    public <T> WhereClause<T> having(Function<T> property) {
	return chain(new WhereHibernateQueryBuilder<T>(this, SEPARATOR.HAVING, property));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
