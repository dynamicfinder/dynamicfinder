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
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for 'where' clause in general.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaQueryBuilderWhereTest {

	private static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderWhereTest.class);

	@Test
	public void shouldSupportInvokeMethodsInMultipleTimes() {
		logger.debug("#shouldSupportInvokeMethodsInMultipleTimes()");

		QueryBuilder queryBuilder = new JpaQueryBuilder(Person.class);
		queryBuilder = queryBuilder.where(new JpaRestriction("name", "xsalefter"));
		queryBuilder = queryBuilder.where(new JpaRestriction("hobby", "reading"));
		final String queryString = queryBuilder.getQueryString();

		Assert.assertEquals(2, queryBuilder.getActualRestrictions().size());
		Assert.assertEquals("select person from Person person where person.name = ?1 and person.hobby = ?2", 
				queryString);
	}

	/**
	 * Call <code>getQueryString()</code> first, then <code>getCountQueryString()</code>
	 */
	@Test
	public void actualRestrictionSizeIsNotChangedAferCountQuery() {
		logger.debug("#actualRestrictionSizeIsNotChangedAferCountQuery()");

		QueryBuilder queryBuilder = new JpaQueryBuilder(Person.class);
		queryBuilder = queryBuilder.where(new JpaRestriction("name", "xsalefter"));
		queryBuilder = queryBuilder.where(new JpaRestriction("hobby", "reading"));
		// Invoke this..
		String queryString = queryBuilder.getQueryString();
		// .. and count restrictions ..
		final int actualRestrictionSize = queryBuilder.getActualRestrictions().size();
		// .. and then call 'count' ..
		String countQueryString = queryBuilder.getCountQueryString();
		// .. and count restrictions again.. 
		final int actualRestrictionSizeAfterCount = queryBuilder.getActualRestrictions().size();
		// ... the size is not changed.
		Assert.assertEquals(actualRestrictionSize, actualRestrictionSizeAfterCount);
		Assert.assertNotNull(queryString);
		Assert.assertNotNull(countQueryString);
	}

	/**
	 * Call <code>getCountQueryString()</code> first, then <code>getQueryString()</code>
	 */
	@Test
	public void actualRestrictionSizeIsNotChangedAferGetQuery() {
		logger.debug("#actualRestrictionSizeIsNotChangedAferGetQuery()");

		QueryBuilder queryBuilder = new JpaQueryBuilder(Person.class);
		queryBuilder = queryBuilder.where(new JpaRestriction("name", "xsalefter"));
		queryBuilder = queryBuilder.where(new JpaRestriction("hobby", "reading"));
		// Invoke this..
		String countQueryString = queryBuilder.getCountQueryString();
		// .. and count restrictions ..
		final int actualRestrictionSize = queryBuilder.getActualRestrictions().size();
		// .. and then call 'count' ..
		String queryString = queryBuilder.getQueryString();
		// .. and count restrictions again.. 
		final int actualRestrictionSizeAfterCount = queryBuilder.getActualRestrictions().size();
		// ... the size is not changed.
		Assert.assertEquals(actualRestrictionSize, actualRestrictionSizeAfterCount);
		Assert.assertNotNull(countQueryString);
		Assert.assertNotNull(queryString);
	}

	/**
	 * https://github.com/dynamicfinder/dynamicfinder/issues/7
	 */
	@Test
	  public void getCountQueryBuilderOnlyTest() {
	    logger.debug("#getCountQueryBuilderOnlyTest()");

	    QueryBuilder personQueryBuilder = new JpaQueryBuilder(Person.class);

	    Calendar birth = Calendar.getInstance();
	    birth.set(1980, 5, 15);

	    List<Restriction> restrictions = new ArrayList<Restriction>();
	    restrictions.add(new JpaRestriction("name", RestrictionType.EQUAL, Nullable.DISCARD, RestrictionLogic.OR, "xsalefter"));
	    restrictions.add(new JpaRestriction("birthDate", RestrictionType.EQUAL, Nullable.DISCARD, birth));
	    restrictions.add(new JpaRestriction("gender", RestrictionType.EQUAL, Nullable.DISCARD, RestrictionLogic.OR));
	    restrictions.add(new JpaRestriction("hobby", RestrictionType.EQUAL, Nullable.DISCARD, "coding"));

	    personQueryBuilder.select("name", "birthDate", "gender");
	    personQueryBuilder.where(restrictions);

	    final String actual = personQueryBuilder.getCountQueryString();
	    final String expected = "select count(person) from " +
	        "Person person where person.name = ?1 or " + 
	        "person.birthDate = ?2 and person.hobby = ?3";

	    Assert.assertEquals(expected, actual);
	    Assert.assertEquals(3, personQueryBuilder.getActualRestrictions().size());
	  }
}
