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

	protected QueryBuilder<String> personQueryBuilder;
	protected QueryBuilder<String> addressQueryBuilder;
	protected QueryBuilder<String> cityQueryBuilder;

	@Before
	public void before() {
		this.personQueryBuilder = new JPAQueryBuilder(Person.class);
		this.addressQueryBuilder = new JPAQueryBuilder(Address.class);
		this.cityQueryBuilder = new JPAQueryBuilder(City.class);
	}

	@Test
	public void constructor() {
		logger.debug("#constructor()");

		Assert.assertNotNull(personQueryBuilder);
		Assert.assertNotNull(personQueryBuilder.getRestrictions());
		Assert.assertEquals(0, personQueryBuilder.getRestrictions().size());
		Assert.assertEquals(personQueryBuilder.getQueryString().trim(), "from Person");
	}

}
