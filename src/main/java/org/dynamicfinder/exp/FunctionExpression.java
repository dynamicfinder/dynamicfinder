package org.dynamicfinder.exp;

public interface FunctionExpression {
	String date(Object value);
	String time(Object value);
	String concat(Object...values);
}
