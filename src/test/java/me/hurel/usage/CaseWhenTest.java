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
import org.junit.Test;


import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;

public class CaseWhenTest {

    @Test
    public void case_when() {
	User user = queryOn(new User());
	String queryString = select(caseWhen(user.getChildren()).isNull().then(false).whenElse(true)).from(user).getQueryString();
	assertThat(queryString).isEqualTo("SELECT (CASE WHEN user.children IS NULL THEN false ELSE true END) FROM User user ");
    }

}
