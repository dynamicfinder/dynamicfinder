package org.dynamicfinder.test.integration.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

public class AbstractJpaIntegrationTest {

	private static EntityManagerFactory emf;
	protected EntityManager entityManager;

	/**
	 * Only for testing. Do not follow this in real project!
	 */
	static {
		emf = Persistence.createEntityManagerFactory("dynamicfinder");
	}

	@Before
	public void setupEntityManager() {
		this.entityManager = emf.createEntityManager();
	}

	@After
	public void closeEntityManager() {
		if (this.entityManager.isOpen())
			this.entityManager.close();
	}
}
