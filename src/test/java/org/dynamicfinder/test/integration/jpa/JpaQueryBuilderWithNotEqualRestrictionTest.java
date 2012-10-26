package org.dynamicfinder.test.integration.jpa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import junit.framework.Assert;

import org.dynamicfinder.QueryBuilder;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionType;
import org.dynamicfinder.jpa.JpaQueryBuilder;
import org.dynamicfinder.jpa.JpaRestriction;
import org.dynamicfinder.test._entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test {@link JpaRestriction} with {@link RestrictionType#NOT_EQUAL}.
 * @author xsalefter (xsalefer[at]gmail.com)
 */
public class JpaQueryBuilderWithNotEqualRestrictionTest extends AbstractJpaIntegrationTest {

	static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderWithNotEqualRestrictionTest.class);

	private QueryBuilder queryBuilder;

	@Before
	public void before() {
		this.queryBuilder = new JpaQueryBuilder(Employee.class);
	}


	@Test
	public void restrictionForStringTest() {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("firstName", RestrictionType.NOT_EQUAL, "Georgi"));
		restrictions.add(new JpaRestriction("lastName", RestrictionType.NOT_EQUAL, "Facello"));

		queryBuilder.where(restrictions);

		super.entityManager.getTransaction().begin();
		TypedQuery<Employee> query = super.entityManager.createQuery(queryBuilder.getQueryString(), Employee.class);
		TypedQuery<Long> countQuery = super.entityManager.createQuery(queryBuilder.getCountQueryString(), Long.class);
		Map<Integer, Restriction> actualRestriction = queryBuilder.getActualRestrictions();

		for (Integer parameter : actualRestriction.keySet()) {
			query.setParameter(parameter, actualRestriction.get(parameter).getValue());
			countQuery.setParameter(parameter, actualRestriction.get(parameter).getValue());
		}

		List<Employee> result = query.getResultList();
		Long count = countQuery.getSingleResult();
		super.entityManager.getTransaction().commit();

		Assert.assertEquals(199, result.size());
		Assert.assertEquals(199, count.intValue());
		Assert.assertFalse("Georgi".equals(result.iterator().next().getFirstName()));
	}


	@Test
	public void restrictionForEnumTest() {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("gender", RestrictionType.NOT_EQUAL, Employee.Gender.M));

		queryBuilder.where(restrictions);

		super.entityManager.getTransaction().begin();
		TypedQuery<Employee> query = super.entityManager.createQuery(queryBuilder.getQueryString(), Employee.class);
		TypedQuery<Long> countQuery = super.entityManager.createQuery(queryBuilder.getCountQueryString(), Long.class);
		Map<Integer, Restriction> actualRestriction = queryBuilder.getActualRestrictions();

		for (Integer parameter : actualRestriction.keySet()) {
			query.setParameter(parameter, actualRestriction.get(parameter).getValue());
			countQuery.setParameter(parameter, actualRestriction.get(parameter).getValue());
		}

		List<Employee> result = query.getResultList();
		Long count = countQuery.getSingleResult();
		super.entityManager.getTransaction().commit();

		Assert.assertEquals(75, result.size());
		Assert.assertEquals(75, count.intValue());
		Assert.assertEquals(Employee.Gender.F, result.iterator().next().getGender());
	}


	@Test
	public void restrictionForDateTest() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = dateFormat.parse("1959-12-03");

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.NOT_EQUAL, birthDate));

		queryBuilder.where(restrictions);

		super.entityManager.getTransaction().begin();
		TypedQuery<Employee> query = super.entityManager.createQuery(queryBuilder.getQueryString(), Employee.class);
		TypedQuery<Long> countQuery = super.entityManager.createQuery(queryBuilder.getCountQueryString(), Long.class);
		Map<Integer, Restriction> actualRestriction = queryBuilder.getActualRestrictions();

		for (Integer parameter : actualRestriction.keySet()) {
			query.setParameter(parameter, actualRestriction.get(parameter).getValue());
			countQuery.setParameter(parameter, actualRestriction.get(parameter).getValue());
		}

		List<Employee> result = query.getResultList();
		Long count = countQuery.getSingleResult();
		super.entityManager.getTransaction().commit();

		Assert.assertEquals(199, result.size());
		Assert.assertEquals(199, count.intValue());
		Assert.assertFalse(birthDate.equals(result.iterator().next().getBirthDate()));
	}
}
