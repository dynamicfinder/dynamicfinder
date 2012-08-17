package org.xsalefter.finder4j;

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
	 * Do the same thing like {@link Enum#valueOf(String)} and set 
	 * restrictionString parameter to upper case.
	 * @param restrictionString to parse as {@link RestrictionType}.
	 * @return {@link RestrictionType} instance.
	 */
	public static final RestrictionType of(final String restrictionString) {
		return RestrictionType.valueOf(restrictionString.toUpperCase());
	}

	/**
	 * Get the to lower version of this {@link Restriction#toString()}.
	 * @return {@link String#toLowerCase()}.
	 */
	public String toLowerCase() {
		return this.toString().toLowerCase();
	}
}
