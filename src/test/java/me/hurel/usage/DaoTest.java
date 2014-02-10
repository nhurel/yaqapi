package me.hurel.usage;

import static org.fest.assertions.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import me.hurel.dao.UserDao;
import me.hurel.entity.User;

import org.h2.Driver;
import org.junit.Before;
import org.junit.Test;

public class DaoTest {

    UserDao dao = new UserDao();

    @Before
    public void populateDB() throws SQLException {
	Connection connection = new Driver().connect("jdbc:h2:mem:yaqapi", null);
	connection.createStatement().execute("CREATE TABLE T_USER(ID BIGINT, ADRESS_ID BIGINT, FIRSTNAME VARCHAR(255), LASTNAME VARCHAR(255), AGE SMALLINT)");
	connection.createStatement().executeUpdate("INSERT INTO T_USER VALUES(1,NULL,'titi','toto',5)");
	connection.close();
    }

    @Test
    public void test_query_on_dao() {
	User user = dao.getUserByFirstName("titi");
	assertThat(user).isNotNull();
	assertThat(user.getFirstName()).isEqualTo("titi");
    }

}
