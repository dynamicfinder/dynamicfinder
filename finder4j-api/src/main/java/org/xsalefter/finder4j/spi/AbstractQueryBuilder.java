package org.xsalefter.finder4j.spi;

import java.beans.Introspector;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xsalefter.finder4j.NoRestrictionHandlerException;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;

public abstract class AbstractQueryBuilder implements QueryBuilder {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractQueryBuilder.class);

	/** Used to handle query language string. */
	private final StringBuilder queryStringBuilder;

	/** Used to handle count / data size based on added {@link Restriction} */
	private final StringBuilder countQueryStringBuilder;

	private final Class<?> entityClass;
	private final String entityName;
	private final String entityAliasName;

	private final Map<RestrictionType, RestrictionHandler> restrictionHandlers;
	private final Map<String, Object> restrictions;

	public AbstractQueryBuilder(final Class<?> entityClass) {
		this.queryStringBuilder = new StringBuilder();
		this.countQueryStringBuilder = new StringBuilder();
		this.entityClass = entityClass;
		this.entityName = this.entityClass.getSimpleName();
		this.entityAliasName = Introspector.decapitalize(this.entityName);

		this.restrictionHandlers = new HashMap<RestrictionType, RestrictionHandler>();
		this.restrictions = new HashMap<String, Object>();

		logger.debug("Creating new AbstractQueryBuilder for '{}'", this.entityName);
	}

	public AbstractQueryBuilder(final String entityName) {
		this.queryStringBuilder = new StringBuilder();
		this.countQueryStringBuilder = new StringBuilder();
		this.entityClass = Object.class;
		this.entityName = entityName;
		this.entityAliasName = Introspector.decapitalize(this.entityName);

		this.restrictionHandlers = new HashMap<RestrictionType, RestrictionHandler>();
		this.restrictions = new HashMap<String, Object>();

		logger.debug("Creating new AbstractQueryBuilder for '{}'", this.entityName);
	}

	@Override
	public final Map<String, Object> getRestrictions() {
		return Collections.unmodifiableMap(this.restrictions);
	}

	protected final void addRestriction(final String key, final Object value) {
		this.restrictions.put(key, value);
	}

	protected final int getRestrictionSize() {
		return this.restrictions.size();
	}

	/**
	 * <p>
	 * Add new or change {@link RestrictionHandler} to this {@link QueryBuilder}.
	 * </p>
	 * 
	 * <p>In implementation detail, each {@link RestrictionHandler} will be 
	 * placed in {@link Map} where the key is unique {@link RestrictionType} and 
	 * the value is {@link RestrictionHandler} implementation.</p>
	 * 
	 * @param restrictionType {@link RestrictionType} as an unique key.
	 * @param handler {@link RestrictionHandler} to added.
	 * @see AbstractQueryBuilder#addRestrictionHandlers(Map).
	 */
	protected final void setRestrictionHandler(final RestrictionType type, final RestrictionHandler handler) {
		this.restrictionHandlers.put(type, handler);
	}

	/**
	 * <p>
	 * Instead of add each {@link RestrictionHandler} one by one with calling 
	 * {@link #setRestrictionHandler(RestrictionType, RestrictionHandler)}, this 
	 * method would use {@link Map#putAll(Map)} to registering 
	 * {@link RestrictionHandler}s. 
	 * @param handlers {@link RestrictionHandler} to register.
	 */
	protected final void addRestrictionHandlers(final Map<RestrictionType, ? extends RestrictionHandler> handlers) {
		this.restrictionHandlers.putAll(handlers);
	}

	protected final RestrictionHandler getRestrictionHandler(final RestrictionType restrictionType)
	throws NoRestrictionHandlerException {
		final RestrictionHandler handler = this.restrictionHandlers.get(restrictionType);
		if (handler == null)
			throw new NoRestrictionHandlerException(restrictionType);
		return handler;
	}

	protected final StringBuilder getQueryStringBuilder() {
		return queryStringBuilder;
	}

	protected final StringBuilder getCountQueryStringBuilder() {
		return countQueryStringBuilder;
	}


	public final String getEntityName() {
		return entityName;
	}

	public final String getEntityAliasName() {
		return entityAliasName;
	}
}
