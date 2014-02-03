package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.HibernateQueryBuilder.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.User;

import org.hibernate.Session;
import org.junit.Test;

public class OuterJoinTest {

    Session session = null;

    @Test
    public void should_left_join_on_property() {
	String queryString = select(session).from(User.class).leftJoin("user.adress").getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN user.adress adress ");
    }

    @Test
    public void should_left_join_on_methodCall() {
	User user = queryOn(new User());
	String queryString = select(session).from(User.class).leftJoin(user.getAdress()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN user.adress adress ");
    }

    @Test
    public void should_left_join_on_alias() {
	String queryString = select(session).from(User.class).leftJoin("user.adress", "a").getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN user.adress a ");
    }

    @Test
    public void should_left_fetch_join_on_property() {
	String queryString = select(session).from(User.class).leftJoinFetch("user.adress").getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN FETCH user.adress adress ");
    }

    @Test
    public void should_left_fetch_join_on_methodCall() {
	User user = queryOn(new User());
	String queryString = select(session).from(User.class).leftJoin(user.getAdress()).fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN FETCH user.adress adress ");
    }

    @Test
    public void should_left_fetch_join_on_alias() {
	String queryString = select(session).from(User.class).leftJoin("user.adress", "a").fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN FETCH user.adress a ");
    }

    @Test
    public void should_right_join_on_property() {
	String queryString = select(session).from(User.class).rightJoin("user.adress").getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user RIGHT JOIN user.adress adress ");
    }

    @Test
    public void should_right_join_on_methodCall() {
	User user = queryOn(new User());
	String queryString = select(session).from(User.class).rightJoin(user.getAdress()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user RIGHT JOIN user.adress adress ");
    }

    @Test
    public void should_right_join_on_alias() {
	String queryString = select(session).from(User.class).rightJoin("user.adress", "a").getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user RIGHT JOIN user.adress a ");
    }

    @Test
    public void should_right_fetch_join_on_property() {
	String queryString = select(session).from(User.class).rightJoinFetch("user.adress").getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user RIGHT JOIN FETCH user.adress adress ");
    }

    @Test
    public void should_right_fetch_join_on_methodCall() {
	User user = queryOn(new User());
	String queryString = select(session).from(User.class).rightJoin(user.getAdress()).fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user RIGHT JOIN FETCH user.adress adress ");
    }

    @Test
    public void should_right_fetch_join_on_alias() {
	String queryString = select(session).from(User.class).rightJoin("user.adress", "a").fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user RIGHT JOIN FETCH user.adress a ");
    }

}
