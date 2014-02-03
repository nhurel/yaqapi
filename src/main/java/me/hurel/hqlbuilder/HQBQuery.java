package me.hurel.hqlbuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.engine.query.spi.ParameterMetadata;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.QueryImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

public class HQBQuery implements Query{

	private final Query delegate;
	
	
	public HQBQuery(Query delegate) {
		super();
		this.delegate = delegate;
	}

	public FlushMode getFlushMode() {
		return delegate.getFlushMode();
	}

	public CacheMode getCacheMode() {
		return delegate.getCacheMode();
	}

	public boolean isCacheable() {
		return delegate.isCacheable();
	}

	public String getCacheRegion() {
		return delegate.getCacheRegion();
	}

	public Integer getTimeout() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getFetchSize() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	public Type[] getReturnTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getMaxResults() {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setMaxResults(int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getFirstResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setFirstResult(int firstResult) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setFlushMode(FlushMode flushMode) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCacheMode(CacheMode cacheMode) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCacheable(boolean cacheable) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCacheRegion(String cacheRegion) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setTimeout(int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setFetchSize(int fetchSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	public LockOptions getLockOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setLockOptions(LockOptions lockOptions) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setLockMode(String alias, LockMode lockMode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getComment() {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setComment(String comment) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query addQueryHint(String hint) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getReturnAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getNamedParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator iterate() {
		// TODO Auto-generated method stub
		return null;
	}

	public ScrollableResults scroll() {
		// TODO Auto-generated method stub
		return null;
	}

	public ScrollableResults scroll(ScrollMode scrollMode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List list() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object uniqueResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public int executeUpdate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Query setParameter(int position, Object val, Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setParameter(String name, Object val, Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setParameter(int position, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setParameter(String name, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setParameters(Object[] values, Type[] types) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setParameterList(String name, Collection values, Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setParameterList(String name, Collection values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setParameterList(String name, Object[] values, Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setParameterList(String name, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setProperties(Object bean) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setProperties(Map bean) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setString(int position, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCharacter(int position, char val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setBoolean(int position, boolean val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setByte(int position, byte val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setShort(int position, short val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setInteger(int position, int val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setLong(int position, long val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setFloat(int position, float val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setDouble(int position, double val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setBinary(int position, byte[] val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setText(int position, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setSerializable(int position, Serializable val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setLocale(int position, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setBigDecimal(int position, BigDecimal number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setBigInteger(int position, BigInteger number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setDate(int position, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setTime(int position, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setTimestamp(int position, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCalendar(int position, Calendar calendar) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCalendarDate(int position, Calendar calendar) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setString(String name, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCharacter(String name, char val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setBoolean(String name, boolean val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setByte(String name, byte val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setShort(String name, short val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setInteger(String name, int val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setLong(String name, long val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setFloat(String name, float val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setDouble(String name, double val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setBinary(String name, byte[] val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setText(String name, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setSerializable(String name, Serializable val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setLocale(String name, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setBigDecimal(String name, BigDecimal number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setBigInteger(String name, BigInteger number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setDate(String name, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setTime(String name, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setTimestamp(String name, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCalendar(String name, Calendar calendar) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setCalendarDate(String name, Calendar calendar) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setEntity(int position, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setEntity(String name, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setResultTransformer(ResultTransformer transformer) {
		// TODO Auto-generated method stub
		return null;
	}



}
