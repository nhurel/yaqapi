package me.hurel.hqlbuilder.internal;

import static me.hurel.hqlbuilder.builder.HibernateQueryBuilder.*;
import static me.hurel.hqlbuilder.builder.UnfinishedHibernateQueryBuilder.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

public class HQBInvocationHandler implements MethodInterceptor {

    private static final Logger LOGGER = Logger.getLogger(HQBInvocationHandler.class);

    private static ThreadLocal<HQBInvocationHandler> instance = new ThreadLocal<HQBInvocationHandler>();

    private StringBuilder currentPath;

    private String currentAlias;

    private Map<Object, String> aliases = new HashMap<Object, String>();

    private boolean started = false;

    public HQBInvocationHandler() {
	super();
	reset();
	instance.set(this);
    }

    public static HQBInvocationHandler getCurrentInvocationHandler() {
	return instance.get();
    }

    public void declareAlias(Object entity, String alias) {
	aliases.put(entity, alias);
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	Object returnValue = proxy.invokeSuper(obj, args);
	if (isGetter(proxy.getSignature().getName())) {
	    try {
		if (!started) {
		    if (aliases.get(obj) != null) {
			currentPath.append(aliases.get(obj));
		    } else {
			currentPath.append(toAlias(getActualClass(obj.getClass())));
		    }
		}

		String fieldName = toPropertyName(proxy.getSignature().getName());
		currentPath.append('.').append(fieldName);
		Class<?> returnType = Class.forName(proxy.getSignature().getReturnType().getClassName());
		if (!returnType.isPrimitive() && !returnType.getCanonicalName().startsWith("java.")) {
		    if (returnValue == null) {
			returnValue = returnType.newInstance();
			returnValue = andQueryOn(returnValue);
			Class<?> objClass = getActualClass(obj.getClass());
			Field field = objClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, returnValue);
		    }
		}
		if (aliases.get(returnValue) != null) {
		    currentAlias = aliases.get(returnValue);
		} else {
		    currentAlias = fieldName;
		}
		return returnValue;
	    } catch (Throwable t) {
		LOGGER.error("", t);
		throw t;
	    } finally {
		started = true;
	    }
	}
	return returnValue;
    }

    public void reset() {
	currentPath = new StringBuilder(); // new StringBuilder(root);
	started = false;
    }

    public String getCurrentPath() {
	return currentPath.toString();
    }

    public String getCurrentAlias() {
	return currentAlias;
    }

}
