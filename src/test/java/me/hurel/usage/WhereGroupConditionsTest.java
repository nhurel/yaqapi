package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.User;
import me.hurel.hqlbuilder.Condition;
import me.hurel.hqlbuilder.QueryBuilder;

import org.junit.Test;

public class WhereGroupConditionsTest {

    @Test
    public void where_with_two_groups() {
	User user = queryOn(new User());
	Condition<String> query = select(user).from(user).innerJoin(user.getAdress()).where(user.getAdress().getNumber()).isGreaterEqualThan("10")
		.orGroup(user.getAdress().getNumber()).isLike("20%").and(user.getAdress().getStreet()).isLike("Carnaby Street%").closeGroup();

	assertThat(query.getQueryString()).isEqualTo(
		"SELECT user FROM User user INNER JOIN user.adress adress WHERE adress.number >= ? OR ( adress.number LIKE ? AND adress.street LIKE ? ) ");
	assertThat(query.getParameters()).containsExactly("10", "20%", "Carnaby Street%");

    }

    @Test
    public void where_with_inner_groups() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).innerJoin(user.getAdress()).where(user.getAdress().getNumber()).isGreaterEqualThan("10").orGroup(user.getAdress().getNumber())
		.isLike("20%").andGroup(user.getAdress().getStreet()).isLike("Carnaby Street%").or(user.getAdress().getStreet()).isLike("Picadilly Circus%").closeGroup()
		.closeGroup();

	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT user FROM User user INNER JOIN user.adress adress WHERE adress.number >= ? OR ( adress.number LIKE ? AND ( adress.street LIKE ? OR adress.street LIKE ? ) ) ");
	assertThat(query.getParameters()).containsExactly("10", "20%", "Carnaby Street%", "Picadilly Circus%");

    }

    @Test
    public void where_with_groups() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).innerJoin(user.getAdress()).whereGroup(user.getAdress().getNumber()).isGreaterEqualThan("10")
		.and(user.getAdress().getCity().getCountry()).isNull().closeGroup().orGroup(user.getAdress().getNumber()).isLike("20%").andGroup(user.getAdress().getStreet())
		.isLike("Carnaby Street%").or(user.getAdress().getStreet()).isLike("Picadilly Circus%").closeGroup().closeGroup();

	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT user FROM User user INNER JOIN user.adress adress WHERE ( adress.number >= ? AND adress.city.country IS NULL ) OR ( adress.number LIKE ? AND ( adress.street LIKE ? OR adress.street LIKE ? ) ) ");
	assertThat(query.getParameters()).containsExactly("10", "20%", "Carnaby Street%", "Picadilly Circus%");

    }

}
