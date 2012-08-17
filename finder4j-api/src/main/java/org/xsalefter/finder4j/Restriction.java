package org.xsalefter.finder4j;

import java.io.Serializable;

/**
 * Define data structure for query restriction.
 * @author xsalefter
 * @since 0.1
 */
public class Restriction implements Serializable {

	private static final long serialVersionUID = -467341589745717636L;

	private String id; // Used as <input name="${restriction.id}" /> or <h:inputText id="#{restriction.id}" />
	private Nullable nullable;
	private String field;
	private RestrictionType type;
	private Object[] values;
	private RestrictionLogic logic;


	/**
	 * Create new instance of {@link Restriction}. Set {@link Restriction#nullable} 
	 * value to {@link Nullable#DISCARD}, and set {@link RestrictionLogic} to 
	 * {@link RestrictionLogic#AND}. 
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of restriction. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public Restriction(String field, RestrictionType type, Object...values) {
		this.nullable = Nullable.DISCARD;
		this.field = field;
		this.type = type;
		this.values = values;
		this.logic = RestrictionLogic.AND;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link Restriction}.
	 * @param field to added as restriction. Couldn't be null.
	 * @param restrictionType of restriction as a {@link String}. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public Restriction(String field, String restrictionType, Object...values) {
		this(field, RestrictionType.of(restrictionType), values);
		this.logic = RestrictionLogic.AND;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link Restriction}.
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of restriction. Couldn't be null.
	 * @param logic of restriction.
	 * @param values to pass to restriction.
	 */
	public Restriction(String field, RestrictionType type, RestrictionLogic logic, Object...values) {
		this(field, type, values);
		this.logic = logic;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link Restriction}.
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of restriction. Couldn't be null.
	 * @param logic of restriction. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public Restriction(String field, String restrictionType, RestrictionLogic logic, Object...values) {
		this(field, restrictionType, values);
		this.logic = logic;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link Restriction}. Use default {@link RestrictionType} 
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of nullable. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public Restriction(String field, Nullable nullable, Object...values) {
		this.field = field;
		this.nullable = nullable;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link Restriction}.
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of restriction. Couldn't be null.
	 * @param type of nullable. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public Restriction(String field, RestrictionType restrictionType, Nullable nullable, Object...values) {
		this(field, restrictionType, values);
		this.nullable = nullable;
		this.id = this.generateId();
	}

	/**
	 * Create new instance of {@link Restriction}.
	 * @param field to added as restriction. Couldn't be null.
	 * @param type of {@link RestrictionType}. Couldn't be null.
	 * @param nullable type of {@link Nullable}. Couldn't be null.
	 * @param logic type of {@link RestrictionLogic}. Couldn't be null.
	 * @param values to pass to restriction.
	 */
	public Restriction(String field, RestrictionType restrictionType, Nullable nullable, RestrictionLogic logic, Object...values) {
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

		Restriction other = (Restriction) obj;
		if (id.equalsIgnoreCase(other.getId())) 
			return true;

		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Field:").append(this.field).
			append(" Restriction Type:").append(this.type).append(" ").
			append("Restriction Nullable:").append(this.nullable).append(" ").
			append(" Restriction Logic:").append(this.logic).append(" ").
			append(" Restriction Value:");

		for (Object v : this.values)
			sb.append(v.toString()).append(" ");

		return sb.toString();
		
	}

	protected String generateId() {
		StringBuilder str = new StringBuilder().
				append(this.field).append("-").
				append(this.nullable.toString().toLowerCase()).append("-").
				append(this.type.toLowerCase()).append("-").
				append(this.logic);

		return str.toString();
	}
}
