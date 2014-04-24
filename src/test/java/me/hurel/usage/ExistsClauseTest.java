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
import me.hurel.hqlbuilder.QueryBuilder;

import org.junit.Test;

public class ExistsClauseTest {

    @Test
    public void where_exists() {
	City city = queryOn(City.class);
	User user = andQueryOn(User.class);
	QueryBuilder query = select(city).from(city).whereExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge()).isLessThan(5)
		.and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString()).isEqualTo(
		"SELECT city FROM City city WHERE EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }

    @Test
    public void where_not_exists() {
	City city = queryOn(City.class);
	User user = andQueryOn(User.class);
	QueryBuilder query = select(city).from(city).whereNotExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge()).isLessThan(5)
		.and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString()).isEqualTo(
		"SELECT city FROM City city WHERE NOT EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }

    @Test
    public void where_exists_after_join() {
	City city = queryOn(City.class);
	User user = andQueryOn(User.class);
	QueryBuilder query = select(city).from(city).innerJoin(city.getCountry()).whereExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge())
		.isLessThan(5).and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT city FROM City city INNER JOIN city.country country WHERE EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }

    @Test
    public void where_and_exists() {
	City city = queryOn(City.class);
	User user = andQueryOn(User.class);
	QueryBuilder query = select(city).from(city).where(city.getCountry()).isNotNull().andExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge())
		.isLessThan(5).and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT city FROM City city WHERE city.country IS NOT NULL AND EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }

    @Test
    public void where_or_not_exists() {
	City city = queryOn(City.class);
	User user = andQueryOn(User.class);
	QueryBuilder query = select(city).from(city).where(city.getCountry()).isNotNull().orNotExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge())
		.isLessThan(5).and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT city FROM City city WHERE city.country IS NOT NULL OR NOT EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }
}
