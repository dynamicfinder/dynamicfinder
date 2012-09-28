package org.xsalefter.finder4j;

public final class ParameterRestriction<P> {

	private final P parameter;
	private final Restriction restriction;

	public ParameterRestriction(final P parameter, final Restriction restriction) {
		this.parameter = parameter;
		this.restriction = restriction;
	}

	public final P getParameter() {
		return parameter;
	}

	public final Restriction getRestriction() {
		return restriction;
	}

}
