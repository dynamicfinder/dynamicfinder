package org.xsalefter.finder4j.jpa;

import java.util.Arrays;
import java.util.List;

import org.xsalefter.finder4j.NoRestrictionHandlerException;
import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Order;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.spi.AbstractQueryBuilder;
import org.xsalefter.finder4j.spi.RestrictionHandler;
import org.xsalefter.finder4j.spi.RestrictionHandler.DTO;

public class JPAQueryBuilder extends AbstractQueryBuilder {

	public JPAQueryBuilder(Class<?> entityClass) {
		super(entityClass);
		final JPARestrictionHandlerFactory handlers = 
				new JPARestrictionHandlerFactory(super.getEntityAliasName());
		this.addRestrictionHandlers(handlers);

		super.getCountQueryStringBuilder().append("select ").
			append("count(").append(super.getEntityAliasName()).append(")").
			append("from ").append(super.getEntityName()).append(" ").
			append(super.getEntityAliasName());
	}

	@Override
	public QueryBuilder select(final String... fields) {
		// super.clearQueryString(); // clearing buffer.
		super.getQueryStringBuilder().append("select ");
		int count = 0;

		if (fields.length > 0) {
			for (String s : fields) {
				count ++;
				super.getQueryStringBuilder().append(super.getEntityAliasName()).append(".").append(s);
				if (count < fields.length) 
					super.getQueryStringBuilder().append(",");
				super.getQueryStringBuilder().append(" ");
			}
		} else {
			super.getQueryStringBuilder().append(super.getEntityAliasName()).append(" ");
		}

		super.getQueryStringBuilder().append("from ").append(super.getEntityName()).
			append(" ").append(super.getEntityAliasName());

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
			final RestrictionType restrictionType = restriction.getType();
			final RestrictionHandler handler = super.getRestrictionHandler(restrictionType);

			if (handler == null)
				throw new NoRestrictionHandlerException(restrictionType);

			RestrictionHandler.DTO dto = handler.handleRestriction(restriction);
			whereQueryString.append(dto.getRestrictionString());

			final boolean isRestrictionNeedParam = dto.hasParameterizedQueryString();
			if (isRestrictionNeedParam) 
				super.addRestriction(restriction.getId(), restriction.getValues());

			if (logicCounter < restrictionSize) {
				final Restriction nextRestriction = restrictions.get(logicCounter);
				final DTO nextRestrictionHandlerDTO = handler.handleRestriction(nextRestriction);
				final boolean isNextRestrictionNeedParam = 
						nextRestrictionHandlerDTO.hasParameterizedQueryString();

				// If DISCARD, we need to make sure that no current restriction and nextRestriction 
				// need a parameter, to deal with RestrictionLogic.
				if (restriction.getNullable().equals(Nullable.DISCARD)) {
					if (isRestrictionNeedParam && isNextRestrictionNeedParam) 
						whereQueryString.append(" ").append(restriction.getLogic().toLowerCase()).append(" ");
				} else if (restriction.getNullable().equals(Nullable.KEEP)) {
					whereQueryString.append(" ").append(restriction.getLogic().toLowerCase()).append(" ");
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
			new StringBuilder().append("from ").append(super.getEntityName()).toString();

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
				append("select ").append(super.getEntityAliasName()).
				append(" from ").append(super.getEntityName()).
				append(" ").append(super.getEntityAliasName());
		}
	}
}
