package me.hurel.hqlbuilder.functions;

public interface Function<T> {

    public String getName();

    public Object getEntity();

    public enum FUNCTION {
	AVERAGE("avg"), COUNT("count"), DISTINCT("distinct"), MAX("max"), MIN("min"), SUM("sum"), SIZE("size");
	String function;

	FUNCTION(String function) {
	    this.function = function;
	}

	public String getFunction() {
	    return function;
	}
    }

}