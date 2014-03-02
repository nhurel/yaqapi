/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public interface QueryBuilder {

    /**
     * Get the equivalent HQL query written through the API
     * 
     * @return
     */
    public String getQueryString();

    public List<Object> getParameters();

    /**
     * Builds the Hibernate query which this object defines
     * 
     * @param session
     *            the hibernate Session
     * @return Query the built query with all parameters set
     */
    public Query build(Session session);

}
