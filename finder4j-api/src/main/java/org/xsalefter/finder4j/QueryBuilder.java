package org.xsalefter.finder4j;

import java.util.List;
import java.util.Map;

/**
 * Used to define Java Persistence API query. The implementation class would 
 * provide constructor with {@link Class} of entity class as a parameter.
 * 
 * @author xsalefter (xsalefter@gmail.com)
 * @see {@link AbstractQueryBuilder}.
 * @see {@link RestrictionHandler}.
 */
public interface QueryBuilder {

	/**
	 * Defining fields to add to query. Optional call. If this method doesn't 
	 * call explicitly, implementation of this interface will produce default 
	 * {@link String} query value.
	 * <p>
	 * Example:
	 * <pre>
	 * QueryBuilder queryBuilder = new SomeQueryBuilderImpl(Person.class);
	 * // Will produce 'select person from Person person'.
	 * queryBuiler.select();
	 * 
	 * // Will produce 'select person.name, person.age from Person'.
	 * queryBuilder.select("name", "age");
	 * 
	 * // Will product 'select person from Person person where person.age = :params'.
	 * queryBuilder.where(new Restriction("age", RestrictionType.EQUAL, "20");
	 * </pre>
	 * </p> 
	 * @param fields to select.
	 * @return Current {@link QueryBuilder} instance.
	 */
	QueryBuilder select(String...fields);
	QueryBuilder join(String...fields);

	/**
	 * Do the same thing as {@link #where(List)}, but accept args parameter 
	 * instead of {@link List}.
	 * @param restrictions to add.
	 * @return Current {@link QueryBuilder} instance.
	 * @throws NoRestrictionHandlerException if no {@link RestrictionHandler} defined 
	 * with particular {@link RestrictionType} in {@link Restriction#getType()}.
	 */
	QueryBuilder where(Restriction...restrictions) throws NoRestrictionHandlerException;

	/**
	 * Used to define {@link Restriction} for this {@link QueryBuilder}.
	 * @param restrictions {@link List} of {@link Restriction} to added.
	 * @return Current instance of {@link QueryBuilder} (for method chaining).
	 * @throws NoRestrictionHandlerException if no {@link RestrictionHandler} 
	 * defined. 
	 * with particular {@link RestrictionType} in {@link Restriction#getType()}.
	 */
	QueryBuilder where(List<Restriction> restrictions) throws NoRestrictionHandlerException;
	QueryBuilder orderBy(Order...orders);
	QueryBuilder groupBy(String...fields);
	QueryBuilder limit(int resultSize, int indexOfResultSize);

	/**
	 * <p>Add new {@link RestrictionHandler} to this {@link QueryBuilder}. For 
	 * each {@link RestrictionType}, specifics {@link RestrictionHandler} is 
	 * needed to make {@link RestrictionHandler#parseRestriction(Restriction)} 
	 * parse {@link Restriction} correctly.</p>
	 * 
	 * <p>In implementation detail, each {@link RestrictionHandler} will be 
	 * placed in {@link Map} (or its implementation) where the key will be 
	 * unique {@link RestrictionType} and the value would be 
	 * {@link RestrictionHandler} implementation.
	 * 
	 * @param restrictionType {@link RestrictionType} as an unique key.
	 * @param handler {@link RestrictionHandler} to added.
	 * @see QueryBuilder#addRestrictionHandlers(Map).
	 */
	void addRestrictionHandler(RestrictionType restrictionType, RestrictionHandler handler);

	/**
	 * Instead of registering one by one {@link RestrictionHandler} in 
	 * {@link #addRestrictionHandler(RestrictionType, RestrictionHandler)}, 
	 * this method will register all {@link RestrictionHandler} defined in 
	 * {@link Map}.
	 * @param handlers {@link Map} with {@link RestrictionType} key and 
	 * {@link RestrictionHandler} value.
	 */
	void addRestrictionHandlers(Map<? extends RestrictionType, ? extends RestrictionHandler> handlers);

	/**
	 * Get {@link Restriction}s needed.
	 * @return
	 */
	Map<String, Restriction> getRestrictions();

	/**
	 * Get the parsed query string.
	 * @return Query String.
	 */
	String toString();
}
