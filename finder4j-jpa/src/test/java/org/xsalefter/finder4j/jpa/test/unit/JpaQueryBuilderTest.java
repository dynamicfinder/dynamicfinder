package org.xsalefter.finder4j.jpa.test.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.jpa.JpaQueryBuilder;
import org.xsalefter.finder4j.jpa._entity.Address;
import org.xsalefter.finder4j.jpa._entity.City;
import org.xsalefter.finder4j.jpa._entity.Person;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class JpaQueryBuilderTest {

	protected volatile Logger logger = LoggerFactory.getLogger(getClass());

	protected QueryBuilder personQueryBuilder;
	protected QueryBuilder addressQueryBuilder;
	protected QueryBuilder cityQueryBuilder;

	@Before
	public void before() {
		this.personQueryBuilder = new JpaQueryBuilder(Person.class);
		this.addressQueryBuilder = new JpaQueryBuilder(Address.class);
		this.cityQueryBuilder = new JpaQueryBuilder(City.class);
	}

	@Test
	public void constructor() {
		logger.debug("#constructor()");

		QueryBuilder queryBuilder = new JpaQueryBuilder(Person.class);

		Assert.assertNotNull(queryBuilder);
		Assert.assertNotNull(queryBuilder.getActualRestrictions());
		Assert.assertEquals(0, queryBuilder.getActualRestrictions().size());
		Assert.assertEquals(queryBuilder.getQueryString().trim(), "from Person");
	}

}
