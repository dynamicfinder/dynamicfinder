package org.xsalefter.finder4j.test;

import org.junit.Assert;
import org.junit.Test;
import org.xsalefter.finder4j.RestrictionType;

public class RestrictionTypeTest {

	@Test
	public void of() {
		Assert.assertEquals(RestrictionType.of("equal"), RestrictionType.EQUAL);
		Assert.assertEquals(RestrictionType.of("greater"), RestrictionType.GREATER);
		Assert.assertEquals(RestrictionType.of("greater_equal"), RestrictionType.GREATER_EQUAL);
		Assert.assertEquals(RestrictionType.of("lesser"), RestrictionType.LESSER);
		Assert.assertEquals(RestrictionType.of("lesser_equal"), RestrictionType.LESSER_EQUAL);
		Assert.assertEquals(RestrictionType.of("like"), RestrictionType.LIKE);
		Assert.assertEquals(RestrictionType.of("like_with_prefix"), RestrictionType.LIKE_WITH_PREFIX);
		Assert.assertEquals(RestrictionType.of("like_with_postfix"), RestrictionType.LIKE_WITH_POSTFIX);
		Assert.assertEquals(RestrictionType.of("in"), RestrictionType.IN);
		Assert.assertEquals(RestrictionType.of("not_in"), RestrictionType.NOT_IN);
		Assert.assertEquals(RestrictionType.of("between"), RestrictionType.BETWEEN);
	}
}
