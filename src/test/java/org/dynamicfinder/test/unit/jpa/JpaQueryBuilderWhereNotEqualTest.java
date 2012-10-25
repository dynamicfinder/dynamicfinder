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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaQueryBuilderWhereNotEqualTest
implements NullableAndRestrictionLogicSpecs {

	private static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderWhereNotEqualTest.class);

	private QueryBuilder personQueryBuilder = new JpaQueryBuilder(Person.class); 


	@Test
	@Override
	public void nullableDiscardWithOR() {
		logger.debug("#nullableDiscardWithOR()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.DISCARD, RestrictionLogic.OR, "xsalefter"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.DISCARD, RestrictionLogic.OR, birth));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name != ?1 or person.birthDate != ?2";

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
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.DISCARD, "xsalefter"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.DISCARD, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.DISCARD));

		personQueryBuilder.select("name", "birthDate", "gender");
		personQueryBuilder.where(restrictions);

		final String actual = personQueryBuilder.getQueryString();
		final String expected = "select person.name, person.birthDate, person.gender from " +
				"Person person where person.name != ?1 and person.birthDate != ?2";

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
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name is not null or person.birthDate != ?1 or " +
				"person.gender is not null";

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
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.KEEP));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.KEEP, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP, Gender.Female));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name is not null and person.birthDate != ?1 and " +
				"person.gender != ?2 and person.gender is not null";

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
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.KEEP));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.KEEP, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR, Gender.Female));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR));
		restrictions.add(new JpaRestriction("hobby", RestrictionType.NOT_EQUAL, Nullable.KEEP, "footbal"));
		restrictions.add(new JpaRestriction("hobby", RestrictionType.NOT_EQUAL, Nullable.KEEP, "games"));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name is not null and person.birthDate != ?1 and " +
				"person.gender != ?2 or person.gender is not null or " +
				"person.hobby != ?3 and person.hobby != ?4";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(4, personQueryBuilder.getActualRestrictions().size());
	}

}
