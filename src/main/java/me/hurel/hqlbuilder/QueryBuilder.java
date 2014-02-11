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