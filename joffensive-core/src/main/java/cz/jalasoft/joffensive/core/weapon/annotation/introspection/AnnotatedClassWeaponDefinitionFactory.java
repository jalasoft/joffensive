package cz.jalasoft.joffensive.core.weapon.annotation.introspection;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.weapon.WeaponDefinition;
import cz.jalasoft.joffensive.core.weapon.annotation.CleanWeapon;
import cz.jalasoft.joffensive.core.weapon.annotation.PrepareWeapon;
import cz.jalasoft.joffensive.core.weapon.annotation.Shoot;
import cz.jalasoft.joffensive.core.invokable.Invokable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static cz.jalasoft.joffensive.core.invokable.Invokable.*;
import static cz.jalasoft.joffensive.core.invokable.Invokable.method;
import static java.lang.reflect.Modifier.isPublic;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 *
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-30.
 */
final class AnnotatedClassWeaponDefinitionFactory {

    /**
     * More than one definition might be retrieved since there can
     * be more then one method annotated with {@link Shoot}
     *
     * @param type
     * @return
     */
    public static WeaponDefinition newDefinition(Class<?> type) {

        validateHasPublicParameterlessConstructor(type);

        Method shootMethod = validateAndGetShootMethod(type);
        String shootMethodName = shootAnnotationValue(shootMethod);

        Collection<Invokable<Void>> prepareMethods = validateAndGetPrepareMethods(type)
                .stream()
                .map(m -> Invokable.method(m, Void.class))
                .collect(Collectors.toList());

        Collection<Invokable<Void>> cleanMethods = validateAndGetCleanMethods(type)
                .stream()
                .map(m -> Invokable.method(m, Void.class))
                .collect(Collectors.toList());


        return WeaponDefinition.newDefinition()
                .on(type)
                .called(shootMethodName)
                .preparing(aggregating(prepareMethods))
                .shooting(method(shootMethod, Recoil.class))
                .cleaning(aggregating(cleanMethods))
                .get();
    }

    //---------------------------------------------------------------------------
    //VALIDATION METHODS
    //---------------------------------------------------------------------------

    private static void validateHasPublicParameterlessConstructor(Class<?> type) {
        for(Constructor<?> constructor : type.getConstructors()) {
            if (isPublicParameterless(constructor)) {
                return;
            }
        }
        throw new WeaponIntrospectionException("Type " + type + " does not have public parameterless constructor.");
    }

    private static Method validateAndGetShootMethod(Class<?> type) {
        List<Method> shootMethods = methodsAnnotatedBy(type, Shoot.class);

        if (shootMethods.size() != 1) {
            throw new IllegalStateException("Unexpected number of shoot methods: " + shootMethods.size());
        }

        Method shootMethod = shootMethods.get(0);

        validateShootMethod(shootMethod);

        return shootMethod;
    }

    private static Method validateShootMethod(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new WeaponIntrospectionException("Shoot method " + method.getDeclaringClass() + "." + method.getName() + " must be public.");
        }
        if (!method.getReturnType().equals(Recoil.class)) {
            throw new WeaponIntrospectionException("Shoot method " + method.getDeclaringClass() + "." + method.getName() + " must have return type Recoil.");
        }
        if (method.getParameterCount() > 0) {
            throw new WeaponIntrospectionException("Shoot method " + method.getDeclaringClass() + "." + method.getName() + " must be parameterless.");
        }

        String name = shootAnnotationValue(method);

        if (name == null || name.isEmpty()) {
            throw new WeaponIntrospectionException("Shoot annotation must have non empty name");
        }

        return method;
    }

    private static String shootAnnotationValue(Method method) {
        Shoot shoot = method.getAnnotation(Shoot.class);
        String name = shoot.value();

        return name;
    }

    private static Collection<Method> validateAndGetPrepareMethods(Class<?> type) {

        Collection<Method> prepareWeaponMethods = methodsAnnotatedBy(type, PrepareWeapon.class);
        prepareWeaponMethods.forEach(m -> validateLifecycleMethod(m, "Prepare"));

        return prepareWeaponMethods;
    }

    private static Collection<Method> validateAndGetCleanMethods(Class<?> type) {
        Collection<Method> cleanWeaponMethods = methodsAnnotatedBy(type, CleanWeapon.class);
        cleanWeaponMethods.forEach(m -> validateLifecycleMethod(m, "Clean"));

        return cleanWeaponMethods;
    }





    private static Method validateLifecycleMethod(Method method, String type) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new WeaponIntrospectionException(type + " method " + method.getDeclaringClass() + "." + method.getName() + " must be public.");
        }
        if (!method.getReturnType().equals(Void.class)) {
            throw new WeaponIntrospectionException(type + " method " + method.getDeclaringClass() + "." + method.getName() + " must by void.");
        }
        if (method.getParameterCount() > 0) {
            throw new WeaponIntrospectionException(type + " method " + method.getDeclaringClass() + "." + method.getName() + " must be parameterless.");
        }
        return method;
    }

    private static List<Method> methodsAnnotatedBy(Class<?> type, Class<? extends Annotation> annotation) {
        return stream(type.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotation))
                .collect(toList());
    }
}
