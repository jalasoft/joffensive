package cz.jalasoft.joffensive.core.weapon.proxy.invokable;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-01.
 */
final class OneTimeInvokable<T> implements Invokable<T> {

    private final Invokable<T> decorated;

    private boolean invoked;
    private T result;
    private Exception exceptionalResult;

    OneTimeInvokable(Invokable<T> decorated) {
        this.decorated = decorated;
        this.invoked = false;
    }

    @Override
    public T invoke(Object target) throws Exception {
        if (invoked) {
            return replayInvocation();
        }

        invoked = true;

        return recordInvocation(target);
    }

    private T recordInvocation(Object target) throws Exception {
        try {
            result = decorated.invoke(target);
            return result;
        } catch (Exception t) {
            exceptionalResult = t;
            throw t;
        }
    }

    private T replayInvocation() throws Exception {
        if (result != null) {
            return result;
        }

        if (exceptionalResult == null) {
            throw new RuntimeException("No result available");
        }

        throw exceptionalResult;
    }
}
