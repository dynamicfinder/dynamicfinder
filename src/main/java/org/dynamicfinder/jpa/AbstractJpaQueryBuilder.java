package org.dynamicfinder.jpa;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.dynamicfinder.Nullable;
import org.dynamicfinder.Order;
import org.dynamicfinder.QueryBuilder;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionType;
import org.dynamicfinder.spi.AbstractQueryBuilder;
import org.dynamicfinder.spi.AbstractRestriction;
import org.dynamicfinder.spi.RestrictionHandler;

/**
 * Implementation of {@link QueryBuilder} for Java Persistence API.
 * @author xsalefter (xsalefter[at]gmail.com)
 * @see JpaRestrictionHandlerFactory
 */
public abstract class AbstractJpaQueryBuilder extends AbstractQueryBuilder {

	private String entityName;
	private String entityAliasName;

	private List<String> fields = new ArrayList<String>();
	private List<Restriction> restrictions = new ArrayList<Restriction>();

	/** 
	 * Used to indicated that {@link #restrictions} is already parsed and 
	 * assigned to {@link #restrictionString} 
	 */
	private boolean restrictionParsed;

	/**
	 * Needed because we don't want 
	 */
	private String restrictionString;

	/**
	 * Constructor with entity class as parameter and restriction handler used 
	 * to handle {@link Restriction}.
	 * @param entityClass JPA entity class.
	 * @param restrictionHandlers to handle {@link Restriction}.
	 */
	public AbstractJpaQueryBuilder(
			final Class<?> entityClass, 
			Map<RestrictionType, RestrictionHandler> restrictionHandlers) {
		super();
		this.createEntityAndAliasNameFromClass(entityClass);
		super.addRestrictionHandlers(restrictionHandlers);
	}

	@Override
	public QueryBuilder select(final String... fields) {
		this.fields.addAll(Arrays.asList(fields));
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
		this.restrictions.addAll(restrictions);
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
		// Select
		getQueryStringBuilder().
			append("select ").append(this.generateSelectQuery()).
			append("from ").append(this.getEntityName()).
			append(" ").append(this.getEntityAliasName());

		// Where
		if (!this.restrictionParsed) {
			this.restrictionString = this.getLastRestrictionString( this.generateWhereQuery() );
			this.restrictionParsed = true;
		}
		if (!this.restrictionString.isEmpty()) {
			super.getQueryStringBuilder().append(" where ").append(this.restrictionString);
		}

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
		// Select
		super.getCountQueryStringBuilder().append("select ").
			append("count(").append(this.getEntityAliasName()).append(") ").
			append("from ").append(this.getEntityName()).append(" ").
			append(this.getEntityAliasName());

		// Where
		if (!this.restrictionParsed) {
			this.restrictionString = this.getLastRestrictionString( this.generateWhereQuery() );
			this.restrictionParsed = true;
		}
		if (!this.restrictionString.isEmpty()) {
			super.getCountQueryStringBuilder().append(" where ").append(this.restrictionString);
		}

		final String result = super.getCountQueryStringBuilder().toString();
		return result;
	}


	@Override
	public String getEntityAliasName() {
		return this.entityAliasName;
	}

	/**
	 * Get the entity name.
	 * @return {@link String} entity name.
	 */
	protected String getEntityName() {
		return this.entityName;
	}

	/**
	 * Used to parse 'and' or 'or' string in the last of restriction query string.
	 * @param whereRestriction the whole {@link Restriction} query string. Shouldn't 
	 * be null.
	 * @return parsed {@link Restriction} as query {@link String}.
	 */
	protected String getLastRestrictionString(StringBuilder whereRestriction) {
		if (whereRestriction.length() == 0)
			return whereRestriction.toString();

		final int length = whereRestriction.toString().trim().length();
		final int startForAnd = length - 3;
		final int startForOr = length - 2;

		if (whereRestriction.subSequence(startForAnd, length).toString().trim().equals("and"))
			return whereRestriction.delete(startForAnd, length).toString().trim();

		if (whereRestriction.subSequence(startForOr, length).toString().trim().equals("or")) 
			return whereRestriction.delete(startForOr, length).toString().trim();

		return whereRestriction.toString().trim();
	}

	private void createEntityAndAliasNameFromClass(Class<?> entityClass) {
		try {
			@SuppressWarnings("unchecked")
			final Class<? extends Annotation> entityAnnotation = 
				(Class<? extends Annotation>) Class.forName("javax.persistence.Entity");
			Annotation entityAnnotationInstance = entityClass.getAnnotation(entityAnnotation);
			final Method nameMemberMethod = entityAnnotation.getDeclaredMethod("name");
			final String localEntityName = (String) nameMemberMethod.invoke(entityAnnotationInstance);

			if (!localEntityName.equals("")) 
				this.entityName = localEntityName;
			else 
				this.entityName = entityClass.getSimpleName();
		} catch (Exception e) {
			this.entityName = entityClass.getSimpleName();
		} finally {
			this.entityAliasName = Introspector.decapitalize(this.entityName);
		}
	}

	// -- 

	private StringBuilder generateSelectQuery() {
		StringBuilder stringBuilder = new StringBuilder();
		int count = 0;

		if (fields.size() > 0) {
			for (String fieldName : fields) {
				count ++;
				stringBuilder.append(this.getEntityAliasName()).append(".").append(fieldName);
				if (count < fields.size()) 
					stringBuilder.append(",");
				stringBuilder.append(" ");
			}
		} else {
			stringBuilder.append(this.getEntityAliasName()).append(" ");
		}

		return stringBuilder;
	}

	private StringBuilder generateWhereQuery() {
		StringBuilder stringBuilder = new StringBuilder();

		// Used to handle internal 'where' criteria/restriction.
		final int restrictionSize = restrictions.size();

		for (int i = 0; i < restrictions.size(); i++) {
			final Restriction r = restrictions.get(i);

			if (!(r instanceof AbstractRestriction)) {
				final String e = "Passed Restriction should be instance of " +
						"org.dynamicfinder.spi.AbstractRestriction";
				throw new IllegalArgumentException(e);
			}

			final AbstractRestriction restriction = (AbstractRestriction) r;
			restriction.setParameter(super.getActualRestrictionSize() + 1); // Any better idea than this??

			final RestrictionType restrictionType = restriction.getRestrictionType();
			final RestrictionHandler handler = super.getRestrictionHandler(restrictionType);
			final RestrictionHandler.Result dto = handler.handleRestriction(this, restriction);
			stringBuilder.append(dto.getRestrictionString());

			final boolean isRestrictionParameterized = dto.hasParameterizedQueryString();

			if (isRestrictionParameterized)
				super.addActualRestriction(restriction.getParameter(), restriction);

			final String stringLogic = restriction.getRestrictionLogic().toLowerCase();

			// If DISCARD, we need to make sure that no current restriction 
			// and nextRestriction need a parameter, to deal with RestrictionLogic.
			final boolean nullableIsKeep = restriction.getNullable().equals(Nullable.KEEP);
			final boolean nullableIsDiscard = nullableIsKeep == false;
			final boolean isLastRestrictions = (i + 1) == restrictionSize;

			if (nullableIsKeep && !isLastRestrictions) {
				stringBuilder.append(" ").append(stringLogic).append(" ");
			}

			// If nullable is DISCARD, we need to make sure that restriction is also 
			// parameterized. This is needed to avoid double logic added in 
			// query string.
			if (nullableIsDiscard && isRestrictionParameterized && !isLastRestrictions) {
				stringBuilder.append(" ").append(stringLogic).append(" ");
			}
		} // end of for.

		return stringBuilder;
	}
}
