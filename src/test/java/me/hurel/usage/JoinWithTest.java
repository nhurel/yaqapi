package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.User;
import me.hurel.hqlbuilder.QueryBuilder;

import org.junit.Test;

public class JoinWithTest {

    @Test
    public void join_with() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).innerJoin(user.getAdress()).with(user.getAdress().getStreet()).isLike("%Street").where(user.getAge()).isLessThan(30);
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress WITH adress.street LIKE ?1 WHERE user.age < ?2 ");
	assertThat(query.getParameters()).containsExactly("%Street", 30);
    }

    @Test
    public void join_with_group_or_group() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).innerJoin(user.getAdress()).withGroup(user.getAdress().getStreet()).isLike("%Street").orGroup(user.getFirstName()).isNull()
		.and(user.getLastName()).isNull().closeGroup().or(user.getAdress().getCity()).isNull().closeGroup().where(user.getAge()).isLessThan(30);
	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT user FROM User user INNER JOIN user.adress adress WITH ( adress.street LIKE ?1 OR ( user.firstName IS NULL AND user.lastName IS NULL ) OR adress.city IS NULL ) WHERE user.age < ?2 ");
	assertThat(query.getParameters()).containsExactly("%Street", 30);
    }

    @Test
    public void join_with_or_group() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).innerJoin(user.getAdress()).with(user.getAdress().getStreet()).isLike("%Street").orGroup(user.getFirstName()).isNull()
		.and(user.getLastName()).isNull().closeGroup().where(user.getAge()).isLessThan(30);
	assertThat(query.getQueryString()).isEqualTo(
		"SELECT user FROM User user INNER JOIN user.adress adress WITH adress.street LIKE ?1 OR ( user.firstName IS NULL AND user.lastName IS NULL ) WHERE user.age < ?2 ");
	assertThat(query.getParameters()).containsExactly("%Street", 30);
    }
}
