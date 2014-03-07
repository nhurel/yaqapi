/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.User;
import me.hurel.hqlbuilder.QueryBuilder;

import org.junit.Test;

public class GroupByTest {

    @Test
    public void group_by_age() {
	User user = queryOn(new User());
	String queryString = select(user).from(user).groupBy(user.getAge()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user GROUP BY user.age ");
    }

    @Test
    public void group_by_firstName_lastName() {
	User user = queryOn(new User());
	String queryString = select(count("*")).from(user).groupBy(user.getFirstName(), user.getLastName()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT count(*) FROM User user GROUP BY user.firstName, user.lastName ");
    }

    @Test
    public void group_by_firstName_having_age() {
	User user = queryOn(new User());
	QueryBuilder query = select(user.getFirstName()).from(user).groupBy(user.getFirstName()).having(user.getAge()).isGreaterThan(5);
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName FROM User user GROUP BY user.firstName HAVING user.age > ?1 ");
	assertThat(query.getParameters()).containsExactly(5);
    }

    @Test
    public void group_by_firstName_having_children() {
	User user = queryOn(new User());
	QueryBuilder query = select(user.getFirstName()).from(user).groupBy(user.getFirstName()).having(count(user.getChildren())).isGreaterThan(1L);
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName FROM User user GROUP BY user.firstName HAVING count(user.children) > ?1 ");
	assertThat(query.getParameters()).containsExactly(1L);
    }

    @Test
    public void group_by_firstName_having_age_and_firstName_not_null() {
	User user = queryOn(new User());
	QueryBuilder query = select(user.getFirstName()).from(user).groupBy(user.getFirstName()).having(user.getAge()).isGreaterThan(5).and(user.getFirstName()).isNotNull();
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName FROM User user GROUP BY user.firstName HAVING user.age > ?1 AND user.firstName IS NOT NULL ");
	assertThat(query.getParameters()).containsExactly(5);
    }

    @Test
    public void where_group_by_age() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).innerJoinFetch(user.getAdress()).where(user.getLastName()).isNotNull().groupBy(user.getFirstName())
		.having(count(user.getAge())).isGreaterThan(5L);
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo(
		"SELECT user FROM User user INNER JOIN FETCH user.adress adress WHERE user.lastName IS NOT NULL GROUP BY user.firstName HAVING count(user.age) > ?1 ");
	assertThat(query.getParameters()).containsExactly(5L);
    }
}
