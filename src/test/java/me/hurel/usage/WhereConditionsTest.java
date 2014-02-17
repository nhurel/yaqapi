package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.User;
import me.hurel.hqlbuilder.QueryBuilder;

import org.junit.Test;

public class WhereConditionsTest {

    @Test
    public void where_is_null() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isNull();
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.firstName IS NULL ");
	assertThat(query.getParameters()).isNull();
    }

    @Test
    public void where_is_not_null() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isNotNull();
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.firstName IS NOT NULL ");
	assertThat(query.getParameters()).isNull();
    }

    @Test
    public void where_is_equal() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isEqualTo("firstName");
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.firstName = ?1 ");
	assertThat(query.getParameters()).containsExactly("firstName");
    }

    @Test
    public void where_is_not_equal() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isNotEqualTo("firstName");
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.firstName <> ?1 ");
	assertThat(query.getParameters()).containsExactly("firstName");
    }

    @Test
    public void where_is_like() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isLike("firstName%");
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.firstName LIKE ?1 ");
	assertThat(query.getParameters()).containsExactly("firstName%");
    }

    @Test
    public void where_is_not_like() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isNotLike("firstName%");
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.firstName NOT LIKE ?1 ");
	assertThat(query.getParameters()).containsExactly("firstName%");
    }

    @Test
    public void where_is_greater() {
	User user = queryOn(new User());
	QueryBuilder query = select(user.getAge()).from(user).where(user.getAge()).isGreaterThan(1);
	assertThat(query.getQueryString()).isEqualTo("SELECT user.age FROM User user WHERE user.age > ?1 ");
	assertThat(query.getParameters()).containsExactly(1);
    }

    @Test
    public void where_is_greaterEqual() {
	User user = queryOn(new User());
	QueryBuilder query = select(user.getAge()).from(user).where(user.getAge()).isGreaterEqualThan(1);
	assertThat(query.getQueryString()).isEqualTo("SELECT user.age FROM User user WHERE user.age >= ?1 ");
	assertThat(query.getParameters()).containsExactly(1);
    }

    @Test
    public void where_is_less() {
	User user = queryOn(new User());
	QueryBuilder query = select(user.getAge()).from(user).where(user.getAge()).isLessThan(1);
	assertThat(query.getQueryString()).isEqualTo("SELECT user.age FROM User user WHERE user.age < ?1 ");
	assertThat(query.getParameters()).containsExactly(1);
    }

    @Test
    public void where_is_lessEqual() {
	User user = queryOn(new User());
	QueryBuilder query = select(user.getAge()).from(user).where(user.getAge()).isLessEqualThan(1);
	assertThat(query.getQueryString()).isEqualTo("SELECT user.age FROM User user WHERE user.age <= ?1 ");
	assertThat(query.getParameters()).containsExactly(1);
    }

    @Test
    public void where_is_in_values() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).where(user.getAge()).isIn(1, 2, 3);
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.age IN (?1, ?2, ?3) ");
	assertThat(query.getParameters()).containsExactly(1, 2, 3);
    }

    @Test
    public void where_is_not_in_aliases() {
	User user = queryOn(new User());
	QueryBuilder query = select(user).from(user).where(user.getLastName()).isNotIn($(user.getChildren()).getLastName());
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.lastName NOT IN (user.children.lastName) ");
	assertThat(query.getParameters()).isNull();
    }

}
