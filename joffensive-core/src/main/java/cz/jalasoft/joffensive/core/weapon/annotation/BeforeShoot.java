package cz.jalasoft.joffensive.core.weapon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeShoot {
}
