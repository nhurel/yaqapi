package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;
import static org.fest.assertions.Fail.*;
import me.hurel.entity.Adress;
import me.hurel.entity.City;
import me.hurel.entity.Country;
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
	assertThat(queryString).isEqualTo("SELECT adress.number, adress.street, city.name, city.country.name FROM Adress adress INNER JOIN adress.city city ");
    }

    @Test
    public void select_deep_properties_on_two_entity() {
	Adress adress = queryOn(new Adress());
	City city = adress.getCity();
	String queryString = select(adress.getNumber(), adress.getStreet(), adress.getCity().getName(), city.getCountry().getName()).from(adress).getQueryString();
	assertThat(queryString).isEqualTo("SELECT adress.number, adress.street, adress.city.name, adress.city.country.name FROM Adress adress ");
    }

    @Test(expected = RuntimeException.class)
    public void select_property_from_unknown_entity() {
	User user1 = queryOn(new User());
	User user2 = andQueryOn(new User());
	select(user1.getFirstName(), user1.getLastName(), user2.getFirstName()).from(user1).getQueryString();
	fail("Exception expected");
    }

    @Test
    public void select_property_from_external_entity() {
	User user = queryOn(new User());
	Adress adress = andQueryOn(new Adress());
	String queryString = select(user.getFirstName(), user.getLastName(), adress.getNumber()).from(user).andFrom(adress).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user.lastName, adress.number FROM User user , Adress adress ");
    }

    @Test
    public void select_property_from_two_entities_of_same_class() {
	User user1 = queryOn(new User());
	User user2 = andQueryOn(new User());
	String queryString = select(user1.getFirstName(), user1.getLastName(), user2.getFirstName()).from(user1).andFrom(user2).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.firstName, user.lastName, user2.firstName FROM User user , User user2 ");
    }

    @Test
    public void select_from_all_join_types() {
	User user = queryOn(new User());
	String queryString = select(user.getFirstName(), user.getAdress().getNumber(), user.getAdress().getCity(), user.getAdress().getCity().getName(),
		user.getAdress().getCity().getCountry().getName()).from(user).innerJoin(user.getAdress()).leftJoin(user.getAdress().getCity())
		.rightJoin(user.getAdress().getCity().getCountry()).getQueryString();

	assertThat(queryString)
		.isEqualTo(
			"SELECT user.firstName, adress.number, city, city.name, country.name FROM User user INNER JOIN user.adress adress LEFT JOIN adress.city city RIGHT JOIN city.country country ");

    }

    @Test
    public void select_from_join_on_deep_property() {
	User user = queryOn(new User());
	String queryString = select(user.getFirstName(), user.getAdress().getNumber(), user.getAdress().getCity().getName(), user.getAdress().getCity().getCountry().getName())
		.from(user).rightJoin(user.getAdress().getCity().getCountry()).getQueryString();

	assertThat(queryString).isEqualTo(
		"SELECT user.firstName, user.adress.number, user.adress.city.name, country.name FROM User user RIGHT JOIN user.adress.city.country country ");
    }

    @Test
    public void select_from_join_on_deep_property_with_alias() {
	User user = queryOn(new User());
	Adress adress = user.getAdress();
	Country country = adress.getCity().getCountry();

	String queryString = select(user.getFirstName(), adress.getNumber(), adress.getCity().getName(), country.getName()).from(user).rightJoin(country).getQueryString();

	assertThat(queryString).isEqualTo(
		"SELECT user.firstName, user.adress.number, user.adress.city.name, country.name FROM User user RIGHT JOIN user.adress.city.country country ");
    }

    @Test
    public void select_from_join_on_deep_property_with_city_alias() {
	User user = queryOn(new User());
	City city = user.getAdress().getCity();
	Country country = city.getCountry();

	String queryString = select(user.getFirstName(), user.getAdress().getNumber(), city.getName(), country.getName()).from(user).rightJoin(country).getQueryString();

	assertThat(queryString).isEqualTo(
		"SELECT user.firstName, user.adress.number, user.adress.city.name, country.name FROM User user RIGHT JOIN user.adress.city.country country ");
    }

    @Test
    public void select_with_fetching() {
	User user = queryOn(new User());

	String queryString = select(user).from(user).innerJoinFetch(user.getAdress()).leftJoin(user.getAdress().getCity()).fetch()
		.rightJoinFetch(user.getAdress().getCity().getCountry()).getQueryString();

	assertThat(queryString).isEqualTo("SELECT user FROM User user INNER JOIN FETCH user.adress adress LEFT JOIN FETCH adress.city city RIGHT JOIN FETCH city.country country ");
    }

    @Test
    public void select_list_property() {
	User user = queryOn(new User());
	String queryString = select(user.getChildren()).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user.children FROM User user ");
    }

    @Test
    public void join_on_list_property() {
	User user = queryOn(new User());

	String queryString = select(user).from(user).leftJoinFetch(user.getChildren()).leftJoin($(user.getChildren()).getAdress()).getQueryString();
	assertThat(queryString).isEqualTo("SELECT user FROM User user LEFT JOIN FETCH user.children children LEFT JOIN children.adress adress ");
    }

}
