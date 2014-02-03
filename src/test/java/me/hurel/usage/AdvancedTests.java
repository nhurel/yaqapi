package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.HibernateQueryBuilder.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.Adress;
import me.hurel.entity.User;

import org.hibernate.Session;
import org.junit.Test;

public class AdvancedTests {

    Session session = null;

    @Test
    public void test_with_two_aliases() {
	User u = queryOn(new User(), "u");
	Adress a = andQueryOn(u.getAdress(), "a");
	String query = select(session).from(User.class, "u").innerJoin(u.getAdress()).innerJoin(a.getCity()).getQueryString();
	assertThat(query).isEqualTo("SELECT u FROM User u INNER JOIN u.adress a INNER JOIN a.city city ");

    }

    @Test
    public void test_with_later_alias() {
	User u = queryOn(new User(), "u");
	Adress a = andQueryOn(u.getAdress());
	String query = select(session).from(User.class, "u").innerJoin(u.getAdress(), "a").innerJoin(a.getCity()).getQueryString();
	assertThat(query).isEqualTo("SELECT u FROM User u INNER JOIN u.adress a INNER JOIN a.city city ");

    }

    @Test
    public void test_with_two_joins_on_same_entity() {
	User user1 = queryOn(new User(), "user1");
	User user2 = andQueryOn(new User(), "user2");
	Adress adress2 = andQueryOn(user2.getAdress(), "adress2");

	String query = select(session).from(User.class, "user1").innerJoin(user1.getAdress(), "adress").andFrom(User.class, "user2").innerJoin(user2.getAdress())
		.innerJoin(adress2.getCity()).getQueryString();
	assertThat(query).isEqualTo("SELECT user1 FROM User user1 INNER JOIN user1.adress adress , User user2 INNER JOIN user2.adress adress2 INNER JOIN adress2.city city ");

    }

}
