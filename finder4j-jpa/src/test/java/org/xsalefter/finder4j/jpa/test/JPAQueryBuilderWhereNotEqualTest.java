package org.xsalefter.finder4j.jpa.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionLogic;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.jpa._entity.Person.Gender;
import org.xsalefter.finder4j.jpa._specs.RestrictionTypeQueryBuilderSpecification;

public class JPAQueryBuilderWhereNotEqualTest extends JPAQueryBuilderTest 
implements RestrictionTypeQueryBuilderSpecification {

	@Test
	@Override
	public void emptySelect_NullableDiscard() {
		logger.debug("#withEmptySelect_NullableDiscard()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new Restriction("name", RestrictionType.NOT_EQUAL, Nullable.DISCARD, "xsalefter"));
		restrictions.add(new Restriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.DISCARD, birth));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString().trim();
		String expected = "select person from Person person where " +
				"person.name != :name_discard_not_equal_and and " +
				"person.birthDate != :birthDate_discard_not_equal_and";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getRestrictions().size());
	}


	@Test
	@Override
	public void definedSelect_NullableDiscard() {
		logger.debug("#withDefinedSelect_NullableDiscard()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new Restriction("name", RestrictionType.NOT_EQUAL, Nullable.DISCARD, RestrictionLogic.OR, "xsalefter"));
		restrictions.add(new Restriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.DISCARD, birth));
		restrictions.add(new Restriction("gender", RestrictionType.NOT_EQUAL, Nullable.DISCARD));

		personQueryBuilder.select("name", "birthDate", "gender");
		personQueryBuilder.where(restrictions);

		final String actual = personQueryBuilder.getQueryString().trim();
		final String expected = "select person.name, person.birthDate, person.gender from " +
				"Person person where person.name != :name_discard_not_equal_or or " + 
				"person.birthDate != :birthDate_discard_not_equal_and";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getRestrictions().size());
	}

	@Test
	@Override
	public void emptySelect_NullableKeep() {
		logger.debug("#emptySelect_NullableKeep()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new Restriction("name", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR));
		restrictions.add(new Restriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.KEEP, birth));
		restrictions.add(new Restriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString().trim();
		String expected = "select person from Person person where " +
				"person.name is null or " +
				"person.birthDate != :birthDate_keep_not_equal_and and " +
				"person.gender is null";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(1, personQueryBuilder.getRestrictions().size());
	}

	@Test
	@Override
	public void definedSelect_NullableKeep() {
		logger.debug("#emptySelect_NullableKeep()");

		Calendar birth = Calendar.getInstance();
		birth.set(1980, 5, 15);

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new Restriction("name", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR));
		restrictions.add(new Restriction("birthDate", RestrictionType.NOT_EQUAL, Nullable.KEEP, birth));
		restrictions.add(new Restriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP, RestrictionLogic.OR, Gender.Male));
		restrictions.add(new Restriction("gender", RestrictionType.NOT_EQUAL, Nullable.KEEP));

		personQueryBuilder.where(restrictions);

		String actual = personQueryBuilder.getQueryString().trim();
		String expected = "select person from Person person where " +
				"person.name is null or " +
				"person.birthDate != :birthDate_keep_not_equal_and and " +
				"person.gender != :gender_keep_not_equal_or or " +
				"person.gender is null";

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(2, personQueryBuilder.getRestrictions().size());
	}

}
