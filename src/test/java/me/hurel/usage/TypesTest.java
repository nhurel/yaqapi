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

import java.util.Date;

import me.hurel.entity.Car;
import me.hurel.entity.TypeEntity;
import me.hurel.entity.User;
import me.hurel.hqlbuilder.Condition;
import me.hurel.hqlbuilder.OrderByClause;
import me.hurel.hqlbuilder.QueryBuilder;

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
	TypeEntity te = queryOn(new TypeEntity());
	String queryString = select(te.getSomeMap()).from(te).getQueryString();
	assertThat(queryString).isEqualTo("SELECT typeEntity.someMap FROM TypeEntity typeEntity ");
    }

    @Test
    public void select_list() {
	TypeEntity typeEntity = queryOn(new TypeEntity());
	String queryString = select(typeEntity.getSomeList()).from(typeEntity).getQueryString();
	assertThat(queryString).isEqualTo("SELECT typeEntity.someList FROM TypeEntity typeEntity ");
    }

    @Test
    public void select_list_where_list() {
	TypeEntity typeEntity = queryOn(new TypeEntity());
	String queryString = select(typeEntity.getSomeList()).from(typeEntity).where($(typeEntity.getSomeList())).isEqualTo("Ford").getQueryString();
	assertThat(queryString).isEqualTo("SELECT typeEntity.someList FROM TypeEntity typeEntity WHERE typeEntity.someList = ?1 ");
    }

    @Test
    public void select_boolean_where_boolean() {
	Car car = queryOn(new Car());
	String queryString = select(car.isHybrid()).from(car).where(car.isHybrid()).isEqualTo(true).getQueryString();
	assertThat(queryString).isEqualTo("SELECT car.hybrid FROM Car car WHERE car.hybrid = ?1 ");
    }

    @Test
    public void select_boolean_where_booleanProperty() {
	Car car = queryOn(new Car());
	String queryString = select(car.isHybrid()).from(car).where(car.isHybrid()).isEqualTo(car.isHybrid()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT car.hybrid FROM Car car WHERE car.hybrid = car.hybrid ");
    }

    @Test
    public void select_boolean_with_boolean_groupby_boolean_having_boolean() {
	Car car = queryOn(new Car());
	Condition<?> query = select(car.isHybrid()).from(car).innerJoin(car.getOwner()).with(car.isHybrid()).isEqualTo(false).and(car.getOwner().isMale()).isEqualTo(false)
		.where(car.isHybrid()).isEqualTo(false).groupBy(car.isHybrid(), car.getOwner().isMale()).having(car.getOwner().isMale()).isEqualTo(false);
	assertThat(query.getQueryString())
		.isEqualTo(
			"SELECT car.hybrid FROM Car car INNER JOIN car.owner user WITH car.hybrid = ?1 AND user.male = ?2 WHERE car.hybrid = ?3 GROUP BY car.hybrid, user.male HAVING user.male = ?4 ");
	assertThat(query.getParameters()).containsExactly(false, false, false, false);
    }

    @Test
    public void select_entity_where_boolean_orderby_boolean() {
	Car car = queryOn(new Car());
	OrderByClause query = select(car).from(car).where(car.isHybrid()).isEqualTo(false).orderBy(car.getModel(), car.isHybrid());
	assertThat(query.getQueryString()).isEqualTo("SELECT car FROM Car car WHERE car.hybrid = ?1 ORDER BY car.model, car.hybrid ");
	assertThat(query.getParameters()).containsExactly(false);
    }

    @Test
    public void select_entity_where_exists_boolean_in_boolean() {
	Car car = queryOn(new Car());
	Car car2 = andQueryOn(new Car());
	QueryBuilder query = select(car).from(car).whereExists(car2.isHybrid()).from(car2).where(car2.isHybrid()).isIn(car.isHybrid()).closeExists();
	assertThat(query.getQueryString()).isEqualTo("SELECT car FROM Car car WHERE EXISTS ( SELECT car2.hybrid FROM Car car2 WHERE car2.hybrid IN (car.hybrid) ) ");
    }

    @Test
    public void select_distinct_boolean() {
	Car car = queryOn(new Car());
	QueryBuilder query = select(distinct(car.isHybrid())).from(car);
	assertThat(query.getQueryString()).isEqualTo("SELECT distinct(car.hybrid) FROM Car car ");
    }

    @Test
    public void select_date_from_date() {
	Car car = queryOn(new Car());
	Date d = new Date();
	Condition<?> query = select(car).from(car).where(car.getReleaseDate()).isLessThan(d).andGroup(car.getSellDate()).isNull().or(car.getSellDate()).isLessThan(d).closeGroup();
	assertThat(query.getQueryString()).isEqualTo("SELECT car FROM Car car WHERE car.releaseDate < ?1 AND ( car.sellDate IS NULL OR car.sellDate < ?2 ) ");
    }

    @Test
    public void select_from_join_entity() {
	User user = queryOn(new User());
	Date d = new Date();
	Condition<?> query = select(user).from(user).innerJoin(user.getCar()).where(user.getCar().getReleaseDate()).isLessEqualThan(d).andGroup(user.getCar().getSellDate())
		.isNull().or(user.getCar().getSellDate()).isLessEqualThan(d).closeGroup();
	assertThat(query.getQueryString()).isEqualTo(
		"SELECT user FROM User user INNER JOIN user.car car WHERE car.releaseDate <= ?1 AND ( car.sellDate IS NULL OR car.sellDate <= ?2 ) ");
    }

}
