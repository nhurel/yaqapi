package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.HibernateQueryBuilder.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.Adress;
import me.hurel.entity.City;
import me.hurel.entity.Country;
import me.hurel.entity.User;

import org.hibernate.Session;
import org.junit.Test;

public class CartesianJoinTest {

    Session session = null;

    @Test
    public void should_join_on_2_entities() {
	String queryString = select(session).from(User.class).andFrom(Adress.class).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user , Adress adress ");
    }

    @Test
    public void should_join_on_2_entities_with_aliases() {
	String queryString = select(session).from(User.class, "u").andFrom(Adress.class, "a").getQueryString();
	assertThat(queryString).isEqualTo("SELECT u FROM User u , Adress a ");
    }

    @Test
    public void should_join_on_2_from_select() {
	Adress adress = queryOn(new Adress());
	String queryString = selectFrom(session, adress).andFrom(User.class, "u").getQueryString();
	assertThat(queryString).isEqualTo("SELECT adress FROM Adress adress , User u ");
    }

    @Test
    public void should_join_from_2_entities() {
	User user = queryOn(new User());
	String queryString = select(session, "user").from(User.class, "user").innerJoin(user.getAdress()).andFrom(Country.class).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress , Country country ");
    }

    @Test
    public void should_join_on_2nd_from() {
	User user = queryOn(new User());
	City city = andQueryOn(new City());
	String queryString = selectFrom(session, user).innerJoin(user.getAdress()).andFrom(City.class).leftJoin(city.getCountry()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN user.adress adress , City city LEFT JOIN city.country country ");
    }

    @Test
    public void should_select_from_2nd_join() {
	User user = queryOn(new User());
	City city = andQueryOn(new City());
	String queryString = select(session, "country").from(User.class).innerJoin(user.getAdress()).andFrom(City.class).leftJoin(city.getCountry()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT country FROM User user INNER JOIN user.adress adress , City city LEFT JOIN city.country country ");
    }

    @Test
    public void should_select_from_both_join() {
	User user = queryOn(new User());
	Adress adress = user.getAdress();
	City city = andQueryOn(new City());
	String queryString = select(session, "countryCity.name", "country").from(User.class).innerJoin(user.getAdress()).innerJoin(adress.getCity())
		.andFrom(City.class, "countryCity").leftJoin("countryCity.country").getQueryString();
	assertThat(queryString)
		.isEqualTo(
			"SELECT countryCity.name, country FROM User user INNER JOIN user.adress adress INNER JOIN adress.city city , City countryCity LEFT JOIN countryCity.country country ");
    }

}
