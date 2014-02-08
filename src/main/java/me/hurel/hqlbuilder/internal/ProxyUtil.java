package me.hurel.hqlbuilder.internal;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.lang3.StringUtils;

public class ProxyUtil {

    public static Class<?> getParameter(Method method) {
	Class<?> result = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
	return result;
    }

    public static String toAlias(Class<?> entity) {
	return StringUtils.uncapitalize(entity.getSimpleName());
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

    public static <T> T buildProxy(T entity, Class<?> returnType, HQBInvocationHandler handler) {
	return buildProxy(entity, returnType, null, handler);
    }

    public static <T> T buildProxy(Class<?> returnType, Class<?> implementation, HQBInvocationHandler handler) {
	return buildProxy(null, returnType, implementation, handler);
    }

    @SuppressWarnings("unchecked")
    public static <T> T buildProxy(T entity, Class<?> returnType, Class<?> implementation, HQBInvocationHandler handler) {
	T o = entity;
	if (entity == null || !Enhancer.isEnhanced(entity.getClass())) {
	    Enhancer e = new Enhancer();
	    e.setClassLoader(returnType.getClassLoader());
	    if (returnType != null) {
		e.setSuperclass(returnType);
	    }
	    if (implementation != null) {
		e.setInterfaces(new Class[] { implementation });
	    }
	    e.setCallback(handler);
	    e.setUseFactory(true);
	    o = (T) e.create();
	}
	handler.declareAlias(o);
	return o;
    }
}
