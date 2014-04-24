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
import me.hurel.entity.City;
import me.hurel.entity.User;
import me.hurel.hqlbuilder.OrderByClause;

import org.junit.Test;

public class OrderByTest {

    @Test
    public void where_order_by() {
	User user = queryOn(User.class);
	OrderByClause query = select(user).from(user).where(user.getAge()).isLessEqualThan(5).orderBy(user.getAge());
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.age <= ?1 ORDER BY user.age ");
    }

    @Test
    public void from_order_by() {
	User user = queryOn(User.class);
	OrderByClause query = select(user).from(user).orderBy(user.getAge());
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user ORDER BY user.age ");
    }

    @Test
    public void join_order_by() {
	User user = queryOn(User.class);
	OrderByClause query = select(user).from(user).innerJoin(user.getAdress()).with(user.getAdress().getNumber()).isEqualTo("1").orderBy(user.getAge());
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress WITH adress.number = ?1 ORDER BY user.age ");
    }

    @Test
    public void exists_order_by() {
	User user = queryOn(User.class);
	City city = andQueryOn(City.class);
	OrderByClause query = select(user).from(user).innerJoin(user.getAdress()).whereExists(city).from(city).where(city.getName()).isLike("Rou%").and(city.getId())
		.isEqualTo(user.getAdress().getCity().getId()).closeExists().orderBy(user.getFirstName());
	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT user FROM User user INNER JOIN user.adress adress WHERE EXISTS ( SELECT city FROM City city WHERE city.name LIKE ?1 AND city.id = adress.city.id ) ORDER BY user.firstName ");
    }

    @Test
    public void order_by_and_by() {
	User user = queryOn(User.class);
	OrderByClause query = select(user).from(user).orderBy(user.getLastName()).andBy(user.getFirstName());
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user ORDER BY user.lastName , user.firstName ");
    }

    @Test
    public void order_by_multiple_fields() {
	User user = queryOn(User.class);
	OrderByClause query = select(user).from(user).orderBy(user.getLastName(), user.getFirstName());
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user ORDER BY user.lastName, user.firstName ");
    }

    @Test
    public void order_by_desc() {
	User user = queryOn(User.class);
	OrderByClause query = select(user).from(user).orderBy(user.getLastName()).desc();
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user ORDER BY user.lastName DESC ");
    }

    @Test
    public void order_by_desc_and_by_asc() {
	User user = queryOn(User.class);
	OrderByClause query = select(user).from(user).orderBy(user.getLastName()).desc().andBy(user.getFirstName()).asc();
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user ORDER BY user.lastName DESC , user.firstName ASC ");
    }

    @Test
    public void group_by_order_by() {
	User user = queryOn(User.class);
	OrderByClause query = select(user).from(user).groupBy(user.getLastName()).orderBy(user.getLastName()).desc();
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user GROUP BY user.lastName ORDER BY user.lastName DESC ");
    }

    @Test
    public void group_by_having_order_by() {
	User user = queryOn(User.class);
	OrderByClause query = select(user.getLastName(), count("*")).from(user).groupBy(user.getLastName()).having(count("*")).isGreaterEqualThan(2L).orderBy(user.getLastName())
		.desc();
	assertThat(query.getQueryString()).isEqualTo("SELECT user.lastName, count(*) FROM User user GROUP BY user.lastName HAVING count(*) >= ?1 ORDER BY user.lastName DESC ");
	assertThat(query.getParameters()).containsExactly(2L);
    }

}
