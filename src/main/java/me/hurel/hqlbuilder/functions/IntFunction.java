package me.hurel.hqlbuilder.functions;

public class IntFunction implements Function<Integer> {

    private final String name;

    private final Object entity;

    public IntFunction(FUNCTION name, Object entity) {
	this(name.getFunction(), entity);
    }

    public IntFunction(String name, Object entity) {
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
