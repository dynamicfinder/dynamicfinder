package org.xsalefter.finder4j;

import java.util.List;

/**
 * {@link Finder} interface.
 * @author xsalefter
 *
 * @param <E> Entity class.
 */
public interface Finder<E> {

	/**
	 * Get {@link List} of entity based on criteria defined in {@link QueryBuilder}
	 * parameter.
	 * @param queryBuilder query to restrict data obtained from data source.
	 * @return {@link ListResultMapper} object. Never return {@code null}.
	 */
	ListResultMapper<E> find(QueryBuilder queryBuilder);

	/**
	 * Get single entity result based on criteria defined in {@link QueryBuilder} 
	 * parameter.
	 * @param queryBuilder query to restrict data obtained from data source.
	 * @return {@link SingleResultMapper} object. Never return {@code null}.
	 */
	SingleResultMapper<E> get(QueryBuilder queryBuilder);
}
