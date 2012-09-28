package org.xsalefter.finder4j;

import java.io.Serializable;

/**
 * Define data structure for query restriction.
 * @author xsalefter (xsalefter@gmail.com)
 */
public interface Restriction extends Serializable {

	String getField();
	RestrictionType getRestrictionType();
	Nullable getNullable();
	Object[] getRestrictionValues();
	RestrictionLogic getRestrictionLogic();
}
