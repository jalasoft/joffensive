package cz.jalasoft.joffensive.core.invokable;

import java.util.Collection;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-01.
 */
final class AggregatedInvokable implements Invokable<Void> {

    private final Collection<Invokable<Void>> invokers;

    public AggregatedInvokable(Collection<Invokable<Void>> invokers) {
        this.invokers = invokers;
    }

    @Override
    public Void invoke(Object target) throws Exception {

        Exception exception = new Exception();

        for(Invokable<Void> invoker : invokers) {
            try {
                invoker.invoke(target);
            } catch (Exception exc) {
                exception.addSuppressed(exc);
            }
        }

        if (exception.getSuppressed().length > 0) {
            throw exception;
        }

        return null;
    }
}
