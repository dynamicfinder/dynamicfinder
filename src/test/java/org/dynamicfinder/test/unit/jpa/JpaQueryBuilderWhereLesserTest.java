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
public class JpaQueryBuilderWhereLesserTest 
implements NullableAndRestrictionLogicSpecs {

	private static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderWhereLesserTest.class);

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
		restrictions.add(new JpaRestriction("name", RestrictionType.LESSER, Nullable.DISCARD, RestrictionLogic.OR, "some name"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LESSER, Nullable.DISCARD, RestrictionLogic.OR, birth));
		restrictions.add(new JpaRestriction("name", RestrictionType.LESSER, Nullable.DISCARD, RestrictionLogic.OR));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name < ?1 or person.birthDate < ?2";

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
		restrictions.add(new JpaRestriction("name", RestrictionType.LESSER, Nullable.DISCARD, "xsalefter"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LESSER, Nullable.DISCARD, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LESSER, Nullable.DISCARD, Gender.Male));

		personQueryBuilder.select("name", "birthDate", "gender");
		personQueryBuilder.where(restrictions);

		final String actual = personQueryBuilder.getQueryString();
		final String expected = "select person.name, person.birthDate, person.gender from " +
				"Person person where person.name < ?1 and " + 
				"person.birthDate < ?2 and person.gender < ?3";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(3, personQueryBuilder.getActualRestrictions().size());
	}

	@Test
	@Override
	public void nullableKeepWithOR() {
		logger.debug("#emptySelectWithNullableKeep()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LESSER, Nullable.KEEP, RestrictionLogic.OR, "some name"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LESSER, Nullable.KEEP, RestrictionLogic.OR, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LESSER, Nullable.KEEP, RestrictionLogic.OR, Gender.Female));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name < ?1 or person.birthDate < ?2 or person.gender < ?3";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(3, personQueryBuilder.getActualRestrictions().size());
	}


	@Test
	@Override
	public void nullableKeepWithAND() {
		logger.debug("#nullableKeepWithAND()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LESSER, Nullable.KEEP, "john"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LESSER, Nullable.KEEP, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LESSER, Nullable.KEEP, Gender.Male));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LESSER, Nullable.KEEP, Gender.Female));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name < ?1 and person.birthDate < ?2 and " +
				"person.gender < ?3 and person.gender < ?4";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(4, personQueryBuilder.getActualRestrictions().size());
	}


	@Test
	@Override
	public void combinedNullableAndLogic() {
		logger.debug("#combinedNullableAndLogic()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LESSER, Nullable.KEEP, "john"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LESSER, Nullable.KEEP, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LESSER, Nullable.KEEP, Gender.Male));
		restrictions.add(new JpaRestriction("gender", RestrictionType.LESSER, Nullable.KEEP, Gender.Female));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name < ?1 and person.birthDate < ?2 and " +
				"person.gender < ?3 and person.gender < ?4";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(4, personQueryBuilder.getActualRestrictions().size());
	}


	/**
	 * Should throw {@link UnsupportedOperationException} because add null value 
	 * to {@link Restriction} with {@link RestrictionType#LESSER} and 
	 * {@link Nullable#KEEP}.
	 */
	@Test
	public void nullableKeepThrowException() {
		logger.debug("#emptySelectWithNullableKeep()");

		this.expectedException.expect(UnsupportedOperationException.class);
		this.expectedException.expectMessage("Null value only for EQUAL or NOT_EQUAL.");

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.LESSER, Nullable.KEEP, RestrictionLogic.OR));
		restrictions.add(new JpaRestriction("hobby", RestrictionType.LESSER, Nullable.KEEP));

		personQueryBuilder.where(restrictions);
		/** This will throw an exception, which is come from RestrictionHandler. 
		    Just calling where is not working because we change the implementation. */
		personQueryBuilder.getQueryString();
	}


	@Test
	public void shouldDoesntHaveAnyRestriction() {
		logger.debug("#shouldDoesntHaveAnyRestriction()");

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.GREATER));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.GREATER));
		restrictions.add(new JpaRestriction("gender", RestrictionType.GREATER));
		restrictions.add(new JpaRestriction("hobby", RestrictionType.GREATER));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(0, personQueryBuilder.getActualRestrictions().size());
	}
}
