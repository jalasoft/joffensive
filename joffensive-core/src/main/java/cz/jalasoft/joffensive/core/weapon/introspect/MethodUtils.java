package cz.jalasoft.joffensive.core.weapon.introspect;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-07.
 */
final class MethodUtils {

    static boolean isPublic(Executable executable) {
        return Modifier.isPublic(executable.getModifiers());
    }

    static boolean isParameterless(Executable executable) {
        return executable.getParameterCount() == 0;
    }

    static boolean isReturning(Method method, Class<?> returnType) {
        return method.getReturnType().equals(returnType);
    }

    static boolean isVoid(Method method) {
        Class<?> returnType = method.getReturnType();

        return returnType.equals(Void.class) || returnType.equals(void.class);
    }

    static boolean isMethodPublicParameterlessAndReturning(Method method, Class<?> returnType) {

        boolean isPublic = isPublic(method);
        boolean isRecoil = returnType.isAssignableFrom(method.getReturnType());
        boolean hasParameters  = isParameterless(method);

        return isPublic && isRecoil && hasParameters;
    }

    private MethodUtils() {
        throw new RuntimeException("Huh....");
    }
}
