package me.hurel.hqlbuilder.functions;

public class Function<T> {

    private final String name;

    private final T entity;

    public Function(String name, T entity) {
	super();
	this.name = name;
	this.entity = entity;
    }

    public Function(FUNCTION name, T entity) {
	this(name.getFunction(), entity);
    }

    public String getName() {
	return name;
    }

    public T getEntity() {
	return entity;
    }

    public enum FUNCTION {
	AVERAGE("avg"), COUNT("count"), DISTINCT("distinct"), MAX("max"), MIN("min"), SUM("sum");
	String function;

	FUNCTION(String function) {
	    this.function = function;
	}

	public String getFunction() {
	    return function;
	}
    }

}
