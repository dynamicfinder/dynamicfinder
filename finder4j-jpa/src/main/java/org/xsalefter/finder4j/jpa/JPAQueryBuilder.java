package org.xsalefter.finder4j.jpa;

import java.util.Arrays;
import java.util.List;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Order;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.spi.AbstractQueryBuilder;
import org.xsalefter.finder4j.spi.RestrictionHandler;
import org.xsalefter.finder4j.spi.RestrictionHandler.DTO;

public class JPAQueryBuilder extends AbstractQueryBuilder<String> {

	public JPAQueryBuilder(Class<?> entityClass) {
		super(entityClass);
		this.addRestrictionHandlers(new JPARestrictionHandlers());
	}

	@Override
	public JPAQueryBuilder select(final String... fields) {
		super.clearQueryString(); // clearing buffer.
		super.getQueryStringBuilder().append("select ");
		int count = 0;

		if (fields.length > 0) {
			for (String s : fields) {
				count ++;
				super.getQueryStringBuilder().append(super.getEntityName()).append(".").append(s);
				if (count < fields.length) 
					super.getQueryStringBuilder().append(",");
				super.getQueryStringBuilder().append(" ");
			}
		} else {
			super.getQueryStringBuilder().append(super.getEntityName()).append(" ");
		}

		super.getQueryStringBuilder().append("from ").append(super.getEntityClassName()).
			append(" ").append(super.getEntityName());

		return this;
	}

	@Override
	public JPAQueryBuilder select(QueryBuilder<String> subQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JPAQueryBuilder join(final String...fields) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public JPAQueryBuilder where(final Restriction... restrictions) {
		return this.where(Arrays.asList(restrictions));
	}

	@Override
	public JPAQueryBuilder where(List<Restriction> restrictions) {
		this.handleEmptySelect();

		// Used to handle internal 'where' criteria/restriction.
		final StringBuilder whereQueryString = new StringBuilder();

		int logicCounter = 1;
		final int restrictionSize = restrictions.size();

		for (int i = 0; i < restrictionSize; i++) {
			final Restriction restriction = restrictions.get(i);
			final RestrictionType restrictionType = restriction.getRestrictionType();
			final RestrictionHandler handler = super.getRestrictionHandler(restrictionType);
			handler.setEntityAliasName(this.getEntityName());

			RestrictionHandler.DTO dto = handler.handleRestriction(restriction);
			whereQueryString.append(dto.getRestrictionString());

			final boolean isRestrictionNeedParam = dto.hasParameterizedQueryString();
			if (isRestrictionNeedParam) 
				super.addRestriction(restriction.getId(), restriction);

			if (logicCounter < restrictionSize) {
				final Restriction nextRestriction = restrictions.get(logicCounter);
				final DTO nextRestrictionHandlerDTO = handler.handleRestriction(nextRestriction);
				final boolean isNextRestrictionNeedParam = 
						nextRestrictionHandlerDTO.hasParameterizedQueryString();

				// If DISCARD, we need to make sure that no current restriction 
				// and nextRestriction need a parameter, to determine whether 
				// RestrictionLogic (AND or OR) need to added to whereQueryString. 
				if (restriction.getNullable().equals(Nullable.DISCARD)) {
					if (isRestrictionNeedParam && isNextRestrictionNeedParam) {
						whereQueryString.append(" ").append(restriction.getLogic()).append(" ");
					}
				} else if (restriction.getNullable().equals(Nullable.KEEP)) {
					whereQueryString.append(" ").append(restriction.getLogic()).append(" ");
				}

				logicCounter ++;
			}

		} // end of for.

		if (whereQueryString.length() > 0) {
			super.getQueryStringBuilder().append(" where ").append(whereQueryString.toString());
		}
		return this;
	}

	@Override
	public JPAQueryBuilder orderBy(Order... orders) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public JPAQueryBuilder groupBy(String... fields) {
		// TODO Auto-generated method stub
		return this;
	}

	/**
	 * Call when {@link #where(List)} method found that the 
	 * {@link #getCountQueryStringBuilder()} is empty because the 
	 * {@link #select(String...)} method is not called 
	 * (which is considered as valid).
	 */
	protected void handleEmptySelect() {
		// If peoples too lazy to call {@link QueryBuilder#select()}
		if (super.getQueryStringBuilder().length() == 0) {
			super.getQueryStringBuilder().
				append("select ").append(super.getEntityName()).
				append(" from ").append(super.getEntityClassName()).
				append(" ").append(super.getEntityName());
		}
	}

	@Override
	public String getQueryString() {
		final String result = super.getQueryStringBuilder().length() > 0 ? 
				super.getQueryStringBuilder().toString() : 
				"from " + super.getEntityClassName();

		if (logger.isDebugEnabled()) logger.debug(result);

		return result;
	}

	@Override
	public String getCountQueryString() {
		// TODO Auto-generated method stub
		return null;
	}
}
