package org.xsalefter.finder4j;

/**
 * Define {@link RestrictionLogic} after {@link Restriction} object parsed as JPQL.
 */
public enum RestrictionLogic {
	AND { 
		public String toString() { return "and"; }
	}, 
	OR {
		public String toString() { return "or"; }
	};
}
