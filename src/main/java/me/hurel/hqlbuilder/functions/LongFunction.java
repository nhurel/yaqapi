package me.hurel.hqlbuilder.functions;


public class LongFunction implements Function<Long> {
    private final String name;

    private final Object entity;

    public LongFunction(FUNCTION name, Object entity) {
        this(name.getFunction(), entity);
    }

    public LongFunction(String name, Object entity) {
        this.name = name;
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public Object getEntity() {
        return entity;
    }
}
