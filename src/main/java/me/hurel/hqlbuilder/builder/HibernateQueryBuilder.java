package me.hurel.hqlbuilder.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;
import net.sf.cglib.proxy.Enhancer;

public abstract class HibernateQueryBuilder extends UnfinishedHibernateQueryBuilder {

    protected List<HibernateQueryBuilder> chain;

    protected final HibernateQueryBuilder root;

    HQBInvocationHandler invocationHandler;

    HibernateQueryBuilder(HibernateQueryBuilder root) {
	this.root = root;
	this.chain = root.chain;
	this.invocationHandler = root.invocationHandler;
    }

    HibernateQueryBuilder() {
	this.root = this;
	this.chain = new ArrayList<HibernateQueryBuilder>();
    }

    public static UnfinishedSelectHibernateQueryBuilder select() {
	return new UnfinishedSelectHibernateQueryBuilder();
    }

    public static FromHibernateQueryBuilder selectFrom(Object object) {
	return new SelectHibernateQueryBuilder(object).from(object);
    }

    public static UnfinishedSelectHibernateQueryBuilder select(Object methodCall) {
	return new UnfinishedSelectHibernateQueryBuilder(methodCall);
    }

    public static UnfinishedSelectHibernateQueryBuilder select(Object... methodCall) {
	assert methodCall != null;
	return new UnfinishedSelectHibernateQueryBuilder(methodCall);
    }

    public static <T> T queryOn(T entity) {
	return queryOn(entity, toAlias(entity.getClass()));
    }

    public static <T> T queryOn(T entity, String alias) {
	return queryOn(entity, alias, new HQBInvocationHandler());
    }

    @SuppressWarnings("unchecked")
    private static <T> T queryOn(T entity, String alias, HQBInvocationHandler handler) {
	T o = entity;
	if (!Enhancer.isEnhanced(entity.getClass())) {
	    Enhancer e = new Enhancer();
	    e.setClassLoader(entity.getClass().getClassLoader());
	    e.setSuperclass(entity.getClass());
	    e.setCallback(handler);
	    e.setUseFactory(true);
	    o = (T) e.create();
	}
	handler.declareAlias(o, alias);
	return o;
    }

    public static <T> T andQueryOn(T entity) {
	return andQueryOn(entity, toAlias(UnfinishedHibernateQueryBuilder.getActualClass(entity.getClass())));
    }

    public static <T> T andQueryOn(T entity, String alias) {
	return queryOn(entity, alias, HQBInvocationHandler.getCurrentInvocationHandler());
    }

    public String getQueryString() {
	HQBQueryStringVisitor visitor = new HQBQueryStringVisitor(HQBInvocationHandler.getCurrentInvocationHandler().getAliases(), HQBInvocationHandler
		.getCurrentInvocationHandler().getPaths());
	for (HibernateQueryBuilder builder : chain) {
	    builder.accept(visitor);
	}
	return visitor.getQuery();
    }

    protected abstract void accept(HQBVisitor visitor);

    protected <T extends HibernateQueryBuilder> T chain(T queryBuilder) {
	if (this == root) {
	    chain.add(queryBuilder);
	} else {
	    root.chain(queryBuilder);
	}
	return queryBuilder;
    }

    private Map<Object, String> aliases = new HashMap<Object, String>();
    private Map<Object, String> paths = new HashMap<Object, String>();

    protected void keepAlias(Object entity, String alias) {
	if (this == root) {
	    aliases.put(entity, alias);
	} else {
	    root.keepAlias(entity, alias);
	}
    }

    protected void keepPath(Object entity, String path) {
	if (this == root) {
	    paths.put(entity, path);
	} else {
	    root.keepPath(entity, path);
	}
    }

}
