package me.hurel.hqlbuilder.builder;

import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.lang3.StringUtils;

public class UnfinishedHibernateQueryBuilder {

    UnfinishedHibernateQueryBuilder() {
	super();
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

    public static boolean isGetter(String method) {
	return method.startsWith("get") || method.startsWith("is");
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

}
