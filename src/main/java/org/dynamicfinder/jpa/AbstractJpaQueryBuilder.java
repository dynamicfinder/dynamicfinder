package org.dynamicfinder.jpa;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.dynamicfinder.Nullable;
import org.dynamicfinder.Order;
import org.dynamicfinder.QueryBuilder;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionType;
import org.dynamicfinder.spi.AbstractQueryBuilder;
import org.dynamicfinder.spi.RestrictionHandler;

/**
 * Implementation of {@link QueryBuilder} for Java Persistence API.
 * @author xsalefter (xsalefter[at]gmail.com)
 * @see JpaRestrictionHandlerFactory
 */
public abstract class AbstractJpaQueryBuilder extends AbstractQueryBuilder {

	private String entityName;
	private String entityAliasName;

	private final StringBuilder selectQueryStringBuilder;
	private final StringBuilder joinQueryStringBuilder;
	private final StringBuilder whereQueryStringBuilder;
	private final StringBuilder orderByQueryStringBuilder;
	private final StringBuilder groupByQueryStringBuilder;

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

		this.selectQueryStringBuilder = new StringBuilder();
		this.joinQueryStringBuilder = new StringBuilder();
		this.whereQueryStringBuilder = new StringBuilder();
		this.orderByQueryStringBuilder = new StringBuilder();
		this.groupByQueryStringBuilder = new StringBuilder();

		super.getCountQueryStringBuilder().append("select ").
			append("count(").append(this.getEntityAliasName()).append(")").
			append("from ").append(this.getEntityName()).append(" ").
			append(this.getEntityAliasName());
	}

	@Override
	public QueryBuilder select(final String... fields) {
		int count = 0;

		if (fields.length > 0 && fields[0] != null) {
			for (String fieldName : fields) {
				count ++;

				this.selectQueryStringBuilder.
					append(this.getEntityAliasName()).
					append(".").
					append(fieldName);

				if (count < fields.length)
					this.selectQueryStringBuilder.append(",");

				this.selectQueryStringBuilder.append(" ");
			}
		} else {
			this.selectQueryStringBuilder.append(this.getEntityAliasName()).append(" ");
		}

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
		// Used to handle internal 'where' criteria/restriction.
		final int restrictionSize = restrictions.size();

		for (int i = 0; i < restrictions.size(); i++) {
			final Restriction restriction = restrictions.get(i);
			restriction.setParameter(super.getActualRestrictionSize() + 1); // Any better idea than this??

			final RestrictionType restrictionType = restriction.getRestrictionType();
			final RestrictionHandler handler = super.getRestrictionHandler(restrictionType);
			final RestrictionHandler.Result dto = handler.handleRestriction(this, restriction);
			this.whereQueryStringBuilder.append(dto.getRestrictionString());

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
				this.whereQueryStringBuilder.append(" ").append(stringLogic).append(" ");
			}

			// If nullable is DISCARD, we need to make sure that restriction is also 
			// parameterized. This is needed to avoid double logic added in 
			// query string.
			if (nullableIsDiscard && isRestrictionParameterized && !isLastRestrictions) {
				this.whereQueryStringBuilder.append(" ").append(stringLogic).append(" ");
			}

		} // end of for.

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
		if (this.selectQueryStringBuilder.length() > 0) {
			// Add last sentence for 'select'
			this.selectQueryStringBuilder.
				append("from ").append(this.getEntityName()).
				append(" ").append(this.getEntityAliasName());
		} else {
			this.selectQueryStringBuilder.
				append(this.getEntityAliasName()).
				append(" from ").append(this.getEntityName()).
				append(" ").append(this.getEntityAliasName());
		}

		// Add to master.
		super.getQueryStringBuilder().append("select ").
			append(this.selectQueryStringBuilder);

		// Check whether we have actual restriction. If so, master queryStringBuilder
		// and countQueryStringBuilder appended with the restriction string.
		if (this.whereQueryStringBuilder.length() > 0) {
			super.getQueryStringBuilder().append(" where ").
				append(this.getLastRestrictionString(this.whereQueryStringBuilder));

			super.getCountQueryStringBuilder().append(" where ").
				append(this.getLastRestrictionString(this.whereQueryStringBuilder));
		}


		final String result = super.getQueryStringBuilder().length() > 0 ? 
			super.getQueryStringBuilder().toString().trim() : 
			new StringBuilder().append("from ").append(this.getEntityName()).toString();

		// Clear all
		this.selectQueryStringBuilder.setLength(0);
		this.joinQueryStringBuilder.setLength(0);
		this.whereQueryStringBuilder.setLength(0);
		this.orderByQueryStringBuilder.setLength(0);
		this.groupByQueryStringBuilder.setLength(0);
		
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
		final int length = whereRestriction.toString().trim().length();
		final int startForAnd = length - 3;
		final int startForOr = length - 2;

		if (whereRestriction.subSequence(startForAnd, length).toString().trim().equals("and"))
			return whereRestriction.delete(startForAnd, length).toString().trim();

		if (whereRestriction.subSequence(startForOr, length).toString().trim().equals("or")) 
			return whereRestriction.delete(startForOr, length).toString().trim();

		return whereRestriction.toString().trim();
	}

	/**
	 * Get {@link #select(String...)} query string handler.
	 * @return {@link StringBuilder} for {@link #select(String...)}.
	 */
	protected final StringBuilder getSelectQueryStringBuilder() {
		return selectQueryStringBuilder;
	}

	/**
	 * Get {@link #join(String...)} query string handler.
	 * @return {@link StringBuilder} for {@link #join(String...)}.
	 */
	protected final StringBuilder getJoinQueryStringBuilder() {
		return joinQueryStringBuilder;
	}

	/**
	 * Get {@link #where(List)} or {@link #where(Restriction...)} query string 
	 * handler.
	 * @return {@link StringBuilder} for {@link #join(String...)}.
	 */
	protected final StringBuilder getWhereQueryStringBuilder() {
		return whereQueryStringBuilder;
	}

	/**
	 * Get {@link #orderBy(Order...)} query string handler.
	 * @return {@link StringBuilder} for {@link #orderBy(Order...)}.
	 */
	protected final StringBuilder getOrderByQueryStringBuilder() {
		return orderByQueryStringBuilder;
	}

	/**
	 * Get {@link #groupBy(String...)} query string handler.
	 * @return {@link StringBuilder} for {@link #groupBy(String...)}.
	 */
	protected final StringBuilder getGroupByQueryStringBuilder() {
		return groupByQueryStringBuilder;
	}

	private void createEntityAndAliasNameFromClass(Class<?> entityClass) {
		try {
			@SuppressWarnings("unchecked")
			final Class<? extends Annotation> entityAnnotation = 
				(Class<? extends Annotation>) Class.forName("javax.persistence.Entity");
			Annotation entityAnnotationInstance = entityClass.getAnnotation(entityAnnotation);
			final Method nameMemberMethod = entityAnnotation.getDeclaredMethod("name");
			final String localEntityName = (String) nameMemberMethod.invoke(entityAnnotationInstance);

			if (!localEntityName.equals("")) {
				this.entityName = localEntityName;
			} else {
				this.entityName = entityClass.getSimpleName();
			}

		} catch (Exception e) {
			logger.info(">>>>> {}", e.toString());
			this.entityName = entityClass.getSimpleName();
		} finally {
			this.entityAliasName = Introspector.decapitalize(this.entityName);
		}

	}
}
