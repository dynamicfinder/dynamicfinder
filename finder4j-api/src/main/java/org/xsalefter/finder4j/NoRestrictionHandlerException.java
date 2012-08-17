package org.xsalefter.finder4j;

/**
 * Throws when no {@link RestrictionHandler} defined for supplied {@link RestrictionType}.
 * @author xsalefter
 */
public final class NoRestrictionHandlerException extends RuntimeException {

	private static final long serialVersionUID = 670341131545341311L;

	public NoRestrictionHandlerException(final RestrictionType restrictionType) {
		super("No RestrictionHandler defined for RestrictionType: " + restrictionType);
	}
}
