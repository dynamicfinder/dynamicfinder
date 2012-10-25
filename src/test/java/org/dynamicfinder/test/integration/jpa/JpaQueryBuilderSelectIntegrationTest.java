package org.dynamicfinder.test.integration.jpa;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.dynamicfinder.QueryBuilder;
import org.dynamicfinder.jpa.JpaQueryBuilder;
import org.dynamicfinder.test._entity.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaQueryBuilderSelectIntegrationTest extends AbstractJpaIntegrationTest {

	private static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderSelectIntegrationTest.class);

	private QueryBuilder queryBuilder;

	@Before
	public void before() {
		this.queryBuilder = new JpaQueryBuilder(Employee.class);
	}

	@Test
	public void selectWithEmptyParameter() {
		logger.debug("#selectWithEmptyParameter()");

		super.entityManager.getTransaction().begin();
		TypedQuery<Employee> query = super.entityManager.
				createQuery(this.queryBuilder.select().getQueryString(), Employee.class);
		Assert.assertNotNull(query);
		List<Employee> result = query.getResultList();
		super.entityManager.getTransaction().commit();

		Assert.assertEquals(200, result.size());
		
	}

	@Test
	public void selectWithDefinedParameters() {
		logger.debug("#selectWithDefinedParameter()");

		queryBuilder.select("id", "firstName");
		String actual = queryBuilder.getQueryString();
		String expected = "select employee.id, employee.firstName from Employee employee";
		Assert.assertEquals(expected, actual);

		super.entityManager.getTransaction().begin();
		Query query = super.entityManager.createQuery(actual);
		Assert.assertNotNull(query);
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.getResultList();
		super.entityManager.getTransaction().commit();

		Assert.assertEquals(200, result.size());
	}

}
