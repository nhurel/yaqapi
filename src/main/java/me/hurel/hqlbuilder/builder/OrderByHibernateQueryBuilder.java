package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.OrderByClause;

public class OrderByHibernateQueryBuilder extends HibernateQueryBuilder implements OrderByClause {
    Object[] aliases;

    PRIORITY priority = null;

    String separator;

    OrderByHibernateQueryBuilder(HibernateQueryBuilder root, SEPARATOR separator, Object... orders) {
	super(root);
	this.aliases = orders;
	this.separator = separator.separator;
    }

    public OrderByClause asc() {
	this.priority = PRIORITY.ASC;
	return this;
    }

    public OrderByClause desc() {
	this.priority = PRIORITY.DESC;
	return this;
    }

    public OrderByClause andBy(Object... orders) {
	return chain(new OrderByHibernateQueryBuilder(this, SEPARATOR.COMMA, orders));
    }

    @Override
    void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

    public static enum PRIORITY {
	ASC("ASC"), DESC("DESC");
	String priority;

	PRIORITY(String priority) {
	    this.priority = priority;
	}
    }
}
