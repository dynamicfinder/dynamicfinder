package org.xsalefter.finder4j.jpa.test;

import org.junit.Assert;
import org.junit.Test;

public class JPAQueryBuilderSelectTest extends JPAQueryBuilderTest {

	@Test
	public void selectWithEmptyParameter() {
		logger.debug("#selectWithEmptyParameter()");

		addressQueryBuilder.select();
		Assert.assertEquals(addressQueryBuilder.toString().trim(), "select address from Address address");

		cityQueryBuilder.select();
		Assert.assertEquals(cityQueryBuilder.toString().trim(), "select city from City city");
	}


	@Test
	public void selectWithDefinedParameters() {
		logger.debug("#selectWithDefinedParameter()");

		personQueryBuilder.select("id", "name");
		String actual = personQueryBuilder.toString().trim();
		String expected = "select person.id, person.name from Person person";
		Assert.assertEquals(expected, actual);

		personQueryBuilder.select("addresses");
		actual = personQueryBuilder.toString().trim();
		expected = "select person.addresses from Person person";
		Assert.assertEquals(expected, actual);
	}

}
