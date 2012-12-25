package org.dynamicfinder.test.unit.jpa;

import org.dynamicfinder.QueryBuilder;
import org.dynamicfinder.jpa.JpaQueryBuilder;
import org.dynamicfinder.test._entity.Address;
import org.dynamicfinder.test._entity.City;
import org.dynamicfinder.test._entity.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaQueryBuilderSelectTest {

	private static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderSelectTest.class);

	private QueryBuilder personQueryBuilder;
	private QueryBuilder addressQueryBuilder;
	private QueryBuilder cityQueryBuilder;

	@Before
	public void before() {
		this.personQueryBuilder = new JpaQueryBuilder(Person.class);
		this.addressQueryBuilder = new JpaQueryBuilder(Address.class);
		this.cityQueryBuilder = new JpaQueryBuilder(City.class);
	}

	@Test
	public void selectWithEmptyParameter() {
		logger.debug("#selectWithEmptyParameter()");

		addressQueryBuilder.select();
		Assert.assertEquals(addressQueryBuilder.getQueryString(), "select address from Address address");

		cityQueryBuilder.select();
		Assert.assertEquals(cityQueryBuilder.getQueryString(), "select cityEntity from CityEntity cityEntity");
	}

	@Test
	public void selectWithDefinedParameters() {
		logger.debug("#selectWithDefinedParameter()");

		personQueryBuilder.select("id", "name");
		String actual = personQueryBuilder.getQueryString();
		String expected = "select person.id, person.name from Person person";
		Assert.assertEquals(expected, actual);

		personQueryBuilder.select("addresses");
		actual = personQueryBuilder.getQueryString();
		expected = "select person.id, person.name, person.addresses from Person person";
		Assert.assertEquals(expected, actual);
	}

}
