package me.hurel.hqlbuilder.functions;

import me.hurel.hqlbuilder.internal.HQBInvocationHandler;

/**
 * @author n.hurel
 */
public class MultiParameterFunction<T> implements Function<T> {

    final Object[] entity;

    final String name;

    public MultiParameterFunction(String name, T... entity) {
        this.entity = HQBInvocationHandler.getCurrentInvocationHandler().poll(entity);
        this.name = name;
    }


    public MultiParameterFunction(FUNCTION name, T... entity) {
        this(name.getFunction(), entity);
    }

    public String getName() {
        return name;
    }

    public Object[] getEntity() {
        return entity;
    }
}
