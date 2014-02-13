package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.builder.FromClause;

public interface SelectClause {

    public FromClause from(Object entity);

    public SelectClause distinct();

}
