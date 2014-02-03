package me.hurel.hqlbuilder.builder;

public class FromHibernateQueryBuilder extends AbstractJoinQueryBuilder {

    FromHibernateQueryBuilder(HibernateQueryBuilder root, Class<?> entity, String alias) {
	super(root, JOIN.FROM);
	this.object = entity.getSimpleName();
	this.alias = alias;
    }

    @Override
    public void accept(HQBVisitor visitor) {
	visitor.visit(this);
    }

}
