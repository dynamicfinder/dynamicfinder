package org.dynamicfinder.test.integration.jpa;

import java.util.ArrayList;
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
 * Test {@link JpaRestriction} with {@link RestrictionType#EQUAL}.
 * @author xsalefter (xsalefer[at]gmail.com)
 */
public class JpaQueryBuilderWithLikeRestrictionTest extends AbstractJpaIntegrationTest {

	static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilderWithLikeRestrictionTest.class);

	private QueryBuilder queryBuilder;

	@Before
	public void before() {
		this.queryBuilder = new JpaQueryBuilder(Employee.class);
	}


	@Test
	public void restrictionForStringTest() {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(new JpaRestriction("firstName", RestrictionType.LIKE, "Geo"));

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

		Assert.assertEquals(2, result.size());
		Assert.assertEquals(2, count.intValue());
	}

}
