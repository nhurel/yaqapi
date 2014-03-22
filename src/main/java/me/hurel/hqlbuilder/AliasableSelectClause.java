package me.hurel.hqlbuilder;

/**
 * Created by nathan on 21/03/14.
 */
public interface AliasableSelectClause extends ChainableSelectClause {

    public ChainableSelectClause as(String alias);

}
