/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.usage;

import me.hurel.entity.User;
import me.hurel.hqlbuilder.Condition;
import org.junit.Test;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;

public class CoalesceTest {

    @Test
    public void select_coalesce() {
        User user = queryOn(User.class);
        String queryString = select(user.getId()).andSelect(coalesce(user.getFather().getLastName(), user.getLastName())).as("maidenName")
                .from(user).leftJoin(user.getFather()).getQueryString();
        assertThat(queryString)
                .isEqualTo("SELECT user.id, coalesce(user2.lastName, user.lastName) AS maidenName FROM User user LEFT JOIN user.father user2 ");
    }

    @Test
    public void where_coalesce() {
        User user = queryOn(User.class);
        Condition<String> query = selectFrom(user).leftJoin(user.getFather()).where(coalesce(user.getFather().getLastName(), user.getLastName()))
                .isEqualTo("MAIDEN");
        assertThat(query.getQueryString())
                .isEqualTo("SELECT user FROM User user LEFT JOIN user.father user2 WHERE coalesce(user2.lastName, user.lastName) = ?1 ");
        assertThat(query.getParameters()).containsExactly("MAIDEN");
    }

    @Test
    public void group_by_coalesce() {
        User user = queryOn(User.class);
        String queryString = select(count("*")).from(user).leftJoin(user.getFather()).groupBy(coalesce(user.getFather().getLastName(), user.getLastName()), user.getFirstName()).getQueryString();
        assertThat(queryString)
                .isEqualTo("SELECT count(*) FROM User user LEFT JOIN user.father user2 GROUP BY coalesce(user2.lastName, user.lastName), user.firstName ");
    }

}
