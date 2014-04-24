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

public class SelectDistinctTest {

    @Test
    public void select_distinct_entity() {
	User user = queryOn(User.class);
	String queryString = select(user).distinct().from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT DISTINCT user FROM User user ");
    }

    @Test
    public void select_distinct_entity_directly() {
	User user = queryOn(User.class);
	String queryString = selectDistinct(user).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT DISTINCT user FROM User user ");
    }

    @Test
    public void select_distinct_properties() {
	User user = queryOn(User.class);
	String queryString = select(user.getFirstName(), user.getLastName()).distinct().from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT DISTINCT user.firstName, user.lastName FROM User user ");
    }

    @Test
    public void select_distinct_properties_directly() {
	User user = queryOn(User.class);
	String queryString = selectDistinct(user.getFirstName(), user.getLastName()).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT DISTINCT user.firstName, user.lastName FROM User user ");
    }

    @Test
    public void select_distinct_from_entity() {
	User user = queryOn(User.class);
	String queryString = selectDistinctFrom(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT DISTINCT user FROM User user ");
    }

}
