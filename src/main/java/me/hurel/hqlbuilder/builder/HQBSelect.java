package me.hurel.hqlbuilder.builder;

public interface HQBSelect {

    public FromHibernateQueryBuilder from(Object entity);

    public HQBSelect distinct();

}
