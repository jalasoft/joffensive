package cz.jalasoft.joffensive.core.weapon.annotation.introspection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-03.
 */
final class MaybeWeaponType {

    private final Class<?> examinedType;

    MaybeWeaponType(Class<?> examminedType) {
        this.examinedType = examminedType;
    }

    boolean hasDefaultConstructor() {
        Constructor<?>[] constructors = examinedType.getConstructors();

        for(Constructor<?> constructor : constructors) {
            if (MethodUtils.isPublic(constructor) && MethodUtils.isParameterless(constructor)) {
                return true;
            }
        }
        return false;
    }



    int annotatedMethodCount(Class<? extends Annotation> annotation) {
        return (int) stream(examinedType.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotation))
                .count();
    }

    <T extends Annotation> T annotation(Class<T> annotationType) {
        return annotatedMethod(annotationType).getAnnotation(annotationType);
    }

    Optional<Method> tryAnnotatedMethod(Class<? extends Annotation> annotation) {
        return stream(examinedType.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotation))
                .findFirst();
    }

    Method annotatedMethod(Class<? extends Annotation> annotation) {
        return tryAnnotatedMethod(annotation)
                .orElseThrow(() -> new RuntimeException("No annotation present. Validation should have been invoked before this method."));
    }

    String typeName() {
        return examinedType.getName();
    }

    Class<?> type() {
        return examinedType;
    }
}
