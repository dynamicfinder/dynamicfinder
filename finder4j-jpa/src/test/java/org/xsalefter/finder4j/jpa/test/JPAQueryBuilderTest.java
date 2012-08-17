package org.xsalefter.finder4j.jpa.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsalefter.finder4j.QueryBuilder;
import org.xsalefter.finder4j.jpa.JPAQueryBuilder;
import org.xsalefter.finder4j.jpa._entity.Address;
import org.xsalefter.finder4j.jpa._entity.City;
import org.xsalefter.finder4j.jpa._entity.Person;

public class JPAQueryBuilderTest {

	protected volatile Logger logger = LoggerFactory.getLogger(getClass());

	protected QueryBuilder personQueryBuilder;
	protected QueryBuilder addressQueryBuilder;
	protected QueryBuilder cityQueryBuilder;

	@Before
	public void before() {
		this.personQueryBuilder = new JPAQueryBuilder(Person.class);
		this.addressQueryBuilder = new JPAQueryBuilder(Address.class);
		this.cityQueryBuilder = new JPAQueryBuilder(City.class);
	}

	@Test
	public void constructor() {
		logger.debug("#constructor()");

		QueryBuilder queryBuilder = new JPAQueryBuilder(Person.class);

		Assert.assertNotNull(queryBuilder);
		Assert.assertNotNull(queryBuilder.getRestrictions());
		Assert.assertEquals(0, queryBuilder.getRestrictions().size());
		Assert.assertEquals(queryBuilder.toString().trim(), "from Person");
	}

}
