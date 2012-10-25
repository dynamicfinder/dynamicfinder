package org.dynamicfinder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * <p>
 * This annotation used for enabling dependency injection with {@link QueryBuilder}.
 * Example:
 * <pre>&#064;Finder(forClass=Person.class)
 * private QueryBuilder queryBuilder;</pre>
 * </p>
 * 
 * <p>
 * Although there's no required members for this annotation, {@link #forClass()} 
 * or {@link #forEntity()} should be provided to make sure {@link QueryBuilder} 
 * instance bounded to correct type/entity name. If no member defined, or 
 * all member defined, implementation should throws {@link IllegalArgumentException}.
 * </p>
 * 
 * <p>
 * Note that the API only provide an annotation. Implementation detail would 
 * involve existing dependency injection container such as CDI, Guice, or 
 * Spring framework, and would be provided in another modules. Additionally, 
 * this annotation is part of JSR-330 {@link Qualifier}.
 * </p>
 * 
 * @author xsalefter (xsalefter[at]gmail.com)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Qualifier
public @interface Finder {

	/**
	 * Used to bind an entity class to {@link QueryBuilder} instance.
	 */
	Class<?> forClass() default Object.class;

	/**
	 * Used to bind entity name to {@link QueryBuilder} instance.
	 */
	String forEntity() default "";

	/**
	 * Used to determine which implementation with used for current 
	 * {@link QueryBuilder} instance. Only applicable if use more than one 
	 * implementation.
	 */
	String module() default "";
}
