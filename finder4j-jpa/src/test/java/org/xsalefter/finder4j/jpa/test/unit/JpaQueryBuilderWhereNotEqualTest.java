package org.xsalefter.finder4j.jpa.test.unit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionLogic;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.jpa.JpaQueryBuilder;
import org.xsalefter.finder4j.jpa.JpaRestriction;
import org.xsalefter.finder4j.jpa._entity.Person;
import org.xsalefter.finder4j.jpa._entity.Person.Gender;
import org.xsalefter.finder4j.jpa._specs.NullableRestrictionSpecs;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class JpaQueryBuilderWhereNotEqualTest
implements NullableRestrictionSpecs {

	private static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderWhereNotEqualTest.class);

	private QueryBuilder personQueryBuilder = new JpaQueryBuilder(Person.class); 

	@Test
	@Override
	public void emptySelectWithNullableDiscard() {
		logger.debug("#emptySelectWithNullableDiscard()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.DISCARD, "xsalefter"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.DISCARD, birth));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name != ?1 and " +
				"person.birthDate != ?2";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getRestrictions().size());
	}


	@Test
	@Override
	public void definedSelectWithNullableDiscard() {
		logger.debug("#definedSelectWithNullableDiscard()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.DISCARD, RestrictionLogic.OR, "xsalefter"));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.DISCARD, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.DISCARD));

		personQueryBuilder.select("name", "birthDate", "gender");
		personQueryBuilder.where(restrictions);

		final String actual = personQueryBuilder.getQueryString();
		final String expected = "select person.name, person.birthDate, person.gender from " +
				"Person person where person.name != ?1 or person.birthDate != ?2";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getRestrictions().size());
	}

	@Test
	@Override
	public void emptySelectWithNullableKeep() {
		logger.debug("#emptySelectWithNullableKeep()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.KEEP, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name is null or person.birthDate != ?1 and " +
				"person.gender is null";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(1, personQueryBuilder.getRestrictions().size());
	}

	@Test
	@Override
	public void definedSelectWithNullableKeep() {
		logger.debug("#definedSelectWithNullableKeep()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("name", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR));
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.KEEP, birth));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR, Gender.Male));
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString();
		String expected = "select person from Person person where " +
				"person.name is null or person.birthDate != ?1 and " +
				"person.gender != ?2 or person.gender is null";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getRestrictions().size());
	}

}
