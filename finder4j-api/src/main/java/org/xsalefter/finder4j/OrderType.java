package org.xsalefter.finder4j;

public enum OrderType {
	ASC, DESC;

	/**
	 * Get the lower-case version of this {@link OrderType#toString()}.
	 * @return {@link String#toLowerCase()} version of {@link OrderType#toString()}.
	 */
	public String toLowerCase() {
		return this.toString().toLowerCase();
	}

	/**
	 * Get the upper-case version of this {@link OrderType#toString()}.
	 * @return {@link String#toLowerCase()} version of {@link OrderType#toString()}.
	 */
	public String toUpperCase() {
		return this.toString().toUpperCase();
	}
}
