package me.hurel.hqlbuilder.builder;

public class HQBQueryStringVisitor implements HQBVisitor {

    private final StringBuilder query = new StringBuilder();

    private boolean from = false;

    public void visit(SelectHibernateQueryBuilder select) {
	query.append("SELECT ");
	int i = select.aliases.length;
	for (String alias : select.aliases) {
	    query.append(alias);
	    if (--i > 0) {
		query.append(',');
	    }
	    query.append(' ');
	}
    }

    public void visit(FromHibernateQueryBuilder fromClause) {
	query.append(from ? ',' : fromClause.join).append(' ').append(fromClause.object).append(' ').append(fromClause.alias).append(' ');
	from = true;
    }

    public void visit(AbstractJoinQueryBuilder join) {
	query.append(join.join).append(join.fetch ? " FETCH " : ' ').append(join.object).append(' ').append(join.alias).append(' ');
    }

    public String getQuery() {
	return query.toString();
    }
}
