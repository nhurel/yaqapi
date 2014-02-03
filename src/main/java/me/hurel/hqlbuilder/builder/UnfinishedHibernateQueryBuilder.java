package me.hurel.hqlbuilder.builder;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

public class UnfinishedHibernateQueryBuilder {
    protected final Session session;

    UnfinishedHibernateQueryBuilder(Session session) {
	super();
	this.session = session;
    }

    public static String toAlias(Class<?> entity) {
	return StringUtils.uncapitalize(entity.getSimpleName());
    }

    public static String toSimpleName(String className) {
	return StringUtils.substringAfterLast(className, ".");
    }

    public static String toPropertyName(String getter) {
	if (getter.startsWith("is")) {
	    return StringUtils.uncapitalize(getter.substring(2));
	}
	return StringUtils.uncapitalize(getter.substring(3));
    }

}
