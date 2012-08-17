package org.xsalefter.finder4j.spi;

import java.beans.Introspector;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionHandler;
import org.xsalefter.finder4j.RestrictionType;

public abstract class AbstractQueryBuilder implements QueryBuilder {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractQueryBuilder.class);

	protected final StringBuilder queryString;
	protected final Class<?> entityClass;
	protected final String entityClassName;
	protected final String entityName;

	protected final Map<RestrictionType, RestrictionHandler> restrictionHandlers;
	protected final Map<String, Restriction> restrictions;

	public AbstractQueryBuilder(final Class<?> entityClass) {
		this.queryString = new StringBuilder();
		this.entityClass = entityClass;
		this.entityClassName = this.entityClass.getSimpleName();
		this.entityName = Introspector.decapitalize(this.entityClassName);

		this.restrictionHandlers = new HashMap<RestrictionType, RestrictionHandler>();
		this.restrictions = new HashMap<String, Restriction>();

		logger.debug("Creating new QueryBuilder for '{}' with entity name: {}", this.entityClassName, this.entityName);
	}

	@Override
	public final Map<String, Restriction> getRestrictions() {
		return this.restrictions;
	}

	@Override
	public void addRestrictionHandler(RestrictionType type, RestrictionHandler handler) {
		this.restrictionHandlers.put(type, handler);
	}

	public void addRestrictionHandlers(Map<? extends RestrictionType, ? extends RestrictionHandler> handlers) {
		this.restrictionHandlers.putAll(handlers);
	}
}
