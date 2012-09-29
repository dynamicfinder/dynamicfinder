package org.xsalefter.finder4j;

/**
 * Define {@link RestrictionType} for {@link Restriction}.
 * @author xsalefter (xsalefter@gmail.com)
 */
public enum RestrictionType {
	/** Default value for this enum. Produce query language "<code>where field = :param"</code>. */
	EQUAL,

	/** Produce query language "<code>where field != :param"</code>. */
	NOT_EQUAL,

	/** Produce query language "<code>where field > :param"</code>. */
	GREATER,

	/** Produce query language "<code>where field >= :param"</code>. */
	GREATER_EQUAL,

	/** Produce query language "<code>where field < :param"</code>. */
	LESSER,

	/** Produce query language "<code>where field <= :param"</code>. */
	LESSER_EQUAL,

	/** Produce query language "<code>where field like concat('%', :param, '%")</code> */
	LIKE,

	/** Produce query language "<code>where field like concat(:param, '%")</code> */
	LIKE_WITH_PREFIX,

	/** Produce query language "<code>where field like concat('%', :param)</code> */
	LIKE_WITH_POSTFIX,

	/** Produce query language "<code>where field in :param</code> */
	IN,

	/** Produce query language "<code>where field not in :param</code> */
	NOT_IN,

	/** Produce query language "<code>where field between :param1 and :param2</code> */
	BETWEEN;

	/**
	 * Get the lower-case version of this {@link RestrictionType#toString()}.
	 * @return {@link String#toLowerCase()} version of {@link RestrictionType#toString()}.
	 */
	public String toLowerCase() {
		return this.toString().toLowerCase();
	}

	/**
	 * Get the upper-case version of this {@link RestrictionType#toString()}.
	 * @return {@link String#toLowerCase()} version of {@link RestrictionType#toString()}.
	 */
	public String toUpperCase() {
		return this.toString().toUpperCase();
	}
}
