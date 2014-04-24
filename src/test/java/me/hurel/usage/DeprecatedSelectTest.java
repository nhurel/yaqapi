/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.usage;

import me.hurel.entity.Adress;
import me.hurel.entity.User;
import org.junit.Test;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static me.hurel.hqlbuilder.builder.Yaqapi.andQueryOn;
import static me.hurel.hqlbuilder.builder.Yaqapi.select;
import static org.fest.assertions.Assertions.assertThat;


@SuppressWarnings("deprecation")
public class DeprecatedSelectTest {

    @Test
    public void select_on_one_entity() {
	User user = queryOn(new User());
	String queryString = selectFrom(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user ");
    }


    @Test
    public void select_property_from_external_entity() {
	User user = queryOn(new User());
	Adress adress = andQueryOn(new Adress());
	String queryString = select(user.getFirstName(), user.getLastName(), adress.getNumber()).from(user).andFrom(adress).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user.lastName, adress.number FROM User user , Adress adress ");
    }

    @Test
    public void select_property_from_two_entities_of_same_class() {
	User user1 = queryOn(new User());
	User user2 = andQueryOn(new User());
	String queryString = select(user1.getFirstName(), user1.getLastName(), user2.getFirstName()).from(user1).andFrom(user2).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user.lastName, user2.firstName FROM User user , User user2 ");
    }


}
