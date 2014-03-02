/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.functions;

public class ParameterizedFunction<T> implements Function<T> {

    private final String name;

    private final Object entity;

    public ParameterizedFunction(String name, T entity) {
	super();
	this.name = name;
	this.entity = entity;
    }

    public ParameterizedFunction(FUNCTION name, T entity) {
	this(name.getFunction(), entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.functions.Function#getName()
     */
    public String getName() {
	return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see me.hurel.hqlbuilder.functions.Function#getEntity()
     */
    public Object getEntity() {
	return entity;
    }

}
