package org.xsalefter.finder4j.spi;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xsalefter.finder4j.NoRestrictionHandlerException;
import org.xsalefter.finder4j.ParameterRestriction;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;

public abstract class AbstractQueryBuilder<K> implements QueryBuilder<K> {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractQueryBuilder.class);

	/** Used to handle query language string. */
	private final StringBuilder queryStringBuilder;

	/** Used to handle count / data size based on added {@link Restriction} */
	private final StringBuilder countQueryStringBuilder;

	private final Class<?> entityClass;
	private final String entityClassName;
	private final String entityName;

	private final Map<RestrictionType, RestrictionHandler> restrictionHandlers;
	private final List<ParameterRestriction<K>> restrictions;

	public AbstractQueryBuilder(final Class<?> entityClass) {
		this.queryStringBuilder = new StringBuilder();
		this.countQueryStringBuilder = new StringBuilder();
		this.entityClass = entityClass;
		this.entityClassName = this.entityClass.getSimpleName();
		this.entityName = Introspector.decapitalize(this.entityClassName);

		this.restrictionHandlers = new HashMap<RestrictionType, RestrictionHandler>();
		this.restrictions = new ArrayList<ParameterRestriction<K>>();

		logger.debug("Creating new QueryBuilder for '{}'", this.entityName);
	}

	/**
	 * Used to add {@link Restriction}, 
	 * @param mapKey
	 * @param restriction
	 */
	protected <P> void addParameterizedRestriction(final P parameter, final Restriction restriction) {
		ParameterRestriction<P> pr = new ParameterRestriction<P>(parameter, restriction);
		this.restrictions.add(pr);
	}

	@Override
	public final List<ParameterRestriction<K>> getRestrictions() {
		return Collections.unmodifiableList(this.restrictions);
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

	protected final String getEntityClassName() {
		return entityClassName;
	}

	protected final String getEntityName() {
		return entityName;
	}

	protected final void clearQueryString() {
		this.queryStringBuilder.delete(0, this.queryStringBuilder.length()); // clearing buffer.
	}
}
