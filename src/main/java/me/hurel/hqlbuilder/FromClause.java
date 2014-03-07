/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

public interface FromClause extends AbstractFromClause {

    public ExistsClause whereExists(Object methodCall);

    public ExistsClause whereNotExists(Object methodCall);

    public OrderByClause orderBy(Object... orders);

}
