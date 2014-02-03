package me.hurel.hqlbuilder.internal;

import static me.hurel.hqlbuilder.builder.HibernateQueryBuilder.*;
import static me.hurel.hqlbuilder.builder.UnfinishedHibernateQueryBuilder.*;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class HQBInvocationHandler implements MethodInterceptor {

    private static ThreadLocal<HQBInvocationHandler> instance = new ThreadLocal<HQBInvocationHandler>();

    private StringBuilder currentPath;

    private String root;

    private Class<?> rootEntity;

    private boolean started = false;

    public HQBInvocationHandler(Class<?> rootEntity) {
	this(rootEntity, toAlias(rootEntity));
    }

    public HQBInvocationHandler(Class<?> rootEntity, String root) {
	super();
	this.root = root;
	this.rootEntity = getActualClass(rootEntity);
	reset();
	instance.set(this);
    }

    public static HQBInvocationHandler getCurrentInvocationHandler() {
	return instance.get();
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	try {
	    Object returnValue = proxy.invokeSuper(obj, args);

	    if (!started) {
		if (getActualClass(obj.getClass()).equals(rootEntity)) {
		    currentPath.append(root);
		} else {
		    currentPath.append(toAlias(getActualClass(obj.getClass())));
		}
	    }

	    currentPath.append('.').append(toPropertyName(proxy.getSignature().getName()));
	    Class<?> returnType = Class.forName(proxy.getSignature().getReturnType().getClassName());
	    if (!returnType.isPrimitive() && !returnType.getCanonicalName().startsWith("java.")) {
		if (returnValue == null) {
		    returnValue = returnType.newInstance();
		}
		returnValue = andQueryOn(returnValue);
	    }
	    return returnValue;
	} catch (Throwable t) {
	    throw t;
	} finally {
	    started = true;
	}
    }

    public void reset() {
	currentPath = new StringBuilder(); // new StringBuilder(root);
	started = false;
    }

    public String getCurrentPath() {
	return currentPath.toString();
    }

}
