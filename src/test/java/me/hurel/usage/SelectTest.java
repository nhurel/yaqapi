package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.HibernateQueryBuilder.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.User;

import org.hibernate.Session;
import org.junit.Test;

public class SelectTest {
    Session session = null;

    @Test
    public void should_select_from_entity() {
	String queryString = select(session).from(User.class).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user ");
    }

    @Test
    public void should_select_from_alias() {
	String queryString = select(session, "u").from(User.class, "u").getQueryString();
	assertThat(queryString).isEqualTo("SELECT u FROM User u ");
    }

    @Test
    public void should_select_from_object() {
	User user = new User();
	String queryString = selectFrom(session, user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user ");
    }

    @Test
    public void should_select_multiple_fields() {
	String queryString = select(session, "user.firstName", "c.name").from(User.class).innerJoin("user.adress").innerJoin("adress.city", "c").innerJoin("c.country")
		.getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, c.name FROM User user INNER JOIN user.adress adress INNER JOIN adress.city c INNER JOIN c.country country ");
    }

    @Test
    public void should_select_multiple_fields_with_aliases() {
	String queryString = select(session, "user.firstName as name", "c.name as countryName").from(User.class).innerJoin("user.adress").innerJoin("adress.city", "c")
		.innerJoin("c.country").getQueryString();
	assertThat(queryString).isEqualTo(
		"SELECT user.firstName as name, c.name as countryName FROM User user INNER JOIN user.adress adress INNER JOIN adress.city c INNER JOIN c.country country ");
    }

}
