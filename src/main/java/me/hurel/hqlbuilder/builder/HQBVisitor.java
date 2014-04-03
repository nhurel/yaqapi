/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.builder;

public interface HQBVisitor {

    public void visit(SelectHibernateQueryBuilder builder);

    public void visit(FromHibernateQueryBuilder builder);

    public void visit(JoinQueryBuilder builder);

    public void visit(ConditionHibernateQueryBuilder<?> builder);

    public void visit(NullConditionHibernateQueryBuilder<?> builder);

    public void visit(InConditionHibernateQueryBuilder<?> builder);

    public void visit(WhereHibernateQueryBuilder<?> builder);

    public void visit(GroupByHibernateQueryBuilder builder);

    public void visit(ExistsHibernateQueryBuilder builder);

    public void visit(OrderByHibernateQueryBuilder builder);

    public void visit(UnfinishedCaseWhenQueryBuilder builder);

    public void visit(CaseWhenHibernateQueryBuilder builder);
}
