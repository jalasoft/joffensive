package cz.jalasoft.joffensive.core.weapon.proxy.invokable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-01.
 */
final class MethodInvokable<T> implements Invokable<T> {

    private final Class<T> returnType;
    private final Method method;
    private final Object[] parameters;

    MethodInvokable(Method method, Class<T> returnType, Object... parameters) {
        this.returnType = returnType;
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public T invoke(Object target) throws Exception {
        try {
            Object result = method.invoke(target, parameters);
            T castResult = returnType.cast(result);
            return castResult;
        } catch (InvocationTargetException exc) {
            Throwable cause = exc.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }

            throw (Error) cause;
        }
    }
}
