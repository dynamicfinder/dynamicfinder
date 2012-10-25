package org.dynamicfinder.exp;

import org.dynamicfinder.Restriction;

public interface RestrictionExpression {

	RestrictionExpression equal(Object value);
	RestrictionExpression notEqual(Object value);
	RestrictionExpression greaterThan(Object value);
	RestrictionExpression greaterThanEqual(Object value);
	RestrictionExpression lesserThan(Object value);
	RestrictionExpression like(String value);
	RestrictionExpression startWith(String value);
	RestrictionExpression endWith(String value);
	RestrictionExpression keepNull();
	Restriction and();
	Restriction or();

}
