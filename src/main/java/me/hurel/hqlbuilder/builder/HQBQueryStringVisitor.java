/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

import me.hurel.hqlbuilder.functions.Function;
import me.hurel.hqlbuilder.functions.Function.FUNCTION;
import me.hurel.hqlbuilder.internal.ProxyUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HQBQueryStringVisitor implements HQBVisitor {

    private final StringBuilder query = new StringBuilder();
    private final Map<Object, String> aliases;
    private final Map<Object, String> paths;
    private final Map<Object, Object> parentEntities;
    private final List<Object> joinedEntities;
    List<Object> parameters;
    private boolean from = false;
    private int params = 1;

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
	int i = select.map.size();
	for (Map.Entry<Object, String> object : select.map.entrySet()) {
	    if (object.getKey() instanceof CaseWhenHibernateQueryBuilder) {
		appendCaseWhenClause((CaseWhenHibernateQueryBuilder) object.getKey());
	    } else {
		appendAliasOrPath(object.getKey());
	    }
	    if (object.getValue() != null && object.getValue().trim().length() > 0) {
		query.append(" AS ").append(object.getValue());
	    }
	    if (--i > 0) {
		query.append(',');
	    }
	    query.append(' ');
	}
    }

    public void visit(OrderByHibernateQueryBuilder order) {
	query.append(order.separator).append(' ');
	int i = order.aliases.length;
	for (Object alias : order.aliases) {
	    appendAliasOrPath(alias);
	    if (--i > 0) {
		query.append(',');
	    }
	    query.append(' ');
	}
	if (order.priority != null) {
	    query.append(order.priority.priority).append(' ');
	}
    }

    public void visit(ExistsHibernateQueryBuilder exists) {
	query.append(exists.separator).append(' ');
	if (exists.not) {
	    query.append("NOT ");
	}
	query.append("EXISTS ( ");
	visit((SelectHibernateQueryBuilder) exists);
	from = false;
    }

    public void visit(FromHibernateQueryBuilder fromClause) {
	query.append(from ? ',' : fromClause.join).append(' ').append(ProxyUtil.getActualClass(fromClause.object.getClass()).getSimpleName()).append(' ')
			.append(aliases.get(fromClause.object)).append(' ');
	from = true;
    }

    public void visit(JoinQueryBuilder join) {
	query.append(join.join).append(join.fetch ? " FETCH " : ' ').append(getReducedPath(join.object, false)).append(' ').append(aliases.get(join.object)).append(' ');
    }

    public void visit(WhereHibernateQueryBuilder<?> builder) {
	query.append(builder.operator).append(' ');
	if (builder.group) {
	    query.append("( ");
	}
	appendReducedPath(builder.value);
	query.append(' ');
    }

    public void visit(NullConditionHibernateQueryBuilder<?> builder) {
	query.append(builder.operator).append(' ');
	closeGroup(builder);
    }

    public void visit(InConditionHibernateQueryBuilder<?> builder) {
	query.append(builder.operator);
	visitIn(builder.values);
	closeGroup(builder);
    }

    private void visitIn(Object[] values) {
	query.append(" (");
	if (values != null) {
	    int i = 1;
	    for (Object value : values) {
		if (parentEntities.containsKey(value)) {
		    appendAliasOrPath(value);
		} else {
		    query.append('?').append(params++);
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
	    query.append('?').append(params++);
	    addParameter(builder.value);
	}
	query.append(' ');
	closeGroup(builder);
    }

    private void closeGroup(ConditionHibernateQueryBuilder<?> builder) {
	int group = builder.closeGroup + builder.closeExists;
	while (group-- > 0) {
	    query.append(") ");
	}
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


    public void visit(UnfinishedCaseWhenQueryBuilder builder) {
	query.append("THEN ").append(builder.value).append(' ');
    }

    public void visit(CaseWhenHibernateQueryBuilder builder) {
	query.append("ELSE ").append(builder.value).append(" END");
    }

    public void appendCaseWhenClause(CaseWhenHibernateQueryBuilder builder) {
	query.append('(');
	for (HibernateQueryBuilder caseWhenBuilder : builder.chain) {
	    caseWhenBuilder.accept(this);
	}
	query.append(')');
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
		    query.append(getReducedPath(function.getEntity(), true));
		}
	    }
	    query.append(')');
	} else {
	    query.append(getReducedPath(entity, true));
	}
    }

    private String getReducedPath(Object entity, boolean shortest) {
	String result;
	if (shortest && joinedEntities.contains(entity)) {
	    return aliases.get(entity);
	}
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
