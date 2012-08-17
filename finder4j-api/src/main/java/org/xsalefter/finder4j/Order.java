package org.xsalefter.finder4j;

import java.io.Serializable;

public final class Order implements Serializable {

	private static final long serialVersionUID = 4365168092798967256L;

	private String id;
	private String field;
	private OrderType type;

	public Order(String field, OrderType type) {
		this.id = this.generateId();
		this.field = field;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getField() {
		return field;
	}

	public OrderType getType() {
		return type;
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
		if (getClass() != obj.getClass()) return false;
		
		Order other = (Order) obj;
		return this.id.equals(other.getId());
	}

	private String generateId() {
		return new StringBuilder(field).append("-").
				append(this.type).
				toString();
	}
}
