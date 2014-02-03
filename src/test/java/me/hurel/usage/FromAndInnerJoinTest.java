package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.HibernateQueryBuilder.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.Adress;
import me.hurel.entity.City;
import me.hurel.entity.User;

import org.hibernate.Session;
import org.junit.Test;

public class FromAndInnerJoinTest {

    Session session = null;

    @Test
    public void should_select_from_object_and_property() {
	User user = queryOn(new User());
	String queryString = selectFrom(session, user).innerJoin(user.getAdress()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress ");
    }

    @Test
    public void should_select_from_entity_and_property() {
	User user = queryOn(new User());
	String queryString = select(session).from(User.class).innerJoin(user.getAdress()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress ");
    }

    @Test
    public void should_select_fetching_property() {
	User user = queryOn(new User());
	String queryString = select(session).from(User.class).innerJoin(user.getAdress()).fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN FETCH user.adress adress ");
    }

    @Test
    public void should_inner_join_on_property() {
	String queryString = select(session).from(User.class).innerJoin("user.adress").getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress ");
    }

    @Test
    public void should_inner_join_on_property_with_alias() {
	String queryString = select(session).from(User.class).innerJoin("user.adress", "a").getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN user.adress a ");
    }

    @Test
    public void should_select_on_deep_property() {
	User user = queryOn(new User());
	String queryString = select(session).from(User.class).innerJoin(user.getAdress().getCity()).fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN FETCH user.adress.city city ");
    }

    @Test
    public void should_join_on_deep_property() {
	User user = queryOn(new User());
	Adress adress = user.getAdress();
	String queryString = select(session).from(User.class).innerJoin(user.getAdress()).innerJoin(adress.getCity()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress INNER JOIN adress.city city ");
    }

    @Test
    public void should_join_on_really_deep_property() {
	User user = queryOn(new User(), "u");
	Adress adress = user.getAdress();
	City city = adress.getCity();
	String queryString = select(session).from(User.class, "u").innerJoin(user.getAdress()).innerJoin(adress.getCity()).innerJoin(city.getCountry()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT u FROM User u INNER JOIN u.adress adress INNER JOIN adress.city city INNER JOIN city.country country ");
    }

    @Test
    public void should_join_on_really_deep_property_using_alias() {
	String queryString = select(session).from(User.class, "u").innerJoin("u.adress").innerJoin("adress.city", "c").innerJoin("c.country").getQueryString();
	assertThat(queryString).isEqualTo("SELECT u FROM User u INNER JOIN u.adress adress INNER JOIN adress.city c INNER JOIN c.country country ");
    }

    @Test
    public void should_join_and_fetch_directly() {
	Adress adress = queryOn(new Adress());
	String queryString = select(session).from(User.class, "u").innerJoinFetch("u.adress").innerJoinFetch(adress.getCity()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT u FROM User u INNER JOIN FETCH u.adress adress INNER JOIN FETCH adress.city city ");
    }

}
