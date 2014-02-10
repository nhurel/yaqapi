package me.hurel.hqlbuilder.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.hurel.hqlbuilder.functions.Function;
import me.hurel.hqlbuilder.functions.Function.FUNCTION;
import me.hurel.hqlbuilder.internal.ProxyUtil;

import org.apache.commons.lang3.StringUtils;

public class HQBQueryStringVisitor implements HQBVisitor {

    private final StringBuilder query = new StringBuilder();

    private boolean from = false;

    private final Map<Object, String> aliases;

    private final Map<Object, String> paths;

    private final Map<Object, Object> parentEntities;

    private final List<Object> joinedEntities;

    List<Object> parameters;

    public HQBQueryStringVisitor(Map<Object, String> aliases, Map<Object, String> paths, Map<Object, Object> parentEntities, List<Object> joinedEntities) {
	this.aliases = aliases;
	this.paths = paths;
	this.parentEntities = parentEntities;
	this.joinedEntities = joinedEntities;
    }

    public void visit(SelectHibernateQueryBuilder select) {
	query.append("SELECT ");
	if (select.distinct) {
	    query.append("DISTINCT ");
	}
	int i = select.aliases.length;
	for (Object alias : select.aliases) {
	    appendAliasOrPath(alias);
	    if (--i > 0) {
		query.append(',');
	    }
	    query.append(' ');
	}
    }

    public void visit(FromHibernateQueryBuilder fromClause) {
	query.append(from ? ',' : fromClause.join).append(' ').append(ProxyUtil.getActualClass(fromClause.object.getClass()).getSimpleName()).append(' ')
		.append(aliases.get(fromClause.object)).append(' ');
	from = true;
    }

    public void visit(JoinQueryBuilder join) {
	query.append(join.join).append(join.fetch ? " FETCH " : ' ').append(getReducedPath(join.object)).append(' ').append(aliases.get(join.object)).append(' ');
    }

    public void visit(HavingHibernateQueryBuilder<?> builder) {
	query.append(builder.operator).append(' ');
	appendReducedPath(builder.value);
	query.append(' ');
    }

    public void visit(NullConditionHibernateQueryBuilder<?> builder) {
	query.append(builder.operator).append(' ');
    }

    public void visit(WhereNullConditionHibernateQueryBuilder<?> builder) {
	query.append(builder.operator).append(' ');
    }

    public void visit(InConditionHibernateQueryBuilder<?> builder) {
	query.append(builder.operator);
	visitIn(builder.values);
    }

    public void visit(WhereInConditionHibernateQueryBuilder<?> builder) {
	query.append(builder.operator);
	visitIn(builder.values);
    }

    private void visitIn(Object[] values) {
	query.append(" (");
	if (values != null) {
	    int i = 1;
	    for (Object value : values) {
		if (parentEntities.containsKey(value)) {
		    appendAliasOrPath(value);
		} else {
		    query.append('?');
		    addParameter(value);
		}
		if (i < values.length) {
		    query.append(", ");
		}
		i++;
	    }
	}
	query.append(") ");
    }

    public void visit(ConditionHibernateQueryBuilder<?> builder) {
	query.append(builder.operator).append(' ');
	if (parentEntities.containsKey(builder.value)) {
	    appendAliasOrPath(builder.value);
	} else {
	    query.append('?');
	    addParameter(builder.value);
	}
	query.append(' ');
    }

    public void visit(GroupByHibernateQueryBuilder builder) {
	query.append("GROUP BY ");
	int i = builder.properties.length;
	for (Object property : builder.properties) {
	    query.append(getAliasOrPath(property));
	    if (--i > 0) {
		query.append(',');
	    }
	    query.append(' ');
	}
    }

    private void appendAliasOrPath(Object entity) {
	if (entity instanceof Function<?>) {
	    Function<?> function = (Function<?>) entity;
	    query.append(function.getName()).append('(');
	    if (function.getEntity() instanceof Function<?>) {
		appendAliasOrPath(function.getEntity());
	    } else {
		// FIXME : any more elegant way to do this ?
		if (function.getName().equals(FUNCTION.COUNT.getFunction()) && function.getEntity().equals("*")) {
		    query.append(function.getEntity());
		} else {
		    query.append(getAliasOrPath(function.getEntity()));
		}
	    }
	    query.append(')');
	} else {
	    query.append(getAliasOrPath(entity));
	}
    }

    private String getAliasOrPath(Object entity) {
	String result = null;
	if (joinedEntities.contains(entity)) {
	    result = aliases.get(entity);
	}
	if (result == null) {
	    Object knownJoinParent = entity;
	    while (knownJoinParent != null && !joinedEntities.contains(knownJoinParent)) {
		knownJoinParent = parentEntities.get(knownJoinParent);
	    }
	    if (!joinedEntities.contains(knownJoinParent)) {
		throw new RuntimeException("Failed to continue query after [" + query.toString()
			+ "] because an entity was used in clause but neither it nor its parent appears explicitely in the from clause");
	    }
	    if (!aliases.containsKey(knownJoinParent)) {
		throw new RuntimeException("Failed to continue query after [" + query.toString() + "] because alias of the parent joined entity is unknown");
	    }
	    result = paths.get(entity);
	    if (entity != knownJoinParent) {
		String joinedParentPath = paths.get(knownJoinParent);
		String end = StringUtils.difference(joinedParentPath, result);
		result = new StringBuilder(aliases.get(knownJoinParent)).append(end.startsWith(".") ? "" : '.').append(end).toString();
	    }
	}
	return result;
    }

    private void appendReducedPath(Object entity) {
	if (entity instanceof Function<?>) {
	    Function<?> function = (Function<?>) entity;
	    query.append(function.getName()).append('(');
	    if (function.getEntity() instanceof Function<?>) {
		appendAliasOrPath(function.getEntity());
	    } else {
		// FIXME : any more elegant way to do this ?
		if (function.getName().equals(FUNCTION.COUNT.getFunction()) && function.getEntity().equals("*")) {
		    query.append(function.getEntity());
		} else {
		    query.append(getReducedPath(function.getEntity()));
		}
	    }
	    query.append(')');
	} else {
	    query.append(getReducedPath(entity));
	}
    }

    private String getReducedPath(Object entity) {
	String result = null;
	result = paths.get(entity);
	Object knownJoinParent = parentEntities.get(entity);
	Object previousKnownParent = null;
	while (knownJoinParent != null && previousKnownParent == null) {
	    if (joinedEntities.contains(knownJoinParent)) {
		previousKnownParent = knownJoinParent;
	    }
	    knownJoinParent = parentEntities.get(knownJoinParent);
	}
	if (previousKnownParent != null) {
	    String parentPath = paths.get(previousKnownParent);
	    String end = StringUtils.difference(parentPath, result);
	    result = new StringBuilder(aliases.get(previousKnownParent)).append(end).toString();
	}
	return result;
    }

    private void addParameter(Object parameter) {
	if (parameters == null) {
	    parameters = new ArrayList<Object>();
	}
	parameters.add(parameter);
    }

    public String getQuery() {
	return query.toString();
    }

    public List<Object> getParameters() {
	return parameters;
    }
}
