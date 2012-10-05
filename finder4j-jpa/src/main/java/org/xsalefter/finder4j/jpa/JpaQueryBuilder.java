package org.xsalefter.finder4j.jpa;

import java.beans.Introspector;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Order;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.spi.AbstractQueryBuilder;
import org.xsalefter.finder4j.spi.RestrictionHandler;
import org.xsalefter.finder4j.spi.RestrictionHandler.DTO;

/**
 * Implementation of {@link QueryBuilder} for Java Persistence API.
 * @author xsalefter (xsalefter@gmail.com)
 * @see JpaRestrictionHandlerFactory
 */
public class JpaQueryBuilder extends AbstractQueryBuilder {

	private String entityName;
	private String entityAliasName;

	public JpaQueryBuilder(final Class<?> entityClass) {
		super();
		this.createEntityAliasAndNameFromClass(entityClass);
		this.addRestrictionHandlers(new JpaRestrictionHandlerFactory(this));

		super.getCountQueryStringBuilder().append("select ").
			append("count(").append(this.getEntityAliasName()).append(")").
			append("from ").append(this.getEntityName()).append(" ").
			append(this.getEntityAliasName());
	}

	@Override
	public QueryBuilder select(final String... fields) {
		super.getQueryStringBuilder().append("select ");
		int count = 0;

		if (fields.length > 0) {
			for (String fieldName : fields) {
				count ++;

				super.getQueryStringBuilder().
					append(this.getEntityAliasName()).
					append(".").
					append(fieldName);

				if (count < fields.length)
					super.getQueryStringBuilder().append(",");

				super.getQueryStringBuilder().append(" ");
			}
		} else {
			super.getQueryStringBuilder().append(this.getEntityAliasName()).append(" ");
		}

		super.getQueryStringBuilder().
			append("from ").append(this.getEntityName()).
			append(" ").append(this.getEntityAliasName());

		return this;
	}

	@Override
	public QueryBuilder join(final String...fields) {
		return this;
	}

	@Override
	public QueryBuilder where(final Restriction... restrictions) {
		return this.where(Arrays.asList(restrictions));
	}

	@Override
	public QueryBuilder where(List<Restriction> restrictions) {
		this.handleEmptySelect();

		// Used to handle internal 'where' criteria/restriction.
		final StringBuilder whereQueryString = new StringBuilder();

		int logicCounter = 1;
		final int restrictionSize = restrictions.size();

		for (int i = 0; i < restrictionSize; i++) {
			final Restriction restriction = restrictions.get(i);
			restriction.setParameter(super.getRestrictionSize() + 1); // Any better idea than this??

			final RestrictionType restrictionType = restriction.getRestrictionType();
			final RestrictionHandler handler = super.getRestrictionHandler(restrictionType);
			final RestrictionHandler.DTO dto = handler.handleRestriction(restriction);
			whereQueryString.append(dto.getRestrictionString());

			final boolean isRestrictionParameterized = dto.hasParameterizedQueryString();

			if (isRestrictionParameterized)
				super.addRestriction(restriction.getParameter(), restriction);

			if (logicCounter < restrictionSize) {
				final Restriction nextRestriction = restrictions.get(logicCounter);
				final DTO nextRestrictionHandlerDTO = handler.handleRestriction(nextRestriction);
				final boolean isNextRestrictionNeedParam = nextRestrictionHandlerDTO.hasParameterizedQueryString();

				// If DISCARD, we need to make sure that no current restriction 
				// and nextRestriction need a parameter, to deal with RestrictionLogic.
				if (restriction.getNullable().equals(Nullable.DISCARD)) {
					if (isRestrictionParameterized && isNextRestrictionNeedParam) 
						whereQueryString.
							append(" ").
							append(restriction.getRestrictionLogic().toLowerCase()).
							append(" ");
				} else if (restriction.getNullable().equals(Nullable.KEEP)) {
					whereQueryString.
						append(" ").
						append(restriction.getRestrictionLogic().toLowerCase()).
						append(" ");
				}

				logicCounter ++;
			}

		} // end of for.

		if (whereQueryString.length() > 0) {
			super.getQueryStringBuilder().append(" where ").append(whereQueryString.toString());
			super.getCountQueryStringBuilder().append(" where ").append(whereQueryString.toString());
		}
		return this;
	}

	@Override
	public QueryBuilder orderBy(Order... orders) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public QueryBuilder groupBy(String... fields) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getQueryString() {
		final String result = super.getQueryStringBuilder().length() > 0 ? 
			super.getQueryStringBuilder().toString().trim() : 
			new StringBuilder().append("from ").append(this.getEntityName()).toString();

		super.getQueryStringBuilder().setLength(0);

		if (logger.isDebugEnabled()) 
			logger.debug(result);

		return result;
	}

	@Override
	public String getCountQueryString() {
		final String result = super.getCountQueryStringBuilder().toString().trim();
		super.getCountQueryStringBuilder().setLength(0);
		return result;
	}

	@Override
	public String getEntityAliasName() {
		return this.entityAliasName;
	}

	/**
	 * Call when {@link #where(List)} method found that the 
	 * {@link #getQueryStringBuilder()} is empty because the 
	 * {@link #select(String...)} method is not called (which is considered as 
	 * valid).
	 */
	protected void handleEmptySelect() {
		// If people too lazy to call {@link QueryBuilder#select()}
		if (super.getQueryStringBuilder().length() == 0) {
			super.getQueryStringBuilder().
				append("select ").append(this.getEntityAliasName()).
				append(" from ").append(this.getEntityName()).
				append(" ").append(this.getEntityAliasName());
		}
	}

	protected String getEntityName() {
		return this.entityName;
	}

	private void createEntityAliasAndNameFromClass(Class<?> entityClass) {
		if (entityClass.isAnnotationPresent(Entity.class) && 
			!entityClass.getAnnotation(Entity.class).name().equals("")) 
			this.entityName = entityClass.getAnnotation(Entity.class).name();
		else 
			this.entityName = entityClass.getSimpleName();

		this.entityAliasName = Introspector.decapitalize(this.entityName);
	}
}
