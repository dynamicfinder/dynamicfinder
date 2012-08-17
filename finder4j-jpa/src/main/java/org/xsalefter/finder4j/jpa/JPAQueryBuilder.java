package org.xsalefter.finder4j.jpa;

import java.util.Arrays;
import java.util.List;

import org.xsalefter.finder4j.NoRestrictionHandlerException;
import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Order;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionHandler;
import org.xsalefter.finder4j.RestrictionHandler.DTO;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.spi.AbstractQueryBuilder;

public class JPAQueryBuilder extends AbstractQueryBuilder {

	public JPAQueryBuilder(Class<?> entityClass) {
		super(entityClass);
		this.addRestrictionHandlers(new JPARestrictionHandler());
	}

	@Override
	public QueryBuilder select(final String... fields) {
		super.queryString.delete(0, super.queryString.length()); // clearing buffer.
		super.queryString.append("select ");
		int count = 0;

		if (fields.length > 0) {
			for (String s : fields) {
				count ++;
				super.queryString.append(super.entityName).append(".").append(s);
				if (count < fields.length) 
					super.queryString.append(",");
				super.queryString.append(" ");
			}
		} else {
			super.queryString.append(super.entityName).append(" ");
		}

		super.queryString.append("from ").append(super.entityClassName).
			append(" ").append(super.entityName);

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
			final RestrictionHandler handler = super.restrictionHandlers.get(restrictionType);

			if (handler == null)
				throw new NoRestrictionHandlerException(restrictionType);

			handler.setEntityName(super.entityName);
			RestrictionHandler.DTO dto = handler.parseRestriction(restriction);
			whereQueryString.append(dto.getRestrictionString());

			final boolean isRestrictionNeedParam = dto.isNeedParameter();
			if (isRestrictionNeedParam) 
				super.getRestrictions().put(restriction.getId(), restriction);

			if (logicCounter < restrictionSize) {
				final Restriction nextRestriction = restrictions.get(logicCounter);
				final DTO nextRestrictionHandlerDTO = handler.parseRestriction(nextRestriction);
				final boolean isNextRestrictionNeedParam = 
						nextRestrictionHandlerDTO.isNeedParameter();

				// If DISCARD, we need to make sure that no current restriction and nextRestriction 
				// need a parameter, to deal with RestrictionLogic.
				if (restriction.getNullable().equals(Nullable.DISCARD)) {
					if (isRestrictionNeedParam && isNextRestrictionNeedParam) 
						whereQueryString.append(" ").append(restriction.getLogic()).append(" ");
				} else if (restriction.getNullable().equals(Nullable.KEEP)) {
					whereQueryString.append(" ").append(restriction.getLogic()).append(" ");
				}

				logicCounter ++;
			}

		} // end of for.

		if (whereQueryString.length() > 0) {
			super.queryString.append(" where ").append(whereQueryString.toString());
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
	public QueryBuilder limit(int index, int dataSize) {
		// TODO Auto-generated method stub
		return this;
	}

	public String toString() {
		final String result = 
				super.queryString.length() > 0 ? 
				super.queryString.toString() : "from " + super.entityClassName;

		if (logger.isDebugEnabled()) logger.debug(result);

		return result;
	}

	/**
	 * Call when {@link #where(List)} method found that the {@link #queryString} 
	 * is empty because the {@link #select(String...)} method is not called 
	 * (which is considered as valid).
	 */
	protected void handleEmptySelect() {
		// If people too lazy to call {@link QueryBuilder#select()}
		if (super.queryString.length() == 0) {
			super.queryString.
				append("select ").append(super.entityName).
				append(" from ").append(super.entityClassName).
				append(" ").append(super.entityName);
		}
	}
}
