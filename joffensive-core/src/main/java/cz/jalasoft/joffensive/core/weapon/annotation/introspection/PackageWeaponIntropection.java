package cz.jalasoft.joffensive.core.weapon.annotation.introspection;

import cz.jalasoft.joffensive.core.weapon.WeaponDefinition;
import cz.jalasoft.joffensive.core.weapon.annotation.Shoot;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-03.
 */
final class PackageWeaponIntropection implements WeaponAnnotationIntrospection {

    private final Reflections reflections;

    PackageWeaponIntropection(String packageName) {
        this.reflections = initializeReflections(packageName);
    }

    private Reflections initializeReflections(String packageName) {
        Scanner methodScanner = new MethodAnnotationsScanner();
        ConfigurationBuilder configuration = new ConfigurationBuilder()
                .addScanners(methodScanner)
                .forPackages(packageName);

        return new Reflections(configuration);
    }

    @Override
    public Collection<WeaponDefinition> introspect() throws WeaponIntrospectionException {
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Shoot.class);
        Collection<Class<?>> types = declaringTypesOf(methods);

        Collection<WeaponDefinition> result = new ArrayList<>();

        for(Class<?> type : types) {
            WeaponAnnotationIntrospection introspection = WeaponAnnotationIntrospection.forType(type);
            Collection<WeaponDefinition> definitions = introspection.introspect();

            result.addAll(definitions);
        }

        return result;
    }

    private Collection<Class<?>> declaringTypesOf(Set<Method> methods) {
        return methods
                .stream()
                .map(Method::getDeclaringClass)
                .collect(toCollection(HashSet::new));
    }

}
