package org.dynamicfinder.spi;

import org.dynamicfinder.Restriction;


/**
 * SPI for all {@link Restriction} implementation. This class would be used in 
 * every {@link QueryBuilder#where(java.util.List)} implementation to able to 
 * get access to {@link #setParameter(Integer)} and set the parameter value 
 * before {@link Restriction} is parsed by {@link RestrictionHandler} implementation.
 * @author xsalefter (xsalefter[at]gmail.com) 
 */
public abstract class AbstractRestriction implements Restriction {

	private static final long serialVersionUID = 5469977765248403818L;

	private Integer parameter;

	/**
	 * Set the parameter value.
	 * @param value to set.
	 */
	public final void setParameter(Integer value) {
		this.parameter = value;
	}

	/**
	 * Get the parameter value.
	 * @return parameter value.
	 */
	public final Integer getParameter() {
		return this.parameter;
	}
}
