package org.dynamicfinder.test.unit.jpa;

import junit.framework.Assert;

import org.dynamicfinder.Nullable;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionLogic;
import org.dynamicfinder.RestrictionType;
import org.dynamicfinder.exp.RestrictionExpression;
import org.dynamicfinder.jpa.JpaRestriction;
import org.dynamicfinder.jpa.JpaRestrictionExpression;
import org.junit.Test;
import static org.dynamicfinder.jpa.JpaRestrictionExpression.*;

/**
 * Test {@link RestrictionExpression} with {@link JpaRestrictionExpression}. 
 * Ignore stupid things like <code>$("name").lesserThan("value")</code>, because 
 * mostly this test for equality with actual {@link JpaRestriction}.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaRestrictionExpressionTest {

	@Test
	public void equalTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.EQUAL, "xsalefter");
		Restriction actual = $("name").equal("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.EQUAL, RestrictionLogic.OR, "xsalefter");
		actual = $("name").equal("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.EQUAL, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").equal("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void notEqualTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.NOT_EQUAL, "xsalefter");
		Restriction actual = $("name").notEqual("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.NOT_EQUAL, RestrictionLogic.OR, "xsalefter");
		actual = $("name").notEqual("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").notEqual("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void greaterThanTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.GREATER, "xsalefter");
		Restriction actual = $("name").greaterThan("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.GREATER, RestrictionLogic.OR, "xsalefter");
		actual = $("name").greaterThan("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.GREATER, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").greaterThan("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void greaterThanEqualTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.GREATER_EQUAL, "xsalefter");
		Restriction actual = $("name").greaterThanEqual("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.GREATER_EQUAL, RestrictionLogic.OR, "xsalefter");
		actual = $("name").greaterThanEqual("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.GREATER_EQUAL, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").greaterThanEqual("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void lesserThanTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.LESSER, "xsalefter");
		Restriction actual = $("name").lesserThan("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LESSER, RestrictionLogic.OR, "xsalefter");
		actual = $("name").lesserThan("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LESSER, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").lesserThan("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void lesserThanEqualTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.LESSER_EQUAL, "xsalefter");
		Restriction actual = $("name").lesserThanEqual("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LESSER_EQUAL, RestrictionLogic.OR, "xsalefter");
		actual = $("name").lesserThanEqual("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LESSER_EQUAL, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").lesserThanEqual("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void likeTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.LIKE, "xsalefter");
		Restriction actual = $("name").like("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LIKE, RestrictionLogic.OR, "xsalefter");
		actual = $("name").like("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LIKE, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").like("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void startWithTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, "xsalefter");
		Restriction actual = $("name").startWith("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, RestrictionLogic.OR, "xsalefter");
		actual = $("name").startWith("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").startWith("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void endWithTest() {
		Restriction expected = new JpaRestriction("name", RestrictionType.LIKE_WITH_POSTFIX, "xsalefter");
		Restriction actual = $("name").endWith("xsalefter").and();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LIKE_WITH_POSTFIX, RestrictionLogic.OR, "xsalefter");
		actual = $("name").endWith("xsalefter").or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);

		expected = new JpaRestriction("name", RestrictionType.LIKE_WITH_POSTFIX, Nullable.KEEP, RestrictionLogic.OR, "xsalefter");
		actual = $("name").endWith("xsalefter").keepNull().or();
		Assert.assertEquals(expected.getField(), actual.getField());
		Assert.assertEquals(expected.getRestrictionType(), actual.getRestrictionType());
		Assert.assertEquals(expected.getNullable(), actual.getNullable());
		Assert.assertEquals(expected.getRestrictionLogic(), actual.getRestrictionLogic());
		Assert.assertEquals(expected, actual);
	}
}
