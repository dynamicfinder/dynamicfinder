package org.dynamicfinder.test.unit.jpa;

import org.dynamicfinder.QueryBuilder;
import org.dynamicfinder.jpa.AbstractJpaQueryBuilder;
import org.dynamicfinder.jpa.JpaQueryBuilder;
import org.dynamicfinder.test._entity.City;
import org.dynamicfinder.test._entity.Person;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaQueryBuilderTest {

	private static Logger logger = LoggerFactory.getLogger(JpaQueryBuilderTest.class);

	@Test
	public void constructor() {
		logger.debug("#constructor()");

		QueryBuilder queryBuilder = new JpaQueryBuilder(Person.class);

		Assert.assertNotNull(queryBuilder);
		Assert.assertNotNull(queryBuilder.getActualRestrictions());
		Assert.assertEquals(0, queryBuilder.getActualRestrictions().size());
		Assert.assertEquals(queryBuilder.getQueryString().trim(), "select person from Person person");
	}


	/**
	 * Test whether {@link AbstractJpaQueryBuilder#createEntityAndAliasNameFromClass(Class)} 
	 * is ok.
	 */
	@Test
	public void readNameMemberOnEntityTest() {
		QueryBuilder queryBuilder = new JpaQueryBuilder(City.class);
		Assert.assertEquals("select cityEntity from CityEntity cityEntity", queryBuilder.getQueryString());
	}


	@Test
	public void readNameMemberOnEntityForGetCountQueryStringTest() {
		QueryBuilder queryBuilder = new JpaQueryBuilder(City.class);
		Assert.assertEquals(
				"select count(cityEntity) from CityEntity cityEntity",
				queryBuilder.getCountQueryString()
		);
	}
}
