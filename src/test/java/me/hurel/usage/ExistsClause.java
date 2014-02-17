package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.City;
import me.hurel.entity.User;
import me.hurel.hqlbuilder.QueryBuilder;

import org.junit.Test;

public class ExistsClause {

    @Test
    public void where_exists() {
	City city = queryOn(new City());
	User user = andQueryOn(new User());
	QueryBuilder query = select(city).from(city).whereExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge()).isLessThan(5)
		.and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString()).isEqualTo(
		"SELECT city FROM City city WHERE EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }

    @Test
    public void where_not_exists() {
	City city = queryOn(new City());
	User user = andQueryOn(new User());
	QueryBuilder query = select(city).from(city).whereNotExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge()).isLessThan(5)
		.and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString()).isEqualTo(
		"SELECT city FROM City city WHERE NOT EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }

    @Test
    public void where_exists_after_join() {
	City city = queryOn(new City());
	User user = andQueryOn(new User());
	QueryBuilder query = select(city).from(city).innerJoin(city.getCountry()).whereExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge())
		.isLessThan(5).and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT city FROM City city INNER JOIN city.country country WHERE EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }

    @Test
    public void where_and_exists() {
	City city = queryOn(new City());
	User user = andQueryOn(new User());
	QueryBuilder query = select(city).from(city).where(city.getCountry()).isNotNull().andExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge())
		.isLessThan(5).and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT city FROM City city WHERE city.country IS NOT NULL AND EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }

    @Test
    public void where_or_not_exists() {
	City city = queryOn(new City());
	User user = andQueryOn(new User());
	QueryBuilder query = select(city).from(city).where(city.getCountry()).isNotNull().orNotExists(user.getId()).from(user).innerJoin(user.getAdress()).where(user.getAge())
		.isLessThan(5).and(user.getAdress().getCity().getId()).isEqualTo(city.getId()).closeExists();

	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT city FROM City city WHERE city.country IS NOT NULL OR NOT EXISTS ( SELECT user.id FROM User user INNER JOIN user.adress adress WHERE user.age < ?1 AND adress.city.id = city.id ) ");

	assertThat(query.getParameters()).containsExactly(5);

    }
}
