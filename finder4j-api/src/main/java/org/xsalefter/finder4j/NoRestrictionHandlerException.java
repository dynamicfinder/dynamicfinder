package org.xsalefter.finder4j;

import org.xsalefter.finder4j.spi.RestrictionHandler;

/**
 * Throws when no {@link RestrictionHandler} defined for supplied {@link RestrictionType}.
 * @author xsalefter (xsalefter@gmail.com)
 */
public final class NoRestrictionHandlerException extends NullPointerException {

	private static final long serialVersionUID = 670341131545341311L;

	/**
	 * Default constructor.
	 * @param restrictionType that doesn't have {@link RestrictionHandler}.
	 */
	public NoRestrictionHandlerException(final RestrictionType restrictionType) {
		super("No RestrictionHandler defined for RestrictionType: " + restrictionType);
	}
}
