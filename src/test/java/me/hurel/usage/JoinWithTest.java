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

public class JoinWithTest {

    @Test
    public void join_with() {
	User user = queryOn(User.class);
	QueryBuilder query = select(user).from(user).innerJoin(user.getAdress()).with(user.getAdress().getStreet()).isLike("%Street").where(user.getAge()).isLessThan(30);
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress WITH adress.street LIKE ?1 WHERE user.age < ?2 ");
	assertThat(query.getParameters()).containsExactly("%Street", 30);
    }

    @Test
    public void join_with_group_or_group() {
	User user = queryOn(User.class);
	QueryBuilder query = select(user).from(user).innerJoin(user.getAdress()).withGroup(user.getAdress().getStreet()).isLike("%Street").orGroup(user.getFirstName()).isNull()
		.and(user.getLastName()).isNull().closeGroup().or(user.getAdress().getCity()).isNull().closeGroup().where(user.getAge()).isLessThan(30);
	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT user FROM User user INNER JOIN user.adress adress WITH ( adress.street LIKE ?1 OR ( user.firstName IS NULL AND user.lastName IS NULL ) OR adress.city IS NULL ) WHERE user.age < ?2 ");
	assertThat(query.getParameters()).containsExactly("%Street", 30);
    }

    @Test
    public void join_with_or_group() {
	User user = queryOn(User.class);
	QueryBuilder query = select(user).from(user).innerJoin(user.getAdress()).with(user.getAdress().getStreet()).isLike("%Street").orGroup(user.getFirstName()).isNull()
		.and(user.getLastName()).isNull().closeGroup().where(user.getAge()).isLessThan(30);
	assertThat(query.getQueryString()).isEqualTo(
		"SELECT user FROM User user INNER JOIN user.adress adress WITH adress.street LIKE ?1 OR ( user.firstName IS NULL AND user.lastName IS NULL ) WHERE user.age < ?2 ");
	assertThat(query.getParameters()).containsExactly("%Street", 30);
    }
}
