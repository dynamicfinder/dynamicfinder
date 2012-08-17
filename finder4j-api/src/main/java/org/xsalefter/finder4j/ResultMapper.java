package org.xsalefter.finder4j;

import java.io.Serializable;
import java.util.Map;

/**
 * Data Transfer Object for {@link Finder} methods.
 * @author xsalefter
 * @param <E> Entity class.
 */
public interface ResultMapper<E> extends Serializable {
	Map<String, Object> getSearchTerms();
}
