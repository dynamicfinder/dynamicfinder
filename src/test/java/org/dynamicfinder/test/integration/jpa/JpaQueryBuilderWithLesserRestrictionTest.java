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
 * Test Test {@link JpaRestriction} with {@link RestrictionType#LESSER}.
 * @author xsalefter (xsalefer[at]gmail.com)
 */
public class JpaQueryBuilderWithLesserRestrictionTest extends AbstractJpaIntegrationTest {

	static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderWithLesserRestrictionTest.class);

	private QueryBuilder queryBuilder;

	@Before
	public void before() {
		this.queryBuilder = new JpaQueryBuilder(Employee.class);
	}


	@Test
	@org.junit.Ignore 
	// To test this in real world scenario, we need this: 
	// https://github.com/dynamicfinder/dynamicfinder/issues/4 
	// resolved
	public void restrictionForStringTest() {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("firstName", RestrictionType.LESSER, "Georgi"));
		restrictions.add(new JpaRestriction("lastName", RestrictionType.LESSER, "Facello"));

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

		Assert.assertEquals(1, result.size());
		Assert.assertEquals(1, count.intValue());
		Assert.assertEquals("Georgi", result.iterator().next().getFirstName());
	}


	@Test
	@org.junit.Ignore
	// To test this in real world scenario, we need this: 
	// https://github.com/dynamicfinder/dynamicfinder/issues/4 
	// resolved
	public void restrictionForEnumTest() {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("gender", RestrictionType.LESSER, Employee.Gender.M));

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

		Assert.assertEquals(125, result.size());
		Assert.assertEquals(125, count.intValue());
		Assert.assertEquals(Employee.Gender.M, result.iterator().next().getGender());
	}


	@Test
	public void restrictionForDateTest() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = dateFormat.parse("1959-12-03");

		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("birthDate", RestrictionType.LESSER, birthDate));

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

		Assert.assertEquals(120, result.size());
		Assert.assertEquals(120, count.intValue());
	}
}
