package org.dynamicfinder.test._specs;

/**
 * Define set of test should be performed for {@link Nullable} and 
 * {@link RestrictionLogic} in {@link QueryBuilder}.
 * @author xsalefter (xsalefter[at]gmail.com)
 *
 */
public interface NullableAndRestrictionLogicSpecs {

	/**
	 * Should perform test with {@link Nullable#DISCARD} and {@link RestrictionLogic#OR}.
	 */
	void nullableDiscardWithOR();

	/**
	 * Should perform test with {@link Nullable#DISCARD} and {@link RestrictionLogic#AND}.
	 */
	void nullableDiscardWithAND();

	/**
	 * Should perform test with {@link Nullable#KEEP} and {@link RestrictionLogic#OR}.
	 */
	void nullableKeepWithOR();

	/**
	 * Should perform test with {@link Nullable#KEEP} and {@link RestrictionLogic#AND}.
	 */
	void nullableKeepWithAND();

	/**
	 * Should perform test with combined {@link Nullable} and {@link RestrictionLogic}.
	 */
	void combinedNullableAndLogic();
}
