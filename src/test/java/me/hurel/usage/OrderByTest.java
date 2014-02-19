package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;
import me.hurel.entity.City;
import me.hurel.entity.User;
import me.hurel.hqlbuilder.OrderByClause;

import org.junit.Test;
public class OrderByTest {

    @Test
    public void where_order_by() {
        User user = queryOn(new User());
        OrderByClause query = select(user).from(user).where(user.getAge()).isLessEqualThan(5).orderBy(user.getAge());
        assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user WHERE user.age <= ?1 ORDER BY user.age ");
    }

    @Test
    public void from_order_by() {
        User user = queryOn(new User());
        OrderByClause query = select(user).from(user).orderBy(user.getAge());
        assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user ORDER BY user.age ");
    }

    @Test
    public void join_order_by() {
        User user = queryOn(new User());
        OrderByClause query = select(user).from(user).innerJoin(user.getAdress()).with(user.getAdress().getNumber()).isEqualTo("1")
                .orderBy(user.getAge());
        assertThat(query.getQueryString()).isEqualTo(
                "SELECT user FROM User user INNER JOIN user.adress adress WITH adress.number = ?1 ORDER BY user.age ");
    }

    @Test
    public void exists_order_by() {
        User user = queryOn(new User());
        City city = andQueryOn(new City());
        OrderByClause query = select(user).from(user).innerJoin(user.getAdress()).whereExists(city).from(city).where(city.getName()).isLike("Rou%")
                .and(city.getId()).isEqualTo(user.getAdress().getCity().getId()).closeExists().orderBy(user.getFirstName());
        assertThat(query.getQueryString()).isEqualTo(
                        "SELECT user FROM User user INNER JOIN user.adress adress WHERE EXISTS ( SELECT city FROM City city WHERE city.name LIKE ?1 AND city.id = adress.city.id ) ORDER BY user.firstName ");
    }

}
