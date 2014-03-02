/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.internal;

import static org.fest.assertions.Assertions.*;

import java.lang.reflect.Method;

import me.hurel.entity.User;

import org.junit.Test;

public class ProxyUtilTest {

    @Test
    public void find_class() throws NoSuchMethodException, SecurityException {
	Method m = User.class.getMethod("getChildren");
	Class<?> foundClass = ProxyUtil.getParameter(m);
	assertThat(foundClass).isEqualTo(User.class);
    }

}
