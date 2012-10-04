package org.xsalefter.finder4j.jpa.test.unit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class JpaQueryBuilderSelectTest extends JpaQueryBuilderTest {

	@Test
	public void selectWithEmptyParameter() {
		logger.debug("#selectWithEmptyParameter()");

		addressQueryBuilder.select();
		Assert.assertEquals(addressQueryBuilder.getQueryString(), "select address from Address address");

		cityQueryBuilder.select();
		Assert.assertEquals(cityQueryBuilder.getQueryString(), "select city from City city");
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
		expected = "select person.addresses from Person person";
		Assert.assertEquals(expected, actual);
	}

}
