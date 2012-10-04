package org.xsalefter.finder4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Provide set of methods to define query language dynamically. Example:
 * <pre>
 * List&lt;Restriction&gt; restrictions = new ArrayList&lt;Restriction&gt;();
 * restrictions.add(new Restriction("name", RestrictionType.EQUAL, Nullable.DISCARD, someName));
 * restrictions.add(new Restriction("birthDate", RestrictionType.EQUAL, Nullable.DISCARD, someDate));
 * 
 * QueryBuilder queryBuilder = new JPAQueryBuilder(Person.class);
 * String queryString = queryBuilder.select().
 *   where(restrictions).
 *   getQueryString();
 * </pre>
 * Based on code above:
 * <ul>
 *   <li>
 *     If <code>someName</code> and <code>someDate</code> is not null, 
 *     <code>queryString</code> value would produce something like: <br/>
 *     <code>
 *     select person from Person person where person.name = ?1 
 *     and person.birthDate = ?2
 *     </code>
 *   </li>
 *   <li>
 *     If <code>someName</code> is empty or null and <code>someDate</code> is not 
 *     null, <code>queryString</code> value would produce something like: <br/>
 *     <code>
 *     select person from Person person person.birthDate = ?1
 *     </code>
 *   </li>
 *   <li>
 *     If <code>someName</code> is empty or null and <code>someDate</code> is not 
 *     null, and we change this line:<br/>
 *     <code>restrictions.add(new Restriction("name", RestrictionType.EQUAL, Nullable.DISCARD, someName));</code><br/>
 *     to:<br/>
 *     <code>restrictions.add(new Restriction("name", RestrictionType.EQUAL, Nullable.KEEP, someName));</code>,<br/>
 *     <code>queryString</code> value would produce something like: <br/>
 *     <code>
 *     select person from Person person 
 *     where person.name is null and person.birthDate = ?1
 *     </code>
 *   </li>
 * </ul>
 * </p>
 * 
 * <p>
 * Query language result could be retrieved by invoking {@link #getQueryString()} 
 * method. Additionally, {@link #getCountQueryString()} could be used to 
 * get correct query language for counting number of total data fetched by 
 * {@link #getQueryString()}, especially when dealing with {@link Restriction}. 
 * Note that {@link #getQueryString()} and {@link #getCountQueryString()} would 
 * (and should) produce <strong>parameterized query language</strong>. Every 
 * query parameter value could be retrieved by invoke {@link #getRestrictions()}.
 * </p>
 * 
 * @author xsalefter (xsalefter@gmail.com)
 */
/* NOTE: 
 * - One reason we doesn't have QueryBuilder#from() method is that it's easy to 
 *   forget to call from() method. Another reason is that to force client code to 
 *   define the entity name on constructor.
 * - Problem with named parameter binding is that how we supposed to deal with 
 *   this issue:
 *   select person from Person person where person.name = :person_name_param_1 
 *   or person.name = :person_name_param_2
 *   
 *   of course we could use List<RestrictionMap> getRestrictions() instead of 
 *   Map<Integer, Restriction> getRestrictions(), but it's start another 
 *   complexity:
 *   - Incompatible with (future) JDBC.
 *   - The key need to be generic, to minimize bug that could be appear when use
 *     Object as a key.
 */ 
public interface QueryBuilder {

	/**
	 * <p>
	 * Used to define fields to add to query. Optional call. If this method 
	 * doesn't call explicitly, implementation class should produce default 
	 * and valid query language.
	 * </p>
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
	 * // Will produce 'select person from Person person where person.age = :params'.
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
	 * <p>Used to add {@link Restriction} for this {@link QueryBuilder}.</p>
	 * @param restrictions {@link List} of {@link Restriction} to added.
	 * @return Current instance of {@link QueryBuilder} (for method chaining).
	 * @throws NoRestrictionHandlerException if no {@link RestrictionHandler} 
	 * defined with particular {@link RestrictionType} in {@link Restriction#getType()}.
	 */
	QueryBuilder where(List<Restriction> restrictions) throws NoRestrictionHandlerException;
	QueryBuilder orderBy(Order...orders);
	QueryBuilder groupBy(String...fields);

	/**
	 * <p>Get filtered {@link Restriction}s to bind to specifics data API</p>
	 * <p>
	 * Based on {@link Restriction} added by {@link #where(List)} or 
	 * {@link #where(Restriction...)}, this method only return 
	 * {@link Restriction} that parsed by 
	 * {@link RestrictionHandler#handleRestriction(Restriction)} when 
	 * {@link RestrictionHandler.DTO#hasParameterizedQueryString()} is true.
	 * </p>
	 * <p>That this method should return read-only/unmodifiable {@link Map}. 
	 * Thus, any modification to this method should throw 
	 * {@link UnsupportedOperationException} at runtime.</p>
	 * 
	 * @return {@link Collections#unmodifiableMap(Map)} of {@link Restriction}s.
	 */
	Map<Integer, Restriction> getRestrictions();

	/**
	 * Get parameterized query {@link String}. Invoke this method should clearing 
	 * buffer that handle chunk of query. 
	 * @return {@link String} of parameterized query string.
	 */
	String getQueryString();

	/**
	 * Get parameterized query {@link String} for count the data size. Invoke 
	 * this method should clearing buffer that handle chuck of query. 
	 * @return {@link String} of query string for count data size.
	 */
	String getCountQueryString();
}
