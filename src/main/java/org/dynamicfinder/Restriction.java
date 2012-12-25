package org.dynamicfinder;

import java.io.Serializable;

/**
 * Define data structure for query restriction.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public interface Restriction extends Serializable {

	/**
	 * Get restriction parameter.
	 * @return {@link Integer} parameter.
	 */
	Integer getParameter();

	/**
	 * Get field name.
	 * @return {@link String} name.
	 */
	String getField();

	/**
	 * Get {@link RestrictionType}. 
	 * @return {@link RestrictionType} instance.
	 */
	RestrictionType getRestrictionType();

	/**
	 * Get actual {@link Restriction} value. This value could be anything. One 
	 * thing to consider is that implementation may assign <code>args</code> 
	 * parameter to this value, so implementation of this method should be able
	 * to handle this issue.
	 * @return {@link Restriction} value as an {@link Object}.
	 */
	Object getValue();

	/**
	 * Get {@link Restriction} {@link Nullable} type.
	 * @return {@link Nullable} type.
	 */
	Nullable getNullable();

	/**
	 * Get {@link RestrictionLogic} type to append to next restriction.
	 * @return {@link RestrictionLogic} enumeration type.
	 */
	RestrictionLogic getRestrictionLogic();
}
