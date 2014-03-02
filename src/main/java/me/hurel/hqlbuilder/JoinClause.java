/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

import me.hurel.hqlbuilder.functions.Function;

public interface JoinClause extends FromClause {

    public JoinClause fetch();

    public <T> WithClause<T> with(T methodCall);

    public <T> WithClause<T> with(Function<T> methodCall);

    public <T> WithClause<T> withGroup(T methodCall);

    public <T> WithClause<T> withGroup(Function<T> methodCall);
}
