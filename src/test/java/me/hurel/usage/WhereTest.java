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

public class WhereTest {

    @Test
    public void simple_where_is_null_clause() {
	User user = queryOn(User.class);
	String queryString = select(user).from(user).where(user.getFirstName()).isNull().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user WHERE user.firstName IS NULL ");
    }

    @Test
    public void simple_where_is_equal_clause() {
	User user = queryOn(User.class);
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isEqualTo("toto");
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user WHERE user.firstName = ?1 ");
	assertThat(query.getParameters()).containsExactly("toto");
    }

    @Test
    public void simple_where_is_equal_other_property_clause() {
	User user = queryOn(User.class);
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isEqualTo(user.getLastName());
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user WHERE user.firstName = user.lastName ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

    @Test
    public void simple_where_is_equal_other_property_on_not_joined_entity_clause() {
	User user = queryOn(User.class);
	QueryBuilder query = select(user).from(user).where(user.getFirstName()).isEqualTo(user.getAdress().getStreet());
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user WHERE user.firstName = user.adress.street ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

    @Test
    public void simple_where_is_equal_other_property_on_joined_entity_clause() {
	User user = queryOn(User.class);
	QueryBuilder query = select(user).from(user).rightJoin(user.getAdress()).where(user.getFirstName()).isEqualTo(user.getAdress().getStreet());
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user RIGHT JOIN user.adress adress WHERE user.firstName = adress.street ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

    @Test
    public void simple_where_is_equal_other_property_on_other_entity_clause() {
	User user = queryOn(User.class);
	User user2 = andQueryOn(User.class);
	QueryBuilder query = select(user).from(user).andFrom(user2).where(user.getLastName()).isEqualTo(user2.getLastName());
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user , User user2 WHERE user.lastName = user2.lastName ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

    @Test
    public void select_multiple_properties_where_is_equal_other_property_on_other_entity_clause() {
	User user = queryOn(User.class);
	User user2 = andQueryOn(User.class);
	QueryBuilder query = select(user.getFirstName(), user2.getFirstName()).from(user).andFrom(user2).where(user.getLastName()).isEqualTo(user2.getLastName());
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user2.firstName FROM User user , User user2 WHERE user.lastName = user2.lastName ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

    @Test
    public void where_list_property_is_null() {
	User user = queryOn(User.class);
	QueryBuilder query = select(user).from(user).where(user.getChildren()).isNull();
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.children IS NULL ");
    }

    @Test
    public void select_where_joined_list_null() {
        User user = queryOn(User.class);
        String queryString = selectFrom(user).leftJoin(user.getChildren()).where(user.getChildren()).isNull().getQueryString();
        assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN user.children children WHERE children IS NULL ");
    }

    @Test
    public void where_and_where_clause() {
	User user = queryOn(User.class);
	User user2 = andQueryOn(User.class);
	QueryBuilder query = select(user.getFirstName(), user2.getFirstName()).from(user).andFrom(user2).where(user.getLastName()).isEqualTo(user2.getLastName())
		.and(user.getFirstName()).isNull();
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user2.firstName FROM User user , User user2 WHERE user.lastName = user2.lastName AND user.firstName IS NULL ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

    @Test
    public void where_and_where_list_property_is_null() {
	User user = queryOn(User.class);
	User user2 = andQueryOn(User.class);
	QueryBuilder query = select(user.getFirstName(), user2.getFirstName()).from(user).andFrom(user2).where(user.getLastName()).isEqualTo(user2.getLastName())
		.and(user.getChildren()).isNull();
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user2.firstName FROM User user , User user2 WHERE user.lastName = user2.lastName AND user.children IS NULL ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

    @Test
    public void where_or_where_clause() {
	User user = queryOn(User.class);
	User user2 = andQueryOn(User.class);
	QueryBuilder query = select(user.getFirstName(), user2.getFirstName()).from(user).andFrom(user2).where(user.getLastName()).isEqualTo(user2.getLastName())
		.or(user.getFirstName()).isNull();
	String queryString = query.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user2.firstName FROM User user , User user2 WHERE user.lastName = user2.lastName OR user.firstName IS NULL ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

    @Test
    public void select_where_joined_entity_property_is_null() {
        User user = queryOn(User.class);
        QueryBuilder query = select(user).from(user).rightJoin(user.getAdress()).where(user.getAdress()).isNull();
        String queryString = query.getQueryString();
        assertThat(queryString).isEqualTo("SELECT user FROM User user RIGHT JOIN user.adress adress WHERE adress IS NULL ");
        assertThat(query.getParameters()).isNullOrEmpty();
    }

}
