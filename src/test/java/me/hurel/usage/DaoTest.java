/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.usage;

import static org.fest.assertions.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import me.hurel.dao.UserDao;
import me.hurel.entity.User;

import org.h2.Driver;
import org.junit.Before;
import org.junit.Test;

public class DaoTest {

    private UserDao dao = new UserDao();

    @Before
    public void populateDB() throws SQLException {
	Connection connection = new Driver().connect("jdbc:h2:mem:yaqapi", null);
	connection.createStatement().execute("DROP TABLE IF EXISTS T_USER");
	connection.createStatement().execute("DROP TABLE IF EXISTS T_CAR");
	connection.createStatement().execute(
		"CREATE TABLE T_CAR(ID BIGINT, RELEASE_DATE DATE, SELL_DATE DATE, MODEL VARCHAR(255), PRICE NUMBER(10,2), USER_ID BIGINT, HYBRID TINYINT(1) )");
	connection.createStatement().execute(
		"CREATE TABLE T_USER(ID BIGINT, ADRESS_ID BIGINT, FIRSTNAME VARCHAR(255), LASTNAME VARCHAR(255), AGE SMALLINT, FATHER_ID BIGINT, CAR_ID BIGINT, MALE TINYINT(1) )");
	connection.createStatement().executeUpdate("INSERT INTO T_USER VALUES(1,NULL,'grandfather','toto',25, NULL, NULL, 1)");
	connection.createStatement().executeUpdate("INSERT INTO T_USER VALUES(2,NULL,'titi','toto',25, 1, NULL, 1)");
	connection.createStatement().executeUpdate("INSERT INTO T_USER VALUES(3,NULL,'tata','tutu',2, 2, NULL, 0)");
	connection.close();
    }

    @Test
    public void test_query_on_dao() {
	User user = dao.getUserByFirstName("titi");
	assertThat(user).isNotNull();
	assertThat(user.getFirstName()).isEqualTo("titi");
    }

    @Test
    public void test_query_on_dao_size_function() {
	List<User> users = dao.getUserHavingChildren();
	assertThat(users).isNotEmpty();
	assertThat(users).hasSize(2);
	assertThat(users).onProperty("firstName").containsOnly("grandfather", "titi");
    }

    @Test
    public void test_query_on_dao_exists() {
	List<User> users = dao.getUserHavingLittleChildren();
	assertThat(users).isNotEmpty();
	assertThat(users).onProperty("firstName").containsExactly("titi");
	List<User> children = users.get(0).getChildren();
	assertThat(children).isNotEmpty();
	assertThat(children.get(0).getFirstName()).isEqualTo("tata");
    }

    @Test
    public void test_query_on_dao_exists_in_exists() {
	List<User> users = dao.getUserHavingChildrenHavingLittleChildren();
	assertThat(users).isNotEmpty();
	assertThat(users).onProperty("firstName").containsExactly("grandfather");
    }

    @Test
    public void test_query_on_dao_order_by_count() {
	String familyName = dao.getUserNameHavingMaxChildren();
	assertThat(familyName).isNotEmpty();
	assertThat(familyName).isEqualTo("toto");
    }

}
