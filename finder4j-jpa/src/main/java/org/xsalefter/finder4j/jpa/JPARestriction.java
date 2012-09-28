package org.xsalefter.finder4j.jpa;

import java.io.Serializable;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.RestrictionLogic;
import org.xsalefter.finder4j.RestrictionType;

/**
 * Define data structure for query restriction.
 * @author xsalefter (xsalefter@gmail.com)
 */
public class JPARestriction implements Serializable {

	private static final long serialVersionUID = -467341589745717636L;

	private String id; // Used as <input name="${restriction.id}" /> or <h:inputText id="#{restriction.id}" />
	private String field;
	private RestrictionType type;
	private Object[] values;
	private Nullable nullable;
	private RestrictionLogic logic;


	/**
	 * Create new instance of {@link JPARestriction}. Set {@link JPARestriction#nullable} 
	 * value to {@link Nullable#DISCARD}, and set {@link RestrictionLogic} to 
	 * {@link RestrictionLogic#AND}. 
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of restriction. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public <T> JPARestriction(String field, RestrictionType type, Object...values) {
		this.nullable = Nullable.DISCARD;
		this.field = field;
		this.type = type;
		this.values = values;
		this.logic = RestrictionLogic.AND;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link JPARestriction}.
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of restriction. Couldn't be null.
	 * @param logic of restriction.
	 * @param values to pass to restriction.
	 */
	public <T> JPARestriction(String field, RestrictionType type, RestrictionLogic logic, Object...values) {
		this(field, type, values);
		this.logic = logic;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link JPARestriction}. Use default {@link RestrictionType} 
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of nullable. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public <T> JPARestriction(String field, Nullable nullable, Object...values) {
		this.field = field;
		this.nullable = nullable;
		this.logic = RestrictionLogic.AND;
		this.type = RestrictionType.EQUAL;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link JPARestriction}.
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of restriction. Couldn't be null.
	 * @param type of nullable. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public <T> JPARestriction(String field, RestrictionType restrictionType, Nullable nullable, Object...values) {
		this(field, restrictionType, values);
		this.nullable = nullable;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link JPARestriction}.
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of {@link RestrictionType}. Couldn't be null.
	 * @param nullable type of {@link Nullable}. Couldn't be null.
	 * @param logic type of {@link RestrictionLogic}. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public <T> JPARestriction(String field, RestrictionType restrictionType, Nullable nullable, RestrictionLogic logic, Object...values) {
		this(field, restrictionType, values);
		this.nullable = nullable;
		this.logic = logic;
		this.id = this.generateId();
	}

	public final String getId() {
		return id;
	}

	public Nullable getNullable() {
		return nullable;
	}

	public void setNullable(Nullable nullable) {
		this.nullable = nullable;
	}

	public String getField() {
		return field;
	}

	public RestrictionType getType() {
		return type;
	}

	public void setType(RestrictionType type) {
		this.type = type;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object...values) {
		this.values = values;
	}

	public RestrictionLogic getLogic() {
		return logic;
	}

	public void setLogic(final RestrictionLogic logic) {
		this.logic = logic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;

		JPARestriction other = (JPARestriction) obj;
		if (id.equalsIgnoreCase(other.getId())) 
			return true;

		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Field:").append(this.field).
			append(" JPARestriction Type:").append(this.type).append(" ").
			append("JPARestriction Nullable:").append(this.nullable).append(" ").
			append(" JPARestriction Logic:").append(this.logic).append(" ").
			append(" JPARestriction Value:");

		for (Object v : this.values) {
			if (v != null)
				sb.append(v.toString()).append(" ");
		}

		return sb.toString();
	}

	protected String generateId() {
		StringBuilder str = new StringBuilder().
				append(this.field).append("_").
				append(this.nullable.toString().toLowerCase()).append("_").
				append(this.type.toLowerCase()).append("_").
				append(this.logic);

		return str.toString();
	}
}
