package cz.jalasoft.joffensive.core.weapon.annotation.introspection;

import cz.jalasoft.joffensive.core.Recoil;

import java.lang.reflect.Constructor;
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
        return executable.getParameterCount() > 0;
    }

    static boolean isVoid(Method method) {
        return method.getReturnType().equals(Void.class);
    }

    static boolean isMethodPublicParameterlessAndReturning(Method method, Class<?> returnType) {

        boolean isPublic = isPublic(method);
        boolean isRecoil = returnType.isAssignableFrom(method.getReturnType());
        boolean isParameterls  = isParameterless(method);

        return isPublic && isRecoil && isParameterls;
    }
}
