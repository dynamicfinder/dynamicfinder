package org.dynamicfinder.jpa;

/**
 * Direct implementation of {@link AbstractJpaQueryBuilder}.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaQueryBuilder extends AbstractJpaQueryBuilder {

	public JpaQueryBuilder(final Class<?> entityClass) {
		super(entityClass, new JpaRestrictionHandlerFactory());
	}

}
