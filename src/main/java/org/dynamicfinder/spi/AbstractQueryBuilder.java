package org.dynamicfinder.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.dynamicfinder.NoRestrictionHandlerException;
import org.dynamicfinder.QueryBuilder;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provide useful methods to help implementation specifics build the query. 
 * @author xsalefter (xsalefter[at]gmail.com)
 * @see {@link RestrictionHandler}
 */
public abstract class AbstractQueryBuilder implements QueryBuilder {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractQueryBuilder.class);

	/** Used to handle query language string. */
	private final StringBuilder queryStringBuilder;

	/** Used to handle count / data size based on added {@link Restriction} */
	private final StringBuilder countQueryStringBuilder;

	private final Map<RestrictionType, RestrictionHandler> restrictionHandlers;
	private final Map<Integer, Restriction> actualRestrictions;

	public AbstractQueryBuilder() {
		this.queryStringBuilder = new StringBuilder();
		this.countQueryStringBuilder = new StringBuilder();
		this.restrictionHandlers = new HashMap<RestrictionType, RestrictionHandler>();
		this.actualRestrictions = new HashMap<Integer, Restriction>();
	}

	/**
	 * Get entity alias name. {@link QueryBuilder} implementation would use 
	 * this for creating the query.
	 * @return {@link String} entity alias name.
	 */
	public abstract String getEntityAliasName();

	@Override
	public final Map<Integer, Restriction> getActualRestrictions() {
		return Collections.unmodifiableMap(this.actualRestrictions);
	}

	/**
	 * Add new restriction (<strong>not</strong> {@link Restriction} object) 
	 * for used later in {@link #getActualRestrictions()}. This new restriction 
	 * added when {@link RestrictionHandler.Result#hasParameterizedQueryString()} 
	 * is true.
	 * @param key of restriction. Should be same value as parameter name 
	 * produced by {@link QueryBuilder}.
	 * @param value value of restriction, to binding to parameterized query 
	 *   string.
	 */
	protected final void addActualRestriction(final Integer id, final Restriction value) {
		this.actualRestrictions.put(id, value);
	}

	/**
	 * Get restriction size.
	 * @return restriction size.
	 */
	protected final int getActualRestrictionSize() {
		return this.actualRestrictions.size();
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

	/**
	 * Get {@link RestrictionHandler} for given {@link RestrictionType}.
	 * @param restrictionType for particular {@link RestrictionHandler}.
	 * @return {@link RestrictionHandler} instance based on given {@link RestrictionType}.
	 * @throws NoRestrictionHandlerException if no {@link RestrictionHandler}
	 *   found for given {@link RestrictionType}.
	 */
	protected final RestrictionHandler getRestrictionHandler(final RestrictionType restrictionType)
	throws NoRestrictionHandlerException {
		final RestrictionHandler handler = this.restrictionHandlers.get(restrictionType);
		if (handler == null)
			throw new NoRestrictionHandlerException(restrictionType);
		return handler;
	}

	/**
	 * Get {@link StringBuilder} that produce parameterized query string. 
	 * @return {@link StringBuilder} instance that used to produce query string.
	 */
	protected final StringBuilder getQueryStringBuilder() {
		return queryStringBuilder;
	}

	/**
	 * Get {@link StringBuilder} that create parameterized query string for 
	 * counting a data. 
	 * @return {@link StringBuilder} instance that used to produce query string 
	 * like <code>select count(entity) from Entity entity</code>.
	 */
	protected final StringBuilder getCountQueryStringBuilder() {
		return countQueryStringBuilder;
	}

}
