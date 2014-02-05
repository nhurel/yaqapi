package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.HibernateQueryBuilder.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.Adress;
import me.hurel.entity.City;
import me.hurel.entity.User;

import org.hibernate.Session;
import org.junit.Test;

public class ProxyOnlyTest {

    Session session;

    @Test
    public void select_on_one_entity() {
	User user = queryOn(new User());
	String queryString = selectFrom(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user ");
    }

    @Test
    public void select_property_on_one_entity() {
	User user = queryOn(new User());
	String queryString = select(user.getFirstName()).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName FROM User user ");
    }

    @Test
    public void select_properties_on_one_entity() {
	User user = queryOn(new User());
	String queryString = select(user.getFirstName(), user.getLastName()).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user.lastName FROM User user ");
    }

    @Test
    public void select_more_properties_on_one_entity() {
	Adress adress = queryOn(new Adress());
	String queryString = select(adress.getNumber(), adress.getStreet(), adress.getCity().getName()).from(adress).getQueryString();
	assertThat(queryString).isEqualTo("SELECT adress.number, adress.street, adress.city.name FROM Adress adress ");
    }

    @Test
    public void select_deep_properties_on_one_entity() {
	Adress adress = queryOn(new Adress());
	String queryString = select(adress.getNumber(), adress.getStreet(), adress.getCity().getName(), adress.getCity().getCountry().getName()).from(adress).getQueryString();
	assertThat(queryString).isEqualTo("SELECT adress.number, adress.street, adress.city.name, adress.city.country.name FROM Adress adress ");
    }

    @Test
    public void select_one_entity_from_join() {
	User user = queryOn(new User());
	String queryString = select(user).from(user).innerJoin(user.getAdress()).fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN FETCH user.adress adress ");
    }

    @Test
    public void select_one_entity_from_join_with_entity() {
	User user = queryOn(new User());
	Adress adress = user.getAdress();
	String queryString = select(user).from(user).innerJoin(adress).fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN FETCH user.adress adress ");
    }

    @Test
    public void select_two_entities_from_join() {
	User user = queryOn(new User());
	Adress adress = user.getAdress();
	String queryString = select(user, adress).from(user).innerJoin(adress).fetch().getQueryString();
	assertThat(queryString).isEqualTo("SELECT user, adress FROM User user INNER JOIN FETCH user.adress adress ");
    }

    @Test
    public void select_deep_properties_on_joined_entities() {
	Adress adress = queryOn(new Adress());
	City city = adress.getCity();
	String queryString = select(adress.getNumber(), adress.getStreet(), adress.getCity().getName(), city.getCountry().getName()).from(adress).innerJoin(city).getQueryString();
	assertThat(queryString).isEqualTo("SELECT adress.number, adress.street, adress.city.name, city.country.name FROM Adress adress INNER JOIN adress.city city ");
	// assertThat(queryString).isEqualTo("SELECT adress.number, adress.street, city.name, city.country.name FROM Adress adress INNER JOIN adress.city city ");
    }

    @Test
    public void select_deep_properties_on_two_entity() {
	Adress adress = queryOn(new Adress());
	City city = adress.getCity();
	String queryString = select(adress.getNumber(), adress.getStreet(), adress.getCity().getName(), city.getCountry().getName()).from(adress).getQueryString();
	assertThat(queryString).isEqualTo("SELECT adress.number, adress.street, adress.city.name, city.country.name FROM Adress adress ");
	// assertThat(queryString).isEqualTo("SELECT adress.number, adress.street, adress.city.name, adress.city.country.name FROM Adress adress ");
    }

}
