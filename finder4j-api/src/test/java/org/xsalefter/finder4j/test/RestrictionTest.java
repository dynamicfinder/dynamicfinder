package org.xsalefter.finder4j.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;

public class RestrictionTest {

	@Test
	public void testRestrictionWithSameField() {
		Restriction r1 = new Restriction("name", RestrictionType.EQUAL);
		Restriction r2 = new Restriction("name", RestrictionType.EQUAL);
		Restriction r3 = new Restriction("name", RestrictionType.GREATER);
		Restriction r4 = new Restriction("name", RestrictionType.LESSER_EQUAL);
		Restriction r5 = new Restriction("name", RestrictionType.LIKE);
		Restriction r6 = new Restriction("name", RestrictionType.LIKE_WITH_POSTFIX);

		Assert.assertTrue( r1.equals(r2) );
		Assert.assertTrue( r1.getId().equals(r2.getId()) );

		Assert.assertTrue( !r1.equals(r3) );
		Assert.assertTrue( !r1.equals(r4) );
		Assert.assertTrue( !r1.equals(r5) );
		Assert.assertTrue( !r1.equals(r6) );

		Assert.assertTrue( !r2.equals(r3) );
		Assert.assertTrue( !r2.equals(r4) );
		Assert.assertTrue( !r2.equals(r5) );
		Assert.assertTrue( !r2.equals(r6) );

		final Set<Restriction> list = new HashSet<Restriction>(
				Arrays.asList(new Restriction[] {r1, r2, r3, r4, r5, r6})
		);

		// should = 5.
		Assert.assertEquals(5, list.size());
	}
}
