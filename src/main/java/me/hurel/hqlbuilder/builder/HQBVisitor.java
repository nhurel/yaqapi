package me.hurel.hqlbuilder.builder;

public interface HQBVisitor {

    public void visit(SelectHibernateQueryBuilder builder);

    public void visit(FromHibernateQueryBuilder builder);

    public void visit(JoinQueryBuilder builder);

    public void visit(ConditionHibernateQueryBuilder<?> builder);

    public void visit(NullConditionHibernateQueryBuilder<?> builder);

    public void visit(InConditionHibernateQueryBuilder<?> builder);

    public void visit(WhereNullConditionHibernateQueryBuilder<?> builder);

    public void visit(WhereInConditionHibernateQueryBuilder<?> builder);

    public void visit(HavingHibernateQueryBuilder<?> builder);

    public void visit(GroupByHibernateQueryBuilder groupByHibernateQueryBuilder);

}
