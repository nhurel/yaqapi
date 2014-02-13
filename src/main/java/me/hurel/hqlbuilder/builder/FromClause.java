package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.AbstractFromClause;
import me.hurel.hqlbuilder.ExistsClause;

public interface FromClause extends AbstractFromClause {

    public ExistsClause whereExists(Object methodCall);

    public ExistsClause whereNotExists(Object methodCall);

}