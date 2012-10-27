package org.dynamicfinder.jpa;

import java.io.Serializable;

import org.dynamicfinder.Nullable;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionLogic;
import org.dynamicfinder.RestrictionType;

/**
 * Define data structure for query restriction for Java Persistence API.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaRestriction implements Restriction, Serializable {

	private static final long serialVersionUID = 1238053379229080782L;

	private Integer parameter;
	private final String field;
	private final RestrictionType restrictionType;
	private final Object value;
	private final Nullable nullable;
	private final RestrictionLogic restrictionLogic;

	public JpaRestriction(
			final String field, 
			final Object...values) {
		super();
		this.field = field;
		this.restrictionType = RestrictionType.EQUAL;
		this.value = values;
		this.nullable = Nullable.DISCARD;
		this.restrictionLogic = RestrictionLogic.AND;
	}

	public JpaRestriction(
			final String field,
			final RestrictionType restrictionType,
			final Object...values) {
		super();
		this.field = field;
		this.restrictionType = restrictionType;
		this.value = values;
		this.nullable = Nullable.DISCARD;
		this.restrictionLogic = RestrictionLogic.AND;
	}

	public JpaRestriction(
			final String field,
			final RestrictionType restrictionType,
			final RestrictionLogic restrictionLogic,
			final Object...values) {
		super();
		this.field = field;
		this.restrictionType = restrictionType;
		this.value = values;
		this.nullable = Nullable.DISCARD;
		this.restrictionLogic = restrictionLogic;
	}

	public JpaRestriction(
			final String field,
			final RestrictionType restrictionType,
			final Nullable nullable,
			final Object...values) {
		super();
		this.field = field;
		this.restrictionType = restrictionType;
		this.value = values;
		this.nullable = nullable;
		this.restrictionLogic = RestrictionLogic.AND;
	}

	public JpaRestriction(
			final String field,
			final RestrictionType restrictionType,
			final Nullable nullable,
			final RestrictionLogic restrictionLogic,
			final Object...values) {
		super();
		this.field = field;
		this.restrictionType = restrictionType;
		this.value = values;
		this.nullable = nullable;
		this.restrictionLogic = restrictionLogic;
	}

	public final Integer getParameter() {
		return parameter;
	}

	public void setParameter(Integer parameter) {
		if (this.getParameter()  == null)
			this.parameter = parameter;
	}

	public final String getField() {
		return field;
	}

	public final RestrictionType getRestrictionType() {
		return restrictionType;
	}

	public final Object getValue() {
		if (this.isSingleValueRestrictionType()) {
			final Object[] result = (Object[]) this.value;
			return result.length != 0 ? result[0] : null;
		}
		return value;
	}

	public final Nullable getNullable() {
		return nullable;
	}

	public final RestrictionLogic getRestrictionLogic() {
		return restrictionLogic;
	}

	private boolean isSingleValueRestrictionType() {
		return this.restrictionType.equals(RestrictionType.EQUAL) ||
			   this.restrictionType.equals(RestrictionType.NOT_EQUAL) ||
			   this.restrictionType.equals(RestrictionType.GREATER) ||
			   this.restrictionType.equals(RestrictionType.GREATER_EQUAL) ||
			   this.restrictionType.equals(RestrictionType.LESSER) ||
			   this.restrictionType.equals(RestrictionType.LESSER_EQUAL) ||
			   this.restrictionType.equals(RestrictionType.LIKE) ||
			   this.restrictionType.equals(RestrictionType.LIKE_WITH_POSTFIX) ||
			   this.restrictionType.equals(RestrictionType.LIKE_WITH_PREFIX);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getField() == null) ? 0 : getField().hashCode());
		result = prime * result
				+ ((getNullable() == null) ? 0 : getNullable().hashCode());
		result = prime
				* result
				+ ((getRestrictionLogic() == null) ? 0 : getRestrictionLogic().hashCode());
		result = prime * result
				+ ((getRestrictionType() == null) ? 0 : getRestrictionType().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }

		if (!(obj instanceof JpaRestriction)) { return false; }

		JpaRestriction other = (JpaRestriction) obj;

		return 
			this.getField().equals( other.getField() ) &&
			this.getRestrictionType().equals( other.getRestrictionType() ) && 
			this.getRestrictionLogic().equals( other.getRestrictionLogic() ) &&
			this.getNullable().equals( other.getNullable() );
	}
}
