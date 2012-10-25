package org.dynamicfinder;

/**
 * Throws when no {@link RestrictionHandler} defined for supplied {@link RestrictionType}.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public final class NoRestrictionHandlerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoRestrictionHandlerException(final RestrictionType restrictionType) {
		super("No RestrictionHandler defined for RestrictionType: " + restrictionType);
	}
}
