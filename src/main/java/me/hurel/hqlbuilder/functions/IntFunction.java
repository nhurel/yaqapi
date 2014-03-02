/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.functions;

public class IntFunction implements Function<Integer> {

    private final String name;

    private final Object entity;

    public IntFunction(FUNCTION name, Object entity) {
	this(name.getFunction(), entity);
    }

    public IntFunction(String name, Object entity) {
	this.name = name;
	this.entity = entity;
    }

    public String getName() {
	return name;
    }

    public Object getEntity() {
	return entity;
    }

}
