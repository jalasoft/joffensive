package cz.jalasoft.joffensive.core.weapon.annotation.introspection;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.invokable.Invokable;
import cz.jalasoft.joffensive.core.weapon.WeaponDefinition;
import cz.jalasoft.joffensive.core.weapon.annotation.CleanWeapon;
import cz.jalasoft.joffensive.core.weapon.annotation.PrepareWeapon;
import cz.jalasoft.joffensive.core.weapon.annotation.Shoot;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-03.
 */
final class ClassWeaponIntrospection implements WeaponAnnotationIntrospection {

    private final MaybeWeaponType examinedType;

    ClassWeaponIntrospection(Class<?> type) {
        this.examinedType = new MaybeWeaponType(type);
    }

    @Override
    public Collection<WeaponDefinition> introspect() throws WeaponIntrospectionException {

        validateParameterlessConstructor();
        validatePrepareMethod();
        validateShootMethod();
        validateCleanMethod();

        Method shootMethod = examinedType.annotatedMethod(Shoot.class);

        WeaponDefinition.Builder builder = WeaponDefinition.newDefinition()
                .on(examinedType.type())
                .called(examinedType.annotation(Shoot.class).value());

        Optional<Method> maybePrepareMethod = examinedType.tryAnnotatedMethod(PrepareWeapon.class);
        if (maybePrepareMethod.isPresent()) {
            builder.preparing(Invokable.method(maybePrepareMethod.get(), Void.class));
        }

        Invokable<Recoil> shootInvokable = Invokable.method(shootMethod, Recoil.class);
        builder.shooting(shootInvokable);


        Optional<Method> maybeCleanMethod = examinedType.tryAnnotatedMethod(CleanWeapon.class);
        if (maybeCleanMethod.isPresent()) {
            builder.cleaning(Invokable.method(maybeCleanMethod.get(), Void.class));
        }

        return asList(builder.get());
    }

    private void validatePrepareMethod() throws WeaponIntrospectionException {
        validatePrepareAnnotationCount();
        validatePrepareAnnotatedMethodSignature();
    }

    private void validatePrepareAnnotationCount() throws WeaponIntrospectionException {
        int count = examinedType.annotatedMethodCount(PrepareWeapon.class);

        if (count == 0) {
            return;
        }

        if (count > 1) {
            throw new WeaponIntrospectionException("There must not be more than 1 method annotated with PrepareWeapon annotation.");
        }
    }

    private void validatePrepareAnnotatedMethodSignature() throws WeaponIntrospectionException {
        Method prepareMethod = examinedType.annotatedMethod(PrepareWeapon.class);
        if (!MethodUtils.isMethodPublicParameterlessAndReturning(prepareMethod, Void.class)) {
            throw new WeaponIntrospectionException("Prepare method on type " + examinedType.typeName() + " must be public, parameterless and void.");
        }
    }

    private void validateCleanMethod() throws WeaponIntrospectionException {
        validateCleanAnnotationCount();
        validateCleanAnnotatedMethodSignature();
    }

    private void validateCleanAnnotationCount() throws WeaponIntrospectionException {
        int count = examinedType.annotatedMethodCount(CleanWeapon.class);

        if (count == 0) {
            return;
        }

        if (count > 1) {
            throw new WeaponIntrospectionException("There must not be more than 1 method annotated with CleanWeapon annotation.");
        }
    }

    private void validateCleanAnnotatedMethodSignature() throws WeaponIntrospectionException {
        Method cleanMethod = examinedType.annotatedMethod(CleanWeapon.class);
        if (!MethodUtils.isMethodPublicParameterlessAndReturning(cleanMethod, Void.class)) {
            throw new WeaponIntrospectionException("Clean method on type " + examinedType.typeName() + " must be public, parameterless and void.");
        }
    }


    private void validateShootMethod() throws WeaponIntrospectionException {
        validateShootAnnotationCount();
        validateShootAnnotationValue();
        validateShootAnnotatedMethodSignature();
    }


    private void validateParameterlessConstructor() throws WeaponIntrospectionException {
        if(!examinedType.hasPublicParameterlessConstructor()) {
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
            throw new WeaponIntrospectionException("Annotation shoot on type " + examinedType.typeName() + " should have name as its value.");
        }
    }

    private void validateShootAnnotatedMethodSignature() throws WeaponIntrospectionException {
        Method shootMethod = examinedType.annotatedMethod(Shoot.class);
        if (!MethodUtils.isMethodPublicParameterlessAndReturning(shootMethod, Recoil.class)) {
            throw new WeaponIntrospectionException("Shoot method on type " + examinedType.typeName() + " must be public, parameterless and returning Recoil.");
        }
    }

}
