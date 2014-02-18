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
import java.util.Random;
import java.util.UUID;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HQBInvocationHandler implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HQBInvocationHandler.class);

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
		newAlias = alias + i;
	    }
	    alias = newAlias;
	}
	aliases.put(entity, alias);
	paths.put(entity, alias);
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	Object returnValue;
	if (method.getName().equals("equals") && args.length == 1) {
	    // Equals of the proxied entities must be safe
	    returnValue = (obj == args[0]);
	} else {
	    returnValue = proxy.invokeSuper(obj, args);
	}
	if (isGetter(method.getName())) {
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

		String fieldName = toPropertyName(method.getName());
		currentPath.append('.').append(fieldName);
		Class<?> returnType = method.getReturnType();
                if (returnValue == null || returnType.isPrimitive() || (!Enhancer.isEnhanced(returnValue.getClass()))) {
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
	    returnValue = buildProxy(getParameter(method), returnType, this);
	    declareAlias(returnValue, fieldName);
	} else if (returnType.isInterface()) {
	    returnValue = buildProxy(null, returnType, this);
	} else {
	    if (returnType.isPrimitive() || returnType.getCanonicalName().startsWith("java.lang")) {
		returnValue = random(returnType);
	    } else {
		returnValue = returnType.newInstance();
		returnValue = buildProxy(returnValue, returnType, this);
	    }
	}

	return returnValue;
    }

    private byte lastByte = Byte.MIN_VALUE;
    private boolean lastBoolean = false;
    private int lastInt = Integer.MIN_VALUE;
    private char lastChar = Character.MIN_VALUE;
    private double lastDouble = Double.MIN_VALUE;
    private float lastFloat = Float.MIN_VALUE;
    private long lastLong = Long.MIN_VALUE;
    private short lastShort = Short.MIN_VALUE;

    Random r = new Random();

    private Object random(Class<?> randomizeType) {
	if (String.class.equals(randomizeType)) {
	    return UUID.randomUUID().toString();
	} else if (boolean.class.equals(randomizeType) || Boolean.class.equals(randomizeType)) {
	    lastBoolean = !lastBoolean;
	    // TODO LOG WARN ABOUT BOOLEAN SUPPORT
	    return lastBoolean;
	} else if (Byte.class.equals(randomizeType) || byte.class.equals(randomizeType)) {
	    return ++lastByte;
	} else if (int.class.equals(randomizeType) || Integer.class.equals(randomizeType)) {
	    return ++lastInt;
	} else if (char.class.equals(randomizeType) || Character.class.equals(randomizeType)) {
	    return ++lastChar;
	} else if (double.class.equals(randomizeType) || Double.class.equals(randomizeType)) {
	    return ++lastDouble;
	} else if (float.class.equals(randomizeType) || Float.class.equals(randomizeType)) {
	    return ++lastFloat;
	} else if (long.class.equals(randomizeType) || Long.class.equals(randomizeType)) {
	    return ++lastLong;
	} else if (short.class.equals(randomizeType) || Short.class.equals(randomizeType)) {
	    return ++lastShort;
	}
	return null;
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
