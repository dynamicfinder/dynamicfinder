package org.xsalefter.finder4j;

/**
 * Specialized of {@link ResultMapper}, when use {@link Finder#get(QueryBuilder)} 
 * method.
 * 
 * @author xsalefter (xsalefter@gmail.com)
 * @param <E> Entity class.
 */
public interface SingleResultMapper<E> extends ResultMapper<E> {
	
	/**
	 * Get the entity result.
	 * @return Entity instance if result found, null if no result found.
	 */
	E getSingleResult();
}
