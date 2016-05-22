package cz.jalasoft.joffensive.core.weapon.annotation.introspection;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.weapon.WeaponDefinition;
import cz.jalasoft.joffensive.core.weapon.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import static cz.jalasoft.joffensive.core.weapon.invokable.Invokable.method;
import static java.util.Arrays.asList;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-03.
 */
final class WeaponClassIntrospection implements WeaponAnnotationIntrospection {

    private final MaybeWeaponType examinedType;

    WeaponClassIntrospection(Class<?> type) {
        this.examinedType = new MaybeWeaponType(type);
    }

    @Override
    public Collection<WeaponDefinition> introspect() throws WeaponIntrospectionException {

        validateParameterlessConstructor();

        validateLifecycleMethod(BeforeWeapon.class);
        validateLifecycleMethod(BeforeShoot.class);
        validateShootMethod();
        validateLifecycleMethod(AfterShoot.class);
        validateLifecycleMethod(AfterWeapon.class);

        WeaponDefinition.Builder builder = WeaponDefinition.newDefinition()
                .on(examinedType.type())
                .called(examinedType.annotation(Shoot.class).value());

        examinedType.tryAnnotatedMethod(BeforeWeapon.class).ifPresent(m -> builder.beforeWeapon(method(m, Void.class)));
        examinedType.tryAnnotatedMethod(BeforeShoot.class).ifPresent(m -> builder.beforeShoot(method(m, Void.class)));
        examinedType.tryAnnotatedMethod(AfterShoot.class).ifPresent(m -> builder.afterShoot(method(m, Void.class)));
        examinedType.tryAnnotatedMethod(AfterWeapon.class).ifPresent(m -> builder.afterWeapon(method(m, Void.class)));

        Method shootMethod = examinedType.annotatedMethod(Shoot.class);
        builder.shooting(method(shootMethod, Recoil.class));

        return asList(builder.get());
    }

    private void validateLifecycleMethod(Class<? extends Annotation> annotation) throws WeaponIntrospectionException {
        boolean hasLifecycleMethod = validateLifecycleMethodCount(annotation);

        if (hasLifecycleMethod) {
            validateLifecycleMethodSignature(annotation);
        }
    }

    private boolean validateLifecycleMethodCount(Class<? extends Annotation> annotation) throws WeaponIntrospectionException {
        int count = examinedType.annotatedMethodCount(annotation);

        if (count == 0) {
            return false;
        }

        if (count > 1) {
            throw new WeaponIntrospectionException("There must not be more than 1 method annotated with '" + annotation + "' annotation.");
        }

        return true;
    }

    private void validateLifecycleMethodSignature(Class<? extends Annotation> annotation) throws WeaponIntrospectionException {
        Method prepareMethod = examinedType.annotatedMethod(annotation);

        if (!MethodUtils.isPublic(prepareMethod)) {
            throw new WeaponIntrospectionException("Lifecycle method '" + prepareMethod.getName() + "' on type '" + examinedType.typeName() + "' must be public");
        }

        if (!MethodUtils.isParameterless(prepareMethod)) {
            throw new WeaponIntrospectionException("Lifecycle method '" + prepareMethod.getName() + "' on type '" + examinedType.typeName() + "' must be parameterless");
        }

        if (!MethodUtils.isVoid(prepareMethod)) {
            throw new WeaponIntrospectionException("Lifecycle method '" + prepareMethod.getName() + "' on type '" + examinedType.typeName() + "' must be void");
        }
    }

    private void validateShootMethod() throws WeaponIntrospectionException {
        validateShootAnnotationCount();
        validateShootAnnotationValue();
        validateShootAnnotatedMethodSignature();
    }


    private void validateParameterlessConstructor() throws WeaponIntrospectionException {
        if(!examinedType.hasDefaultConstructor()) {
            throw new WeaponIntrospectionException("Type " + examinedType.typeName() + " does not have parameterless constructor.");
        }
    }

    private void validateShootAnnotationCount() throws WeaponIntrospectionException {
        int shootAnnotationCount = examinedType.annotatedMethodCount(Shoot.class);
        if (shootAnnotationCount != 1) {
            throw new WeaponIntrospectionException("Annotation shoot appears " + shootAnnotationCount + " times in type " + examinedType.typeName() + ", expected is 1.");
        }

    }

    private void validateShootAnnotationValue() throws WeaponIntrospectionException {
        Shoot annotation = examinedType.annotation(Shoot.class);
        String name = annotation.value();
        if (name == null || name.isEmpty()) {
            throw new WeaponIntrospectionException("Annotation shoot on type " + examinedType.typeName() + " should have called as its value.");
        }
    }

    private void validateShootAnnotatedMethodSignature() throws WeaponIntrospectionException {
        Method shootMethod = examinedType.annotatedMethod(Shoot.class);

        if (!MethodUtils.isPublic(shootMethod)) {
            throw new WeaponIntrospectionException("Shoot method '" + shootMethod.getName() + "' on type '" + examinedType.typeName() + "' must be public");
        }

        if (!MethodUtils.isParameterless(shootMethod)) {
            throw new WeaponIntrospectionException("Shoot method '" + shootMethod.getName() + "' on type '" + examinedType.typeName() + "' must be parameterless");
        }

        if (!MethodUtils.isReturning(shootMethod, Recoil.class)) {
            throw new WeaponIntrospectionException("Shoot method '" + shootMethod.getName() + "' on type '" + examinedType.typeName() + "' must be void");
        }

        if (!MethodUtils.isMethodPublicParameterlessAndReturning(shootMethod, Recoil.class)) {
            throw new WeaponIntrospectionException("Shoot method on type " + examinedType.typeName() + " must be public, parameterless and returning Recoil.");
        }
    }

}
