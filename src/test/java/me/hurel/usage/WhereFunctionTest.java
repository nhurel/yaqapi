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
public class WhereFunctionTest {

    @Test
    public void where_size(){
	User user = queryOn(User.class);
	User father = user.getFather();
	Condition<Integer> query = selectFrom(user).innerJoin(father).where(size(user.getChildren())).isGreaterThan(size(father.getChildren()));
	assertThat(query.getQueryString()).isEqualTo("SELECT user FROM User user INNER JOIN user.father user2 WHERE size(user.children) > size(user2.children) ");
	assertThat(query.getParameters()).isNullOrEmpty();
    }

}
