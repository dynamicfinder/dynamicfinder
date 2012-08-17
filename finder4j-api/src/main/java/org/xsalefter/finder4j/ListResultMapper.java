package org.xsalefter.finder4j;

import java.util.List;

/**
 * Specialized of {@link ResultMapper}, when used with 
 * {@link Finder#find(QueryBuilder)} method.
 * 
 * @author xsalefter (xsalefter@gmail.com)
 * @param <E> Entity class.
 */
public interface ListResultMapper<E> extends ResultMapper<E> {
	
	/**
	 * Get {@link List} of entity.
	 * @return {@link List} of entity.
	 */
	List<E> getResultList();
	
	/**
	 * Indicate that the {@link #getResultList()} is empty and no data found with 
	 * current criteria defined in {@link QueryBuilder} object. Obviously, 
	 * implementation of this method would call {@link #getResultList()#isEmpty()} 
	 * method. 
	 * 
	 * @return True if no {@link #getResultList()#isEmpty()} is {@code true}.
	 */
	boolean isEmpty();
	
	/**
	 * Get total data size produced by {@link Finder#find(QueryBuilder)} method.
	 * @return Number of total data size.
	 */
	Long getTotalDataSize();
}
