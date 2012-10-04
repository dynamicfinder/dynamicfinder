package org.xsalefter.finder4j.jpa._specs;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;

/**
 * Define set of test should be performed for {@link Nullable}
 * in {@link QueryBuilder}.
 * @author xsalefter (xsalefter@gmail.com)
 *
 */
public interface NullableRestrictionSpecs {

	/**
	 * Should perform test with call {@link QueryBuilder#select(String...)} with 
	 * empty parameter (or don't invoke at all) and add {@link Nullable#DISCARD} 
	 * within the {@link Restriction}.
	 */
	void emptySelectWithNullableDiscard();

	/**
	 * Should perform test with call {@link QueryBuilder#select(String...)} with 
	 * defined parameter and add {@link Nullable#DISCARD} within the 
	 * {@link Restriction}.
	 */
	void definedSelectWithNullableDiscard();

	/**
	 * Should perform test with call {@link QueryBuilder#select(String...)} with 
	 * empty parameter (or don't invoke at all) and add {@link Nullable#KEEP} 
	 * within the {@link Restriction}.
	 */
	void emptySelectWithNullableKeep();

	/**
	 * Should perform test with call {@link QueryBuilder#select(String...)} with 
	 * defined parameter and add {@link Nullable#KEEP} within the 
	 * {@link Restriction}.
	 */
	void definedSelectWithNullableKeep();
}
