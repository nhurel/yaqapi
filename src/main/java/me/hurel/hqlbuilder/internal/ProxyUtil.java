/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.lang3.StringUtils;

public class ProxyUtil {

    public static Class<?> getParameter(Method method) {
	Class<?> result = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
	return result;
    }

    public static boolean isFinal(Class<?> objectClass) {
	return (Modifier.FINAL & objectClass.getModifiers()) == Modifier.FINAL;
    }

    public static boolean usePrimitive(Object o) {
	return boolean.class.equals(o.getClass()) || o instanceof Boolean;
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

    public static boolean ignoreClass(Class<?> objectClass) {
	return objectClass.getCanonicalName().startsWith("java.");
    }

    public static Constructor<?> findBestConstructor(Constructor<?>[] constructors) {
	// First try : find a constructor with only primitive arguments
	for (Constructor<?> c : constructors) {
	    Class<?>[] parameterTypes = c.getParameterTypes();
	    if (parameterTypes.length == 0) {
		return c;
	    } else {
		boolean useConstructor = true;
		for (Class<?> parameterType : parameterTypes) {
		    if (!parameterType.isPrimitive()) {
			useConstructor = false;
			break;
		    }
		}
		if (useConstructor) {
		    return c;
		}
	    }
	}
	// Second try : Okay, let's give a chance to String parameters too
	for (Constructor<?> c : constructors) {
	    Class<?>[] parameterTypes = c.getParameterTypes();
	    if (parameterTypes.length == 0) {
		return c;
	    } else {
		boolean useConstructor = true;
		for (Class<?> parameterType : parameterTypes) {
		    if (!parameterType.isPrimitive() && !parameterType.equals(String.class)) {
			useConstructor = false;
			break;
		    }
		}
		if (useConstructor) {
		    return c;
		}
	    }
	}
	return null;
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
	return buildProxy(entity, returnType, null, handler, null, null);
    }

    public static <T> T buildProxy(T entity, Class<?> returnType, HQBInvocationHandler handler, Class<?>[] paramTypes, Object[] params) {
	return buildProxy(entity, returnType, null, handler, paramTypes, params);
    }

    public static <T> T buildProxy(Class<?> returnType, Class<?> implementation, HQBInvocationHandler handler) {
	return buildProxy(null, returnType, implementation, handler, null, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T buildProxy(T entity, Class<?> returnType, Class<?> implementation, HQBInvocationHandler handler, Class<?>[] paramTypes, Object[] params) {
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
	    if (paramTypes == null) {
		o = (T) e.create();
	    } else {
		o = (T) e.create(paramTypes, params);
	    }
	}
	handler.declareAlias(o);
	return o;
    }
}
