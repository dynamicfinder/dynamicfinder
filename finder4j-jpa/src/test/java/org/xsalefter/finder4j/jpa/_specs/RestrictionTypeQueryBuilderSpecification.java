package org.xsalefter.finder4j.jpa._specs;

import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.RestrictionType;

/**
 * Define set of test should be performed for each {@link RestrictionType} 
 * in {@link QueryBuilder}.
 * @author xsalefter (xsalefter@gmail.com)
 *
 */
public interface RestrictionTypeQueryBuilderSpecification {

	void emptySelect_NullableDiscard();
	void definedSelect_NullableDiscard();
	void emptySelect_NullableKeep();
	void definedSelect_NullableKeep();
}
