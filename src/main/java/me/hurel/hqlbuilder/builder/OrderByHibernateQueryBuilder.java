package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.OrderByClause;

public class OrderByHibernateQueryBuilder extends HibernateQueryBuilder implements OrderByClause {
    Object[] aliases;

    OrderByHibernateQueryBuilder(HibernateQueryBuilder root, Object... orders) {
        super(root);
        this.aliases = orders;
    }

    @Override
    void accept(HQBVisitor visitor) {
        visitor.visit(this);
    }
}
