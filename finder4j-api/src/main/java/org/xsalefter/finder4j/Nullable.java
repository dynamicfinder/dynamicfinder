package org.xsalefter.finder4j;

/**
 * Define how the query treat null parameter value in {@link Restriction#values}.
 * @author xsalefter (xsalefter@gmail.com)
 */
public enum Nullable {

	/**
	 * Default value for {@link Restriction#nullable}. This option will make 
	 * {@link RestrictionHandler} by pass the {@link Restriction} if 
	 * {@link Restriction#values} is null. For {@link String} based value, 
	 * this option also will by pass the empty {@link String}. 
	 */
	DISCARD,

	/** 
	 * This option make {@link RestrictionHandler} implementation keep 
	 * including {@link Restriction} although the {@link Restriction#values} 
	 * is null, and thus produce query like 
	 * <code>entityInstance.property = null</code>. Furthermore, if null 
	 * {@link Restriction#values} passed, {@link RestrictionHandler} shouldn't 
	 * add current {@link Restriction} to 
	 * {@link RestrictionHandler#getDefinedRestrictions()}.
	 */
	KEEP,

//	/**
//	 * This is special and extended version of {@link #KEEP} handler for 
//	 * {@link String} based {@link Restriction#values}. This option will 
//	 * also check {@link String#isEmpty()} and add additional query for 
//	 * empty {@link Restriction#values}.
//	 */
//	KEEP_EMPTY,
}