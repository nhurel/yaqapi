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

import org.junit.Test;

public class SelectFunctionsTest {

    @Test
    public void select_max() {
	User user = queryOn(User.class);
	String queryString = select(max(user.getAge())).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT max(user.age) FROM User user ");
    }

    @Test
    public void select_min() {
	User user = queryOn(User.class);
	String queryString = select(min(user.getAge()), user).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT min(user.age), user FROM User user ");
    }

    @Test
    public void select_average() {
	User user = queryOn(User.class);
	String queryString = select(average(user.getAge())).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT avg(user.age) FROM User user ");
    }

    @Test
    public void select_count() {
	User user = queryOn(User.class);
	String queryString = select(count(user.getAge())).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT count(user.age) FROM User user ");
    }

    @Test
    public void select_count_distinct() {
	User user = queryOn(User.class);
	String queryString = select(count(distinct(user.getAge()))).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT count(distinct(user.age)) FROM User user ");
    }

    @Test
    public void select_sum() {
	User user = queryOn(User.class);
	String queryString = select(sum(user.getAge())).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT sum(user.age) FROM User user ");
    }

}
