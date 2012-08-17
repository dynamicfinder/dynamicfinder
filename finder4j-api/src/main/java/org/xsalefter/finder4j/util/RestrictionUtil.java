package org.xsalefter.finder4j.util;

import java.util.Arrays;
import java.util.List;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.OrderType;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionLogic;
import org.xsalefter.finder4j.RestrictionType;

public class RestrictionUtil {

	private String field;
	protected RestrictionUtil(final String field) { this.field = field; }

	public static final Object[] NULL = new Object[] { null };

	public static final RestrictionType EQUAL = RestrictionType.EQUAL;
	public static final RestrictionType NOT_EQUAL = RestrictionType.NOT_EQUAL;
	public static final RestrictionType GREATER = RestrictionType.GREATER;
	public static final RestrictionType GREATER_EQ = RestrictionType.GREATER_EQUAL;
	public static final RestrictionType LESSER = RestrictionType.LESSER;
	public static final RestrictionType LESSER_EQ = RestrictionType.LESSER_EQUAL;
	public static final RestrictionType LIKE = RestrictionType.LIKE;
	public static final RestrictionType LIKE_PRE = RestrictionType.LIKE_WITH_PREFIX;
	public static final RestrictionType LIKE_POST = RestrictionType.LIKE_WITH_POSTFIX;
	public static final RestrictionType IN = RestrictionType.IN;
	public static final RestrictionType NOT_IN = RestrictionType.NOT_IN;
	public static final RestrictionType BETWEEN = RestrictionType.BETWEEN;

	public static final RestrictionLogic AND = RestrictionLogic.AND;
	public static final RestrictionLogic OR = RestrictionLogic.OR;

	public static final Nullable DISCARD = Nullable.DISCARD;
	public static final Nullable KEEP = Nullable.KEEP;

	public static final OrderType ASC = OrderType.ASC;
	public static final OrderType DESC = OrderType.DESC;

	public static Restriction $$(String field, Nullable nullable, Object...values) {
		return new Restriction(field, nullable, values);
	}

	public static <T> List<T> $list(T...arrays) {
		return Arrays.asList(arrays);
	}

	public static Restriction $$(String field, RestrictionType restrictionType, Nullable nullable, Object...values) {
		return new Restriction(field, restrictionType, nullable, values);
	}

	public static RestrictionUtil $$(final String field) {
		return new RestrictionUtil(field);
	}

	public Restriction eq(Nullable nullable, Object...values) {
		return new Restriction(field, nullable, values);
	}

	public Restriction eq(RestrictionLogic logic, Object...values) {
		return new Restriction(field, RestrictionType.EQUAL, logic, values);
	}

	static {
		$list(
			$$("name").eq(DISCARD, "ganteng"),
			$$("gender").eq(DISCARD, "male"),
			$$("birthDate", DISCARD, NULL)
		);
		$$("name").eq(AND);
	}
}
