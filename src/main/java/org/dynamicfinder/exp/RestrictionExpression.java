package org.dynamicfinder.exp;

import org.dynamicfinder.Nullable;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionLogic;
import org.dynamicfinder.RestrictionType;

/**
 * Define set of expression to help create {@link Restriction} easily.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public interface RestrictionExpression {

	/**
	 * Will set {@link RestrictionType#EQUAL} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression equal(Object value);

	/**
	 * Will set {@link RestrictionType#NOT_EQUAL} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression notEqual(Object value);

	/**
	 * Will set {@link RestrictionType#GREATER} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression greaterThan(Object value);

	/**
	 * Will set {@link RestrictionType#GREATER_EQUAL} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression greaterThanEqual(Object value);

	/**
	 * Will set {@link RestrictionType#LESSER} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression lesserThan(Object value);

	/**
	 * Will set {@link RestrictionType#LESSER_EQUAL} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression lesserThanEqual(Object value);

	/**
	 * Will set {@link RestrictionType#LIKE} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression like(String value);

	/**
	 * Will set {@link RestrictionType#LIKE_WITH_PREFIX} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression startWith(String value);

	/**
	 * Will set {@link RestrictionType#LIKE_WITH_POSTFIX} and {@link Nullable#DISCARD}.
	 * @param value to set {@link Restriction#getValue()}.
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression endWith(String value);

	/**
	 * Invoke this method should change {@link Restriction#getNullable()} value 
	 * with {@link Nullable#KEEP}. 
	 * @return Current {@link RestrictionExpression} instance.
	 */
	RestrictionExpression keepNull();

	/**
	 * Invoke this method would get {@link Restriction} instance with 
	 * {@link RestrictionLogic#AND}. 
	 * @return {@link Restriction} instance.
	 */
	Restriction and();

	/**
	 * Invoke this method would get {@link Restriction} instance with 
	 * {@link RestrictionLogic#OR}. 
	 * @return {@link Restriction} instance.
	 */
	Restriction or();

}
