/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.functions;

public interface Function<T> {

    public String getName();

    public Object getEntity();

    public enum FUNCTION {
	AVERAGE("avg"), COUNT("count"), DISTINCT("distinct"), MAX("max"), MIN("min"), SUM("sum"), SIZE("size");
	String function;

	FUNCTION(String function) {
	    this.function = function;
	}

	public String getFunction() {
	    return function;
	}
    }

}
