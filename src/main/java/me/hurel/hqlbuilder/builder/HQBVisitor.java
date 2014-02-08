package me.hurel.hqlbuilder.builder;

public interface HQBVisitor {

    public void visit(SelectHibernateQueryBuilder builder);

    public void visit(FromHibernateQueryBuilder builder);

    public void visit(AbstractJoinQueryBuilder builder);

    public void visit(ConditionHibernateQueryBuilder<?> builder);

    public void visit(NullConditionHibernateQueryBuilder<?> builder);

    public void visit(WhereHibernateQueryBuilder<?> builder);

}
