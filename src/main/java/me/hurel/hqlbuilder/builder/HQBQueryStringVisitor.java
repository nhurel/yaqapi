package me.hurel.hqlbuilder.builder;

import static me.hurel.hqlbuilder.builder.UnfinishedHibernateQueryBuilder.*;

import java.util.Map;

public class HQBQueryStringVisitor implements HQBVisitor {

    private final StringBuilder query = new StringBuilder();

    private boolean from = false;

    private final Map<Object, String> aliases;
    private final Map<Object, String> paths;

    public HQBQueryStringVisitor(Map<Object, String> aliases, Map<Object, String> paths) {
	this.aliases = aliases;
	this.paths = paths;
    }

    public void visit(SelectHibernateQueryBuilder select) {
	query.append("SELECT ");
	int i = select.aliases.length;
	for (Object alias : select.aliases) {
	    query.append(getAliasOrPath(alias));
	    if (--i > 0) {
		query.append(',');
	    }
	    query.append(' ');
	}
    }

    public void visit(FromHibernateQueryBuilder fromClause) {
	query.append(from ? ',' : fromClause.join).append(' ').append(getActualClass(fromClause.object.getClass()).getSimpleName()).append(' ')
		.append(aliases.get(fromClause.object)).append(' ');
	from = true;
    }

    public void visit(AbstractJoinQueryBuilder join) {
	query.append(join.join).append(join.fetch ? " FETCH " : ' ').append(paths.get(join.object)).append(' ').append(aliases.get(join.object)).append(' ');
    }

    private String getAliasOrPath(Object entity) {
	String result = aliases.get(entity);
	if (result == null) {
	    result = paths.get(entity);
	}
	return result;
    }

    public String getQuery() {
	return query.toString();
    }
}
