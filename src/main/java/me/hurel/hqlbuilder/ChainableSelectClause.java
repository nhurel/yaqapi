package me.hurel.hqlbuilder;

/**
 * Created by nathan on 22/03/14.
 */
public interface ChainableSelectClause extends SelectClause {

    public AliasableSelectClause andSelect(Object methodCall);

}
