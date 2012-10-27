package org.dynamicfinder.jpa;

import org.dynamicfinder.Nullable;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionLogic;
import org.dynamicfinder.RestrictionType;
import org.dynamicfinder.exp.RestrictionExpression;

/**
 * Implementation {@link RestrictionExpression} for JPA.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaRestrictionExpression implements RestrictionExpression {

	private final String field;
	private Restriction restriction;

	/**
	 * New instance of {@link JpaRestrictionExpression} with field for 
	 * {@link Restriction#getField()}.
	 * @param field to set {@link Restriction#getField()}.
	 */
	protected JpaRestrictionExpression(final String field) {
		this.field = field;
	}

	/**
	 * Static method that actually build {@link JpaRestrictionExpression} instance. 
	 * @param field to set {@link Restriction#getField()}.
	 * @return new {@link JpaRestrictionExpression} instance.
	 */
	public static final RestrictionExpression $(final String field) {
		return new JpaRestrictionExpression(field);
	}

	@Override
	public RestrictionExpression equal(Object value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.EQUAL, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression notEqual(Object value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.NOT_EQUAL, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression greaterThan(Object value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.GREATER, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression greaterThanEqual(Object value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.GREATER_EQUAL, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression lesserThan(Object value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.LESSER, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression lesserThanEqual(Object value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.LESSER_EQUAL, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression like(String value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.LIKE, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression startWith(String value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression endWith(String value) {
		this.restriction = new JpaRestriction(this.field, RestrictionType.LIKE_WITH_POSTFIX, Nullable.DISCARD, value);
		return this;
	}

	@Override
	public RestrictionExpression keepNull() {
		this.restriction = new JpaRestriction( 
				this.restriction.getField(),
				this.restriction.getRestrictionType(),
				Nullable.KEEP,
				RestrictionLogic.AND,
				this.restriction.getValue());

		return this;
	}

	@Override
	public Restriction and() {
		return new JpaRestriction(
				this.restriction.getField(),
				this.restriction.getRestrictionType(),
				this.restriction.getNullable(),
				RestrictionLogic.AND,
				this.restriction.getValue());
	}

	@Override
	public Restriction or() {
		return new JpaRestriction(
				this.restriction.getField(),
				this.restriction.getRestrictionType(),
				this.restriction.getNullable(),
				RestrictionLogic.OR,
				this.restriction.getValue());
	}

}
