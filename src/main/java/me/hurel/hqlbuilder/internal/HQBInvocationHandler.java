package me.hurel.hqlbuilder.internal;

import static me.hurel.hqlbuilder.internal.ProxyUtil.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

public class HQBInvocationHandler implements MethodInterceptor {

    private static final Logger LOGGER = Logger.getLogger(HQBInvocationHandler.class);

    private static ThreadLocal<HQBInvocationHandler> instance = new ThreadLocal<HQBInvocationHandler>();

    private StringBuilder currentPath;

    private String currentAlias;

    private Map<Object, String> aliases = new HashMap<Object, String>();

    private Map<Object, String> paths = new HashMap<Object, String>();

    /**
     * Map used to retrieve parent proxy of an entity. Key is the child entity
     * and value is its parent
     */
    private Map<Object, Object> parentsEntities = new HashMap<Object, Object>();;

    private Object lastEntity;

    private List<String> fullPathHistory;

    private List<String> aliasesHistory;

    private boolean started = false;

    public HQBInvocationHandler() {
	super();
	reset();
	fullPathHistory = new ArrayList<String>();
	aliasesHistory = new ArrayList<String>();
	instance.set(this);
    }

    public static HQBInvocationHandler getCurrentInvocationHandler() {
	return instance.get();
    }

    public void declareAlias(Object entity) {
	String alias = toAlias(getActualClass(entity.getClass()));
	declareAlias(entity, alias);
    }

    private void declareAlias(Object entity, String alias) {
	if (aliases.containsValue(alias)) {
	    int i = 2;
	    String newAlias = alias + i;
	    while (aliases.containsValue(newAlias)) {
		i++;
	    }
	    alias = newAlias;
	}
	aliases.put(entity, alias);
	paths.put(entity, alias);
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	Object returnValue;
	if (proxy.getSignature().getName().equals("equals") && args.length == 1) {
	    // Equals of the proxied entities must be safe
	    returnValue = (obj == args[0]);
	} else {
	    returnValue = proxy.invokeSuper(obj, args);
	}
	if (isGetter(proxy.getSignature().getName())) {
	    try {
		if (lastEntity == null || !obj.equals(lastEntity)) {
		    historise();
		}

		if (!started) {
		    if (paths.get(obj) != null) {
			currentPath.append(paths.get(obj));
		    } else {
			currentPath.append(toAlias(getActualClass(obj.getClass())));
		    }
		}

		String fieldName = toPropertyName(proxy.getSignature().getName());
		currentPath.append('.').append(fieldName);
		Class<?> returnType = method.getReturnType();
		if (returnValue == null) {
		    returnValue = getReturnValue(returnType, method, fieldName);
		    Class<?> objClass = getActualClass(obj.getClass());
		    Field field = objClass.getDeclaredField(fieldName);
		    field.setAccessible(true);
		    field.set(obj, returnValue);
		    parentsEntities.put(returnValue, obj);
		}
		if (aliases.get(returnValue) != null) {
		    currentAlias = aliases.get(returnValue);
		} else {
		    currentAlias = fieldName;
		}
		lastEntity = returnValue;
		paths.put(returnValue, getCurrentPath());
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

    private Object getReturnValue(Class<?> returnType, Method method, String fieldName) throws InstantiationException, IllegalAccessException {
	Object returnValue;
	if (Collection.class.isAssignableFrom(returnType)) {
	    returnValue = buildProxy(getParameter(method), getProxyCollection(returnType), this);
	    declareAlias(returnValue, fieldName);
	} else if (returnType.isInterface()) {
	    returnValue = buildProxy(null, returnType, this);
	} else {
	    returnValue = returnType.newInstance();
	}

	if (!returnType.isPrimitive() && !returnType.getCanonicalName().startsWith("java.")) {
	    returnValue = buildProxy(returnValue, returnType, this);
	} else if (returnType.getCanonicalName().equals("java.lang.String")) {
	    returnValue = UUID.randomUUID().toString();
	}
	return returnValue;
    }

    public void reset() {
	currentPath = new StringBuilder();
	currentAlias = null;
	started = false;
    }

    private void historise() {
	if (started) {
	    paths.put(lastEntity, getCurrentPath());
	    fullPathHistory.add(getCurrentPath());
	    aliasesHistory.add(getCurrentAlias());
	}
	reset();
    }

    public Map<Object, String> getAliases() {
	return Collections.unmodifiableMap(aliases);
    }

    public Map<Object, String> getPaths() {
	return Collections.unmodifiableMap(paths);
    }

    public Map<Object, Object> getParentsEntities() {
	return Collections.unmodifiableMap(parentsEntities);
    }

    public String getCurrentPath() {
	return currentPath.toString();
    }

    public String getCurrentAlias() {
	return currentAlias;
    }

}
