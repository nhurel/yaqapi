/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.usage;

import me.hurel.entity.User;
import me.hurel.hqlbuilder.CaseWhenClause;
import me.hurel.hqlbuilder.QueryBuilder;
import org.junit.Test;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.assertThat;

public class CaseWhenTest {

    @Test
    public void case_when() {
	User user = queryOn(new User());
	String queryString = select(caseWhen(user.getChildren()).isNull().then(false).whenElse(true)).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT (CASE WHEN user.children IS NULL THEN false ELSE true END) FROM User user ");
    }

    @Test
    public void select_case_when_where_case_when() {
	User user = queryOn(new User());
	QueryBuilder query = select(caseWhen(user.getChildren()).isNull().then(false).whenElse(true))
			.from(user)
			.where(caseWhen(user.getAge()).isLessEqualThan(2).then("baby")
					.when(user.getAge()).isGreaterThan(2).and(user.getAge()).isLessThan(18).then("child")
					.whenElse("adult"))
			.isEqualTo("child");
	assertThat(query.getQueryString())
			.isEqualTo("SELECT (CASE WHEN user.children IS NULL THEN false ELSE true END) FROM User user WHERE (CASE WHEN user.age <= ?1 THEN 'baby' WHEN user.age > ?2 AND user.age < ?3 THEN 'child' ELSE 'adult' END) = ?4 ");
	assertThat(query.getParameters()).containsExactly(2, 2, 18, "child");
    }

    @Test
    public void select_case_when_where_case_when_variable() {
	User user = queryOn(new User());
	CaseWhenClause<String> caseWhenClause = caseWhen(user.getAge()).isLessEqualThan(2).then("baby")
			.when(user.getAge()).isGreaterThan(2).and(user.getAge()).isLessThan(18).then("child")
			.whenElse("adult");
	QueryBuilder query = select(caseWhenClause)
			.from(user)
			.where(caseWhenClause)
			.isEqualTo("child");
	assertThat(query.getQueryString())
			.isEqualTo("SELECT (CASE WHEN user.age <= ?1 THEN 'baby' WHEN user.age > ?2 AND user.age < ?3 THEN 'child' ELSE 'adult' END) FROM User user WHERE (CASE WHEN user.age <= ?4 THEN 'baby' WHEN user.age > ?5 AND user.age < ?6 THEN 'child' ELSE 'adult' END) = ?7 ");
	assertThat(query.getParameters()).containsExactly(2, 2, 18, 2, 2, 18, "child");
    }

    @Test
    public void select_case_when_group_by_case_when() {
	User user = queryOn(new User());
	CaseWhenClause<String> caseWhenClause = caseWhen(user.getAge()).isLessEqualThan(2).then("baby")
			.when(user.getAge()).isGreaterThan(2).and(user.getAge()).isLessThan(18).then("child")
			.whenElse("adult");
	QueryBuilder query = select(caseWhenClause).as("generation").andSelect(count("*")).as("nb")
			.from(user).groupBy(caseWhenClause);
	assertThat(query.getQueryString())
			.isEqualTo("SELECT (CASE WHEN user.age <= ?1 THEN 'baby' WHEN user.age > ?2 AND user.age < ?3 THEN 'child' ELSE 'adult' END) AS generation, count(*) AS nb FROM User user GROUP BY (CASE WHEN user.age <= ?4 THEN 'baby' WHEN user.age > ?5 AND user.age < ?6 THEN 'child' ELSE 'adult' END) ");
	assertThat(query.getParameters()).containsExactly(2, 2, 18, 2, 2, 18);

    }

}
