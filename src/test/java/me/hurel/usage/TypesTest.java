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
import me.hurel.entity.Car;

import org.junit.Test;

public class TypesTest {

    @Test
    public void select_date() {
	Car car = queryOn(new Car());
	String queryString = select(car.getReleaseDate()).from(car).getQueryString();
	assertThat(queryString).isEqualTo("SELECT car.releaseDate FROM Car car ");
    }

    @Test
    public void select_bigDecimal() {
	Car car = queryOn(new Car());
	String queryString = select(car.getPrice()).from(car).getQueryString();
	assertThat(queryString).isEqualTo("SELECT car.price FROM Car car ");
    }

    @Test
    public void select_map() {
	Car car = queryOn(new Car());
	String queryString = select(car.getSomeMap()).from(car).getQueryString();
	assertThat(queryString).isEqualTo("SELECT car.someMap FROM Car car ");
    }

    @Test
    public void select_list() {
	Car car = queryOn(new Car());
	String queryString = select(car.getSomeList()).from(car).getQueryString();
	assertThat(queryString).isEqualTo("SELECT car.someList FROM Car car ");
    }

    @Test
    public void select_list_where_list() {
	Car car = queryOn(new Car());
	String queryString = select(car.getSomeList()).from(car).where($(car.getSomeList())).isEqualTo("Ford").getQueryString();
	assertThat(queryString).isEqualTo("SELECT car.someList FROM Car car WHERE car.someList = ?1 ");
    }

}
