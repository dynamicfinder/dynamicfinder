package org.dynamicfinder.test.unit.jpa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.dynamicfinder.Nullable;
import org.dynamicfinder.QueryBuilder;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionLogic;
import org.dynamicfinder.RestrictionType;
import org.dynamicfinder.jpa.JpaQueryBuilder;
import org.dynamicfinder.jpa.JpaRestriction;
import org.dynamicfinder.test._entity.Person;
import org.dynamicfinder.test._entity.Person.Gender;
import org.dynamicfinder.test._specs.NullableAndRestrictionLogicSpecs;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaQueryBuilderWhereLikeWithPrefixTest 
implements NullableAndRestrictionLogicSpecs {

	private static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderWhereLikeWithPrefixTest.class);

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private QueryBuilder personQueryBuilder = new JpaQueryBuilder(Person.class);


	@Test
	@Override
	public void nullableDiscardWithOR() {
		logger.debug("#nullableDiscardWithOR()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, RestrictionLogic.OR, "xsalefter"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, RestrictionLogic.OR, birth));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name like concat(?1, '%') or " +
				"person.birthDate like concat(?2, '%')";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getActualRestrictions().size());
	}


	@Test
	@Override
	public void nullableDiscardWithAND() {
		logger.debug("#nullableDiscardWithAND()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, RestrictionLogic.AND, "xsalefter"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, RestrictionLogic.AND, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, RestrictionLogic.AND));

		personQueryBuilder.select("name", "birthDate", "gender");
		personQueryBuilder.where(restrictions);

		final String actual = personQueryBuilder.getQueryString();
		final String expected = "select person.name, person.birthDate, person.gender from " +
				"Person person where person.name like concat(?1, '%') and " +
				"person.birthDate like concat(?2, '%')";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getActualRestrictions().size());
	}


	@Test
	@Override
	public void nullableKeepWithOR() {
		logger.debug("#nullableKeepWithOR()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LIKE_WITH_PREFIX, Nullable.KEEP, RestrictionLogic.OR, birth));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.birthDate like concat(?1, '%')";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(1, personQueryBuilder.getActualRestrictions().size());
	}

	@Test
	public void nullableKeepWithORWithException() {
		logger.debug("#nullableKeepWithOR()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		this.expectedException.expect(IllegalStateException.class);
		this.expectedException.expectMessage("'like' restriction doesn't support null value.");

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, Nullable.KEEP, RestrictionLogic.OR));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LIKE_WITH_PREFIX, Nullable.KEEP, RestrictionLogic.OR, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LIKE_WITH_PREFIX, Nullable.KEEP, RestrictionLogic.OR));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name is null or " +
				"person.birthDate = ?1 or " +
				"person.gender is null";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(1, personQueryBuilder.getActualRestrictions().size());
	}


	@Test
	@Override
	public void nullableKeepWithAND() {
		logger.debug("#nullableKeepWithAND()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, Nullable.KEEP, "xsalefter"));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LIKE_WITH_PREFIX, Nullable.KEEP, Gender.Male));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name like concat(?1, '%') and " +
				"person.gender like concat(?2, '%')";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getActualRestrictions().size());
	}


	@Test
	@Override
	public void combinedNullableAndLogic() {
		logger.debug("#combinedNullableAndLogic()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, RestrictionLogic.OR, "xsalefter"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, birth));
		restrictions.add(new JpaRestriction("hobby", RestrictionType.LIKE_WITH_PREFIX, Nullable.DISCARD, "coding"));

		personQueryBuilder.select("name", "birthDate", "gender");
		personQueryBuilder.where(restrictions);

		final String actual = personQueryBuilder.getQueryString();
		final String expected = "select person.name, person.birthDate, person.gender from " +
				"Person person where person.name like concat(?1, '%') or " + 
				"person.birthDate like concat(?2, '%') and " +
				"person.hobby like concat(?3, '%')";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(3, personQueryBuilder.getActualRestrictions().size());
	}


	@Test
	public void doesNotProduceAnyRestriction() {
		logger.debug("#doesNotProduceAnyRestriction()");

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LIKE_WITH_PREFIX, RestrictionLogic.OR));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LIKE));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LIKE_WITH_PREFIX, RestrictionLogic.OR));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LIKE));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(0, personQueryBuilder.getActualRestrictions().size());
	}
}
