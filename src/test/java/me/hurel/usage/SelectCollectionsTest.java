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

public class SelectCollectionsTest {

    @Test
    public void select_list_property() {
	User user = queryOn(new User());
	String queryString = select(user.getChildren()).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.children FROM User user ");
    }

    @Test
    public void join_on_list_property() {
	User user = queryOn(new User());

	String queryString = select(user).from(user).leftJoinFetch(user.getChildren()).leftJoin($(user.getChildren()).getAdress()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN FETCH user.children children LEFT JOIN children.adress adress ");
    }

    @Test
    public void select_property_from_joined_list_property() {
	User user = queryOn(new User());

	String queryString = select($(user.getChildren()).getFirstName()).from(user).innerJoin(user.getChildren()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT children.firstName FROM User user INNER JOIN user.children children ");
    }

    @Test
    public void select_property_from_deep_list_property() {
	User user = queryOn(new User());

	String queryString = select($(user.getChildren()).getFirstName()).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.children.firstName FROM User user ");
    }

    @Test
    public void select_second_level_list_property_from_deep_list_property() {
	User user = queryOn(new User());

	String queryString = select($(user.getChildren()).getChildren()).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.children.children FROM User user ");
    }

    @Test
    public void select_property_from_deep_second_level_list_property() {
	User user = queryOn(new User());

	String queryString = select($($(user.getChildren()).getChildren()).getFirstName()).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.children.children.firstName FROM User user ");
    }

    @Test
    public void select_property_from_second_level_join_list() {
	User user = queryOn(new User());

	String queryString = select($($(user.getChildren()).getChildren()).getFirstName()).from(user).innerJoin(user.getChildren()).innerJoin($(user.getChildren()).getChildren())
		.getQueryString();
	assertThat(queryString).isEqualTo("SELECT children2.firstName FROM User user INNER JOIN user.children children INNER JOIN children.children children2 ");
    }

    @Test
    public void select_property_from_aliased_second_level_join_list() {
	User user = queryOn(new User());
	User children2 = $($(user.getChildren()).getChildren());

	String queryString = select(children2.getFirstName()).from(user).innerJoin(user.getChildren()).innerJoin(children2).getQueryString();
	assertThat(queryString).isEqualTo("SELECT children2.firstName FROM User user INNER JOIN user.children children INNER JOIN children.children children2 ");
    }

}
