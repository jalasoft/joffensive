package cz.jalasoft.joffensive.core.weapon.proxy.invokable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-01.
 */
public interface Invokable<T> {

    static Invokable<Void> DUMMY = (t) -> null;

    static <T> Invokable<T> method(Method method, Class<T> returnType, Object... parameters) {
        return new MethodInvokable<T>(method, returnType, parameters);
    }

    static Invokable<Void> aggregating(Invokable<Void>... invokables) {
        return aggregating(invokables);
    }

    static Invokable<Void> aggregating(Collection<Invokable<Void>> invokables) {
        return new AggregatedInvokable(invokables);
    }

    static <T> Invokable<T> oneTime(Invokable<T> invokable) {
        return new OneTimeInvokable(invokable);
    }

    T invoke(Object target) throws Exception;
}
