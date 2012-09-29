package org.xsalefter.finder4j;

import org.xsalefter.finder4j.spi.RestrictionHandler;

/**
 * Define how to handle null parameter value in {@link Restriction#values}.
 * @author xsalefter (xsalefter@gmail.com)
 */
public enum Nullable {

	/**
	 * Default value for {@link Restriction#nullable}. This option will make 
	 * {@link RestrictionHandler} by pass the {@link Restriction} if 
	 * {@link Restriction#getValues()} is null. For {@link String} based value, 
	 * this option also will by pass the empty {@link String}.
	 */
	DISCARD,

	/** 
	 * This option make {@link RestrictionHandler} implementation keep producing 
	 * restriction string although the {@link Restriction#getValues()} 
	 * is null, and thus produce query like 
	 * <code>entityInstance.property = null</code>. Furthermore, if null 
	 * {@link Restriction#getValues()} passed, {@link RestrictionHandler} 
	 * shouldn't add current {@link Restriction} to 
	 * {@link RestrictionHandler#getDefinedRestrictions()}.
	 */
	KEEP;

	/**
	 * Get the lower-case version of this {@link Nullable#toString()}.
	 * @return {@link String#toLowerCase()} version of {@link Nullable#toString()}.
	 */
	public String toLowerCase() {
		return this.toString().toLowerCase();
	}

	/**
	 * Get the upper-case version of this {@link Nullable#toString()}.
	 * @return {@link String#toLowerCase()} version of {@link Nullable#toString()}.
	 */
	public String toUpperCase() {
		return this.toString().toUpperCase();
	}

//	/**
//	 * This is special and extended version of {@link #KEEP} handler for 
//	 * {@link String} based {@link Restriction#values}. This option will 
//	 * also check {@link String#isEmpty()} and add additional query for 
//	 * empty {@link Restriction#values}.
//	 */
//	KEEP_EMPTY,
}