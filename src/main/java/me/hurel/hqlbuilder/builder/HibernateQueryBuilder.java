package me.hurel.hqlbuilder.builder;

import java.util.ArrayList;
import java.util.List;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;
import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.proxy.Enhancer;

import org.hibernate.Session;

public abstract class HibernateQueryBuilder extends UnfinishedHibernateQueryBuilder {

    protected List<HibernateQueryBuilder> chain;

    protected final HibernateQueryBuilder root;

    HQBInvocationHandler invocationHandler;

    HibernateQueryBuilder(HibernateQueryBuilder root) {
	super(root.session);
	this.root = root;
	this.chain = root.chain;
	this.invocationHandler = root.invocationHandler;
    }

    HibernateQueryBuilder(Session session) {
	super(session);
	this.root = this;
	this.chain = new ArrayList<HibernateQueryBuilder>();
    }

    public static UnfinishedSelectHibernateQueryBuilder select(final Session session) {
	return new UnfinishedSelectHibernateQueryBuilder(session);
    }

    public static FromHibernateQueryBuilder selectFrom(final Session session, Object object) {
	Class<?> objectClass = getActualClass(object.getClass());
	return new SelectHibernateQueryBuilder(session, toAlias(objectClass)).from(objectClass);
    }

    public static Class<?> getActualClass(Class<?> objectClass) {
	Class<?> actualClass = objectClass;
	if (Enhancer.isEnhanced(actualClass)) {
	    try {
		actualClass = Class.forName(ReflectUtils.getClassInfo(actualClass).getSuperType().getClassName());
	    } catch (ClassNotFoundException e) {
		throw new RuntimeException(e);
	    }
	}
	return actualClass;
    }

    public static UnfinishedSelectHibernateQueryBuilder select(final Session session, String alias) {
	return new UnfinishedSelectHibernateQueryBuilder(session, alias);
    }

    public static UnfinishedSelectHibernateQueryBuilder select(final Session session, String... aliases) {
	return new UnfinishedSelectHibernateQueryBuilder(session, aliases);
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
	return andQueryOn(entity, toAlias(getActualClass(entity.getClass())));
    }

    public static <T> T andQueryOn(T entity, String alias) {
	return queryOn(entity, alias, HQBInvocationHandler.getCurrentInvocationHandler());
    }

    public String getQueryString() {
	HQBQueryStringVisitor visitor = new HQBQueryStringVisitor();
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

}
