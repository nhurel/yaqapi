/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.usage;

import me.hurel.entity.Car;
import me.hurel.entity.User;
import org.junit.Test;

import static me.hurel.hqlbuilder.builder.Yaqapi.queryOn;
import static me.hurel.hqlbuilder.builder.Yaqapi.select;
import static org.fest.assertions.Assertions.assertThat;

public class AliasTest {

    @Test
    public void select_entity_as() {
	User user = queryOn(User.class);
	String query = select(user).as("u").from(user).getQueryString();
	assertThat(query).isEqualTo("SELECT user AS u FROM User user ");
    }

    @Test
    public void select_multi_entity_as() {
	User user = queryOn(User.class);
	String query = select(user).as("u").andSelect(user.getAdress().getCity()).as("c").from(user).getQueryString();
	assertThat(query).isEqualTo("SELECT user AS u, user.adress.city AS c FROM User user ");
    }

    @Test
    public void select_multi_entity_as_from_join() {
	User user = queryOn(User.class);
	String query = select(user).as("u").andSelect(user.getAdress().getCity()).as("c").from(user).innerJoin(user.getAdress()).innerJoin(user.getAdress().getCity()).getQueryString();
	assertThat(query).isEqualTo("SELECT user AS u, city AS c FROM User user INNER JOIN user.adress adress INNER JOIN adress.city city ");
    }

    @Test
    public void select_multi_properties_as() {
	Car car = queryOn(Car.class);
	String query = select(car.getReleaseDate()).as("rDate").andSelect(car.getSellDate()).as("sDate").andSelect(car.getModel()).as("modelName").from(car).getQueryString();
	assertThat(query).isEqualTo("SELECT car.releaseDate AS rDate, car.sellDate AS sDate, car.model AS modelName FROM Car car ");
    }

    @Test
    public void select_second_property_as() {
	Car car = queryOn(Car.class);
	String query = select(car.getReleaseDate()).andSelect(car.getSellDate()).as("sDate").from(car).getQueryString();
	assertThat(query).isEqualTo("SELECT car.releaseDate, car.sellDate AS sDate FROM Car car ");
    }

}
