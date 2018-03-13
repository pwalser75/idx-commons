package ch.frostnova.caching.util;

import java.lang.annotation.*;

/**
 * Annotation for cache injection. The cache to be used is specified by name,
 * if the name is absent, the cache named 'defaultCache' will be used.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface NamedCache {
    String name() default "defaultCache";
}
